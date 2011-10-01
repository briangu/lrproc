package lrproc.sga.exceptions;

/**************************************************************/
/* Selection function: Standard proportional selection for    */
/* maximization problems incorporating elitist model - makes  */
/* sure that the best member survives.                        */

import lrproc.sga.Chromosome;
import lrproc.sga.Selection;
import org.apache.log4j.Logger;

public class ProportionalSelection implements Selection
{
  Logger log = Logger.getLogger(ProportionalSelection.class);

  // roulette wheel method
  public void select(Chromosome[] population, Chromosome[] newPopulation, int populationSize)
      throws FitnessSumZeroException
  {

    double p, sum = 0;

    // find total _fitness of the population
    for (int mem = 0; mem < populationSize; mem++)
    {
      sum += population[mem].getFitness();
    }

    if (sum == 0) throw new FitnessSumZeroException();

    // calculate relative _fitness
    for (int mem = 0; mem < populationSize; mem++)
    {
      population[mem].rfitnessSet(population[mem].getFitness() / sum);
    }

    population[0].cfitnessSet(population[0].rfitnessGet());
    if (log.isTraceEnabled())
    {
      log.trace("mem=0, _cumulativefitness=" + population[0].cfitnessGet());
    }

    // calculate cumulative _fitness
    for (int mem = 1; mem < populationSize; mem++)
    {
      population[mem].cfitnessSet(population[mem - 1].cfitnessGet() + population[mem].rfitnessGet());
      if (log.isTraceEnabled())
      {
        log.trace("mem=" + mem + ", _cumulativefitness=" + population[mem].cfitnessGet());
      }
    }

    // finally select survivors using cumulative _fitness.
    for (int i = 0; i < populationSize; i++)
    {
      p = MyRandom.dblRandom();
      if (p < population[0].cfitnessGet())
      {
        population[0].copyChromosome(newPopulation[i]);
        if (log.isTraceEnabled())
        {
          log.trace("p=" + p + ", selected 0");
        }
      }
      else
      {
        for (int j = 0; j < populationSize; j++)
        {
          if (p >= population[j].cfitnessGet() && p < population[j + 1].cfitnessGet())
          {
            // note that population[populationSize-1].cfitnessGet()
            // is 1.0, so j+1 never gets as big as populationSize
            population[j + 1].copyChromosome(newPopulation[i]);
            if (log.isTraceEnabled())
            {
              log.trace("p=" + p + ", selected " + (j + 1));
            }
          }
        }
      }
    }
  }
}

