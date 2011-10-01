package lrproc.sga.exceptions;


import lrproc.sga.exceptions.SelectionException;


public class FitnessSumZeroException extends SelectionException
{
  FitnessSumZeroException()
  {
    super("lrproc.sga.exceptions.ProportionalSelection: population _fitness sums to zero");
  }
}



