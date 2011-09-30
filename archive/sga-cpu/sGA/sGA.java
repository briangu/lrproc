import java.io.*;

public class sGA {

   // give fundamental parameters controlling the GA default values
   private static int         populationSize =  100;
   private static int         numXoverPoints =    1;
   private static boolean     doElitism      = false;
   private static double      crossoverRate  =     .7;
   private static double      maxMutationRate   =     .001;
   private static double      mutationRate   =     maxMutationRate;

   // these parameters control the running of the program
   private static int         printPerGens   =  100; // print every this often
   private static int         maxGenerations = 1000; // quit when reached
   private static String      logFileName    = null;
   private static PrintStream myLog          = null;

   // record the time the program started
   private static final long  startTime      = System.currentTimeMillis();
   private static long age() {
      return System.currentTimeMillis() - startTime;
   }

   public static void main(String args[]) {

      // process command line arguments, overriding GA parameter defaults
      GetOpt go = new GetOpt(args, "Udp:x:c:m:P:G:EF:");
      go.optErr = true;
      int ch = -1;
      boolean usagePrint = false;
      while ((ch = go.getopt()) != go.optEOF) {
         if      ((char)ch == 'U') usagePrint = true;
         else if ((char)ch == 'd') Debug.flag = true;
         else if ((char)ch == 'p')
            populationSize = go.processArg(go.optArgGet(), populationSize);
         else if ((char)ch == 'x')
            numXoverPoints = go.processArg(go.optArgGet(), numXoverPoints);
         else if ((char)ch == 'c')
            crossoverRate = go.processArg(go.optArgGet(), crossoverRate);
         else if ((char)ch == 'm')
	 {
            maxMutationRate = go.processArg(go.optArgGet(), mutationRate);
		mutationRate = maxMutationRate;
	 }
         else if ((char)ch == 'E') doElitism = true;
         else if ((char)ch == 'P')
            printPerGens = go.processArg(go.optArgGet(), printPerGens);
         else if ((char)ch == 'G')
            maxGenerations = go.processArg(go.optArgGet(), maxGenerations);
         else if ((char)ch == 'F')
            logFileName = go.optArgGet();
         else System.exit(1); // undefined option
      }
      if (usagePrint) {
         System.out.println
            ("Usage: -d -p populationSize -x numXoverPoints\n" +
             "       -E -c crossoverRate -m mutationRate\n" +
             "       -P printPerGens -G maxGenerations -F logFileName");
         System.exit(0);
      }

      if (logFileName != null) {
         boolean autoFlush = true;
         try {
            myLog = new PrintStream(new BufferedOutputStream(
               new FileOutputStream(new File(logFileName)), 1024), autoFlush);
         } catch (IOException e) {
            System.err.println("IOException opening file " + logFileName);
            System.exit(1);
         }
      }
     // if (myLog != null) System.out = myLog;

      System.out.println
         ("The GA parameters are:\n" +
          "  populationSize      = " + populationSize       + "\n" +
          "  numXoverPoints      = " + numXoverPoints       + "\n" +
          "  crossoverRate       = " + crossoverRate        + "\n" +
          "  mutationRate        = " + mutationRate         + "\n" +
          "  doElitism           = " + doElitism            + "\n" +
          "  printPerGens        = " + printPerGens         + "\n" +
          "  maxGenerations      = " + maxGenerations       + "\n" +
          "  Debug.flag          = " + Debug.flag           + "\n" +
          "  logFileName         = " + logFileName);

      // force loading class MyChromosome and setting static variables
      new MyChromosome();
      System.out.println("GA: chromosome length = " +
         Chromosome.getChromosomeLength());

      sGA theGA = new sGA();
      theGA.mainLoop();
   }

   private sGA() { // only GA.main() can create GA
      super();
   }

   private void printChromosome(String name, Chromosome c) {
      System.out.println(name +
        ":\n   " + c.toGenotype() +
         "\n   " + c.toPhenotype() +
         "\n    fitness= " + c.getFitness());
   }

   private final Chromosome theBest = new MyChromosome();
   private int theBestGeneration; // generation theBest first showed up
   // 0..popSize-1 holds population and theBest holds the most fit
   private Chromosome[] population    = new Chromosome[populationSize];
   private Chromosome[] newPopulation = new Chromosome[populationSize];

   private int generationNum;     // current generation number
   private int numCrossovers, numMutations;  // tabulate for report()

   private Selection theSelection = null;
   private Crossover theCrossover = null;

   private void mainLoop() {
      System.out.println("GA: mainLoop");
      if (Chromosome.isSolutionFitnessKnown()) {
         System.out.println("Known solution fitness is " +
            Chromosome.getSolutionFitness());
      }
      initialize();
      findTheBest();
      report("Initial population");
      while (!terminated()) {
         generationNum++;
         try {
            theSelection.select(population, newPopulation, populationSize);
            swapPopulationArrays();
         } catch (FitnessSumZeroException e) {
            // replace current population with a new random one;
            // other possibilities are to do nothing and hope mutation
            // fixes the problem eventually
            if (Debug.flag) System.out.println(e + " (randomizing)");
            for (int i = 0; i < populationSize; i++) {
               population[i].initializeChromosomeRandom();
            }
         } catch (SelectionException e) {
            if (Debug.flag) System.out.println(e);
         }
         if (Debug.flag) report("Selection");
         crossover();
         if (Debug.flag) report("Crossover");
         mutate();
         if (Debug.flag) report("Mutation");
         if (doElitism) elitism();
         else justUpdateTheBest();
         if (Debug.flag || (generationNum % printPerGens) == 0) {
            report("Report");
         }
      }
      System.out.println("Simulation completed in " + generationNum +
         " generations and " + age()/1000.0 + " seconds");
      printChromosome("Best member (generation="+theBestGeneration+")",
         theBest);
      System.exit(0);
   }

   private void initialize() {

      generationNum = 0; numCrossovers = 0; numMutations = 0;

      theSelection = new ProportionalSelection();

      if (numXoverPoints == 0) theCrossover = new UniformCrossover();
      else if (numXoverPoints == 1) theCrossover = new OnePointCrossover();
      else theCrossover = new NPointCrossover(numXoverPoints);

      for (int j = 0; j < populationSize; j++) {
         population[j] = new MyChromosome();
         population[j].initializeChromosomeRandom();
         newPopulation[j] = new MyChromosome();
      }

      if (Debug.flag) {
         for (int j=0; j<populationSize; j++) {
            printChromosome("p" + j, population[j]);
         }
      }
   }

   private void findTheBest() {
      double currentBestFitness = population[0].getFitness();
      double next = -1;
      int currentBest = 0; // index of the current best individual

      for (int j = 1; j < populationSize; j++) {
         if ((next = population[j].getFitness()) > currentBestFitness ) {
            currentBest = j;
            currentBestFitness = next;
         }
      }
      // once the best member in the population is found, copy the genes
      population[currentBest].copyChromosome(theBest);

      if (Debug.flag) {
          printChromosome("currentBest (generation="+theBestGeneration+")",
             theBest);
      }
   }

	private
	void
	adjustMutationRate(
				double	fitness)
	{
		mutationRate = maxMutationRate * (1.0 - fitness); 
	}

   private boolean terminated() {
      return (theBest.isSolutionFitnessKnown() &&
              theBest.getFitness() == theBest.getSolutionFitness()) ||
             generationNum >= maxGenerations;
   }

   private void swapPopulationArrays() {
      Chromosome[] temp = population;
      population = newPopulation;
      newPopulation = temp;
   }

   /***************************************************************/
   /* Crossover selection: selects two parents that take part in  */
   /* the crossover.                                              */
   /***************************************************************/
   private void crossover() {
      int one = -1;    // compiler complains not being initialized
      int first  =  0; // count of the number of members chosen

      for (int mem = 0; mem < populationSize; ++mem) {
         if (MyRandom.dblRandom() < crossoverRate) {
            ++first;
            if (first % 2 == 0) {
               numCrossovers++;
               if (Debug.flag) {
                  System.out.println("crossing " + one + " and " + mem);
               }
               theCrossover.xOver(population[one], population[mem]);
            } else one = mem;
         }
      }
   }

   /**************************************************************/
   /* Mutation: Random uniform mutation. A variable selected for */
   /* mutation is replaced by a random value between lower and   */
   /* upper bounds of this variable                              */
   /**************************************************************/
   private void mutate() {
      int chromosomeLength = Chromosome.getChromosomeLength();
      for (int i = 0; i < populationSize; i++) {
         for (int j = 0; j < chromosomeLength; j++) {
            if (MyRandom.dblRandom() < mutationRate) {
               numMutations++;
               population[i].mutateGene(j);
               if (Debug.flag) {
                  printChromosome("mutation, i=" + i + ", gene=" + j,
                     population[i]);
               }
            }
         }
      }
   }

   /***************************************************************/
   /* Report function: Reports progress of the simulation.
   /***************************************************************/
   private void report(String title) {
      double best_val;            // best population fitness
      double avg;                 // avg population fitness
      double stddev;              // std. deviation of population fitness
      double sum_square;          // sum of square for std. calc
      double square_sum;          // square of sum for std. calc
      double sum;                 // total population fitness
      double fitness;

      sum = 0.0;
      sum_square = 0.0;

      for (int i = 0; i < populationSize; i++) {
         fitness = population[i].getFitness();
         sum += fitness;
         sum_square += fitness * fitness;
      }

      avg = sum/(double)populationSize;
      square_sum = sum * sum/(double)populationSize;
      stddev = Math.sqrt((1.0/(double)(populationSize - 1))
         *(sum_square - square_sum));
      best_val = theBest.getFitness();

      System.out.println(title + ": generation=" + generationNum +
         " most fit=" + best_val + " avg=" + avg + " stddev=" + stddev);
      printChromosome("most fit (generation="+theBestGeneration+")", theBest);
      System.out.println("mutationRate: " + mutationRate);
      System.out.println("number of crossovers and mutations: " + numCrossovers + " and " + numMutations);

      if (Debug.flag) {
         for (int j=0; j<populationSize; j++) {
            printChromosome("p" + j, population[j]);
         }
      }
   }

   /****************************************************************/
   /* Elitist function: The best member of the previous generation */
   /* is stored in theBest Chromosome.  If the best member of      */
   /* the current generation is worse then the best member of the  */
   /* previous generation, the latter one would replace the worst  */
   /* member of the current population                             */
   /****************************************************************/
   private void elitism() {

      double best, worst;          // best and worst fitness values
      int bestMember, worstMember; // indexes of the best and worst member
      int start; // index used to start the loop

      if (populationSize % 2 == 0) {
         best = -1; bestMember = -1;
         worst = Double.MAX_VALUE; worstMember = -1;
         start = 0;
      } else {
         best  = population[0].getFitness(); bestMember = 0;
         worst = population[0].getFitness(); worstMember = 0;
         start = 1;
      }

      for (int i = start; i < populationSize - 1; i+=2) {
         if (population[i].getFitness() > population[i+1].getFitness()) {
            if (population[i].getFitness() > best) {
               best = population[i].getFitness(); bestMember = i;
            }
            if (population[i+1].getFitness() < worst) {
               worst = population[i+1].getFitness(); worstMember = i + 1;
            }
         } else {
            if (population[i].getFitness() < worst) {
               worst = population[i].getFitness(); worstMember = i;
            }
            if (population[i+1].getFitness() > best) {
               best = population[i+1].getFitness(); bestMember = i + 1;
            }
         }
      }
      // if best individual from the new population is better than
      // the best individual from the previous population, then
      // copy the best from the new population; else replace the
      // worst individual from the current population with the
      // best one from the previous generation
      if (best > theBest.getFitness()) {
         population[bestMember].copyChromosome(theBest);
         theBestGeneration = generationNum;
      } else {
         theBest.copyChromosome(population[worstMember]);
      }

	adjustMutationRate(best);

      if (Debug.flag) {
         printChromosome("elitism, best (index="+bestMember+")",
            population[bestMember]);
         printChromosome("elitism, worst (index="+worstMember+")",
            population[worstMember]);
      }
   }

   private void justUpdateTheBest() {
      double currentBestFitness = population[0].getFitness();
      double next = -1;
      int currentBest = 0; // index of the current best individual

      for (int j = 1; j < populationSize; j++) {
         if ((next = population[j].getFitness()) > currentBestFitness ) {
            currentBest = j;
            currentBestFitness = next;
         }
      }
      if (currentBestFitness > theBest.getFitness()) {
         population[currentBest].copyChromosome(theBest);
         theBestGeneration = generationNum;
      }

      if (Debug.flag) {
          printChromosome("theBest (generation="+theBestGeneration+")",
             theBest);
      }
   }
}

