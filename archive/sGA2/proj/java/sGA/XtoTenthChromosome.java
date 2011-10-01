/*
 * This chromosome class implements encoding the problem of maximizing
 * x**10 over the interval 0.0 to 1.0 where the value of x is encoded in
 * some number of bits representing an unsigned integer normalized to
 * be between 0 and 1.
 */
public class XtoTenthChromosome extends BitChromosome  {// this user-defined class
                                                // implements evalChromosome,
   protected XtoTenthChromosome() {            // toPhenotype;
      super();                                // defines _chromosomeLength,
   }                                         // _knownSolutionFitness, and
                                            // _solutionFitness
   private static double divisor;

   static {
      chromosomeLength = 32;
      System.out.println("lrproc.examples.XtoTenthChromosome: chromosome length is "
         + chromosomeLength);
      knownSolutionFitness = true;
      solutionFitness = 1.0;
      divisor = Math.pow((double)2.0, (double)chromosomeLength) - 1;
   }

   protected double evalChromosome() {
      return Math.pow(phenotype(), (double) 10);
   }

   private double phenotype() {
      double value = 0;
      for (int i = 0; i < chromosomeLength; i++) {
         if (bits[i]) value += Math.pow((double)2.0, (double)(i));
      }
      return value/divisor;
   }

   public String toPhenotype() {
      return "x=" + phenotype();
   }
}

