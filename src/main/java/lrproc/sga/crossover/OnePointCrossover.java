package lrproc.sga.crossover;


import java.util.Random;
import lrproc.sga.Chromosome;
import org.apache.log4j.Logger;

/*
* Pick a point at random between 1 and _chromosomeLength-1 (inclusive),
* then exchange all genes at positions 0 to point-1 (inclusive) between
* the two chromosomes being crossed over.
*/
public class OnePointCrossover implements Crossover
{
  Logger log = Logger.getLogger(OnePointCrossover.class);

  private Random _rnd;

  public OnePointCrossover(Random rnd)
  {
    _rnd = rnd;
  }

  public void crossover(Chromosome one, Chromosome two)
  {
    int point;
    int chromLen = Chromosome.length();
    Number temp;

    if (chromLen > 1)
    {
      if (chromLen == 2)
      {
        point = 1;
      }
      else
      {
        point = _rnd.nextInt(chromLen - 1);
      }

      for (int i = 0; i < point; i++)
      {
        temp = one.getGene(i);
        one.setGene(i, two.getGene(i));
        two.setGene(i, temp);
      }

      if (log.isTraceEnabled())
      {
        log.trace("lrproc.sga.crossover.OnePointCrossover: just crossed at " + point);
      }
    }
  }
}

