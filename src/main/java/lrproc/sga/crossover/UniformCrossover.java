package lrproc.sga.crossover;


import lrproc.sga.Chromosome;
import lrproc.sga.crossover.Crossover;


public class UniformCrossover implements Crossover
{
  public void crossover(Chromosome x, Chromosome y)
  {
    Number temp;

    // TODO: allow for random offsetting if one chromosom is longer than the other

    int length = Math.min(x.length(), y.length());

    for (int i = 0; i < length; i++)
    {
      if (MyRandom.boolRandom())
      {
        temp = x.getGene(i);
        x.setGene(i, y.getGene(i));
        y.setGene(i, temp);
      }
    }
  }
}

