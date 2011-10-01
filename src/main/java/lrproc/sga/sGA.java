package lrproc.sga;


import lrproc.sga.crossover.Crossover;
import lrproc.sga.crossover.NPointCrossover;
import lrproc.sga.crossover.OnePointCrossover;
import lrproc.sga.crossover.UniformCrossover;
import lrproc.sga.exceptions.FitnessSumZeroException;
import lrproc.sga.exceptions.ProportionalSelection;
import lrproc.sga.exceptions.SelectionException;
import org.apache.log4j.Logger;


public class sGA implements Runnable
{
  Logger log = Logger.getLogger(sGA.class);

  private int populationSize = 100;
  private int numXoverPoints = 1;
  private boolean doElitism = false;
  private double crossoverRate = .7;
  private double maxMutationRate = .001;
  private double mutationRate = maxMutationRate;

  private int printPerGens = 100; // print every this often
  private int maxGenerations = 1000; // quit when reached

  private final Chromosome theBest = new MyChromosome();

  private int theBestGeneration; // generation theBest first showed up
  // 0..popSize-1 holds population and theBest holds the most fit
  private Chromosome[] population = new Chromosome[populationSize];
  private Chromosome[] newPopulation = new Chromosome[populationSize];

  private int generationNum;     // current generation number
  private int numCrossovers, numMutations;  // tabulate for report()

  private Selection theSelection = null;
  private Crossover theCrossover = null;

  private final long startTime = System.currentTimeMillis();

  private long age()
  {
    return System.currentTimeMillis() - startTime;
  }

  public void run()
  {
    log.trace("GA: mainLoop");

    if (Chromosome.isSolutionFitnessKnown())
    {
      System.out.println("Known solution _fitness is " + Chromosome.getSolutionFitness());
    }

    initialize();
    findTheBest();

    report("Initial population");

    while (!terminated())
    {
      generationNum++;

      try
      {
        theSelection.select(population, newPopulation, populationSize);
        swapPopulationArrays();
      }
      catch (FitnessSumZeroException e)
      {
        // replace current population with a new random one;
        // other possibilities are to do nothing and hope mutation
        // fixes the problem eventually
        if (log.isTraceEnabled()) System.out.println(e + " (randomizing)");
        for (int i = 0; i < populationSize; i++)
        {
          population[i].initializeChromosomeRandom();
        }
      }
      catch (SelectionException e)
      {
        if (log.isTraceEnabled()) System.out.println(e);
      }

      if (log.isTraceEnabled()) report("Selection");

      crossover();

      if (log.isTraceEnabled()) report("Crossover");

      mutate();

      if (log.isTraceEnabled()) report("Mutation");

      if (doElitism)
      {
        elitism();
      }
      else
      {
        justUpdateTheBest();
      }

      if (log.isTraceEnabled() || (generationNum % printPerGens) == 0)
      {
        report("Report");
      }
    }

    System.out.println("Simulation completed in " + generationNum + " generations and " + age() / 1000.0 + " seconds");

    printChromosome("Best member (generation=" + theBestGeneration + ")", theBest);
  }

  private void initialize()
  {
    generationNum = 0;
    numCrossovers = 0;
    numMutations = 0;

    theSelection = new ProportionalSelection();

    if (numXoverPoints == 0) theCrossover = new UniformCrossover();
    else if (numXoverPoints == 1) theCrossover = new OnePointCrossover();
    else theCrossover = new NPointCrossover(numXoverPoints);

    for (int j = 0; j < populationSize; j++)
    {
      population[j] = new MyChromosome();
      population[j].initializeChromosomeRandom();
      newPopulation[j] = new MyChromosome();
    }

    if (log.isTraceEnabled())
    {
      for (int j = 0; j < populationSize; j++)
      {
        printChromosome("p" + j, population[j]);
      }
    }
  }

  private void findTheBest()
  {
    double currentBestFitness = population[0].getFitness();
    double next;
    int currentBest = 0;

    for (int j = 1; j < populationSize; j++)
    {
      if ((next = population[j].getFitness()) > currentBestFitness)
      {
        currentBest = j;
        currentBestFitness = next;
      }
    }

    // once the best member in the population is found, copy the genes
    population[currentBest].copyChromosome(theBest);

    if (log.isTraceEnabled())
    {
      printChromosome("currentBest (generation=" + theBestGeneration + ")", theBest);
    }
  }

  private void adjustMutationRate(double fitness)
  {
    mutationRate = maxMutationRate * (1.0 - fitness);
    if (mutationRate < 0.001)
    {
      mutationRate = 0.001;
    }
  }

  private boolean terminated()
  {
    return (theBest.isSolutionFitnessKnown() && theBest.getFitness() == theBest.getSolutionFitness()) || generationNum >= maxGenerations;
  }

  private void swapPopulationArrays()
  {
    Chromosome[] temp = population;
    population = newPopulation;
    newPopulation = temp;
  }

  /**
   * ***********************************************************
   */
  /* Crossover selection: selects two parents that take part in  */
  /* the crossover.                                              */
  private void crossover()
  {
    int one = -1;
    int first = 0;

    for (int mem = 0; mem < populationSize; ++mem)
    {
      if (MyRandom.dblRandom() < crossoverRate)
      {
        ++first;
        if (first % 2 == 0)
        {
          numCrossovers++;
          if (log.isTraceEnabled())
          {
            System.out.println("crossing " + one + " and " + mem);
          }
          theCrossover.crossover(population[one], population[mem]);
        }
        else one = mem;
      }
    }
  }

  /**************************************************************/
  /* Mutation: Random uniform mutation. A variable selected for */
  /* mutation is replaced by a random value between lower and   */
  /* upper bounds of this variable                              */

  /**
   * **********************************************************
   */
  private void mutate()
  {
    int chromosomeLength = Chromosome.length();
    for (int i = 0; i < populationSize; i++)
    {
      for (int j = 0; j < chromosomeLength; j++)
      {
        if (MyRandom.dblRandom() < mutationRate)
        {
          numMutations++;
          population[i].mutateGene(j);
          if (log.isTraceEnabled())
          {
            printChromosome("mutation, i=" + i + ", gene=" + j, population[i]);
          }
        }
      }
    }
  }

  private void report(String title)
  {
    double best_val;            // best population _fitness
    double avg;                 // avg population _fitness
    double stddev;              // std. deviation of population _fitness
    double sum_square;          // sum of square for std. calc
    double square_sum;          // square of sum for std. calc
    double sum;                 // total population _fitness
    double fitness;

    sum = 0.0;
    sum_square = 0.0;

    for (int i = 0; i < populationSize; i++)
    {
      fitness = population[i].getFitness();
      sum += fitness;
      sum_square += fitness * fitness;
    }

    avg = sum / (double) populationSize;
    square_sum = sum * sum / (double) populationSize;
    stddev = Math.sqrt((1.0 / (double) (populationSize - 1)) * (sum_square - square_sum));
    best_val = theBest.getFitness();

    System.out.println(title + ": generation=" + generationNum + " most fit=" + best_val + " avg=" + avg + " stddev=" + stddev);
    printChromosome("most fit (generation=" + theBestGeneration + ")", theBest);
    System.out.println("mutationRate: " + mutationRate);
    System.out.println("number of crossovers and mutations: " + numCrossovers + " and " + numMutations);

    if (log.isTraceEnabled())
    {
      for (int j = 0; j < populationSize; j++)
      {
        printChromosome("p" + j, population[j]);
      }
    }
  }

  /****************************************************************/
  /* Elitist function: The best member of the previous generation */
  /* is stored in theBest lrproc.sga.Chromosome.  If the best member of      */
  /* the current generation is worse then the best member of the  */
  /* previous generation, the latter one would replace the worst  */
  /* member of the current population                             */
  private void elitism()
  {

    double best, worst;          // best and worst _fitness values
    int bestMember, worstMember; // indexes of the best and worst member
    int start; // index used to start the loop

    if (populationSize % 2 == 0)
    {
      best = -1;
      bestMember = -1;
      worst = Double.MAX_VALUE;
      worstMember = -1;
      start = 0;
    }
    else
    {
      best = population[0].getFitness();
      bestMember = 0;
      worst = population[0].getFitness();
      worstMember = 0;
      start = 1;
    }

    for (int i = start; i < populationSize - 1; i += 2)
    {
      if (population[i].getFitness() > population[i + 1].getFitness())
      {
        if (population[i].getFitness() > best)
        {
          best = population[i].getFitness();
          bestMember = i;
        }
        if (population[i + 1].getFitness() < worst)
        {
          worst = population[i + 1].getFitness();
          worstMember = i + 1;
        }
      }
      else
      {
        if (population[i].getFitness() < worst)
        {
          worst = population[i].getFitness();
          worstMember = i;
        }
        if (population[i + 1].getFitness() > best)
        {
          best = population[i + 1].getFitness();
          bestMember = i + 1;
        }
      }
    }

    // if best individual from the new population is better than
    // the best individual from the previous population, then
    // copy the best from the new population; else replace the
    // worst individual from the current population with the
    // best one from the previous generation
    if (best > theBest.getFitness())
    {
      population[bestMember].copyChromosome(theBest);
      theBestGeneration = generationNum;
    }
    else
    {
      theBest.copyChromosome(population[worstMember]);
    }

    adjustMutationRate(best);

    if (log.isTraceEnabled())
    {
      printChromosome("elitism, best (index=" + bestMember + ")", population[bestMember]);
      printChromosome("elitism, worst (index=" + worstMember + ")", population[worstMember]);
    }
  }

  private void justUpdateTheBest()
  {
    double currentBestFitness = population[0].getFitness();
    double next = -1;
    int currentBest = 0; // index of the current best individual

    for (int j = 1; j < populationSize; j++)
    {
      if ((next = population[j].getFitness()) > currentBestFitness)
      {
        currentBest = j;
        currentBestFitness = next;
      }
    }
    if (currentBestFitness > theBest.getFitness())
    {
      population[currentBest].copyChromosome(theBest);
      theBestGeneration = generationNum;
    }

    if (log.isTraceEnabled())
    {
      printChromosome("theBest (generation=" + theBestGeneration + ")", theBest);
    }
  }

  private void printChromosome(String name, Chromosome c)
  {
    if (!log.isTraceEnabled()) return;

    log.trace(name + ":\n   " + c.toGenotype() + "\n   " + c.toPhenotype() + "\n    _fitness= " + c.getFitness());
  }
}

