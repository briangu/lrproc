package lrproc.sga;

import java.util.concurrent.Callable;

public class Chromosome
{
  int _length = -1;

  boolean _knownSolutionFitness = false;
  double _solutionFitness = -1;
  double _fitness = -1;
  double _relativefitness = -1;
  double _cumulativefitness = -1;
  final Callable<Double> _fitnessFn;

  public Chromosome(Callable<Double> fitnessFn)
  {
    _fitnessFn = fitnessFn;
  }

  public int length()
  {
    return _length;
  }

  public final double getFitness()
  {
    return (_fitness = evalChromosome());
  }

  public final double rfitnessGet()
  {
    return _relativefitness;
  }

  public final double cfitnessGet()
  {
    return _cumulativefitness;
  }

  public final void rfitnessSet(double value)
  {
    _relativefitness = value;
  }

  public final void cfitnessSet(double value)
  {
    _cumulativefitness = value;
  }

  public boolean isSolutionFitnessKnown()
  {
    return _knownSolutionFitness;
  }

  public double getSolutionFitness()
  {
    return _solutionFitness;
  }

  public String toString()
  {
    return toGenotype();
  }

  protected double evalChromosome()
  {
    try
    {
      return _fitnessFn.call();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return 0;
  }

  public void mutateGene(int i)
  {

  }

  public String toPhenotype()
  {

  }

  public String toGenotype()
  {

  }
}

