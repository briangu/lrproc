package lrproc.sga;


import lrproc.sga.exceptions.SelectionException;


interface Selection
{
  public void select(Chromosome[] population, Chromosome[] newPopulation, int populationSize)
      throws SelectionException;
}



