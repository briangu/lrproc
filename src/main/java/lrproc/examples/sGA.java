package lrproc.examples;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import lrproc.examples.MyChromosome;
import lrproc.sga.Chromosome;
import lrproc.sga.Selection;
import lrproc.sga.crossover.Crossover;
import lrproc.sga.crossover.NPointCrossover;
import lrproc.sga.crossover.OnePointCrossover;
import lrproc.sga.crossover.UniformCrossover;
import lrproc.sga.exceptions.FitnessSumZeroException;
import lrproc.sga.exceptions.ProportionalSelection;
import lrproc.sga.exceptions.SelectionException;


public class sGA
{
  // give fundamental parameters controlling the GA default values
  private static int populationSize = 100;
  private static int numXoverPoints = 1;
  private static boolean doElitism = false;
  private static double crossoverRate = .7;
  private static double maxMutationRate = .001;
  private static double mutationRate = maxMutationRate;

  // these parameters control the running of the program
  private static int printPerGens = 100; // print every this often
  private static int maxGenerations = 1000; // quit when reached
  private static String logFileName = null;
  private static PrintStream myLog = null;

  // record the time the program started
  private static final long startTime = System.currentTimeMillis();

  private static long age()
  {
    return System.currentTimeMillis() - startTime;
  }

  public static void main(String args[])
  {
    // process command line arguments, overriding GA parameter defaults
    GetOpt go = new GetOpt(args, "Udp:x:c:m:P:G:EF:");
    go.optErr = true;
    int ch = -1;
    boolean usagePrint = false;
    while ((ch = go.getopt()) != go.optEOF)
    {
      if ((char) ch == 'U') usagePrint = true;
      else if ((char) ch == 'd') Debug.flag = true;
      else if ((char) ch == 'p') populationSize = go.processArg(go.optArgGet(), populationSize);
      else if ((char) ch == 'x') numXoverPoints = go.processArg(go.optArgGet(), numXoverPoints);
      else if ((char) ch == 'c') crossoverRate = go.processArg(go.optArgGet(), crossoverRate);
      else if ((char) ch == 'm')
      {
        maxMutationRate = go.processArg(go.optArgGet(), mutationRate);
        mutationRate = maxMutationRate;
      }
      else if ((char) ch == 'E') doElitism = true;
      else if ((char) ch == 'P') printPerGens = go.processArg(go.optArgGet(), printPerGens);
      else if ((char) ch == 'G') maxGenerations = go.processArg(go.optArgGet(), maxGenerations);
      else if ((char) ch == 'F') logFileName = go.optArgGet();
      else System.exit(1); // undefined option
    }
    if (usagePrint)
    {
      System.out.println("Usage: -d -p populationSize -x numXoverPoints\n" + "       -E -c crossoverRate -m mutationRate\n" + "       -P printPerGens -G maxGenerations -F logFileName");
      System.exit(0);
    }

    if (logFileName != null)
    {
      boolean autoFlush = true;
      try
      {
        myLog = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(logFileName)), 1024), autoFlush);
      }
      catch (IOException e)
      {
        System.err.println("IOException opening file " + logFileName);
        System.exit(1);
      }
    }
    // if (myLog != null) System.out = myLog;

    System.out.println("The GA parameters are:\n" + "  populationSize      = " + populationSize + "\n" + "  numXoverPoints      = " + numXoverPoints + "\n" + "  crossoverRate       = " + crossoverRate + "\n" + "  mutationRate        = " + mutationRate + "\n" + "  doElitism           = " + doElitism + "\n" + "  printPerGens        = " + printPerGens + "\n" + "  maxGenerations      = " + maxGenerations + "\n" + "  lrproc.common.Debug.flag          = " + Debug.flag + "\n");

    // force loading class lrproc.examples.MyChromosome and setting static variables
    new MyChromosome();
    System.out.println("GA: chromosome length = " + Chromosome.length());

    sGA theGA = new sGA();
    theGA.mainLoop();
  }
}

