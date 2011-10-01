/*
 * Pick N points (p0,p1,...) at random between 1 and _chromosomeLength-1
 * (inclusive) then sort them.  If N is odd, add the point _chromosomeLength
 * to the N already picked.  Then exchange between the two chromosomes being
 * crossed over the genes in the ranges [p0,p1), [p2,p3), ....
 */
public class NPointCrossover implements Crossover {

   private int numPoints = -1;
   private int chromLength = -1;
   private int[] crossArray = null;
   private int pointsToDo = -1;

   public NPointCrossover(int numXoverPoints) {
      super();
      numPoints = numXoverPoints;
      chromLength = Chromosome.getChromosomeLength();
      pointsToDo = numPoints;
      if ((numPoints % 2) == 0) crossArray = new int[numPoints];
      else {
         // If odd number of crossover points, make an even number
         // by using the chromLength as the last crossover point
         crossArray = new int[numPoints+1];
         crossArray[numPoints] = chromLength;
         pointsToDo++;
      }
   }

   public void xOver(Chromosome x, Chromosome y) {
      int from, to;
      Number temp;

      // Fill in crossover points into an array.
      for (int i = 0; i < numPoints; i++) {
         crossArray[i] = MyRandom.intRandom(chromLength-1);
      }

      // Sort the array of crossover points.
      Utilities.sort(crossArray);

      if (Debug.flag) {
         System.out.print("Npoint crossover at:");
         for (int i = 0; i < crossArray.length; i++) {
            System.out.print(" " + crossArray[i]);
         }
         System.out.println();
      }

      // Perform crossover over the right sections.
      for (int j = 0; j < pointsToDo; j += 2) {
         from = crossArray[j];
         to = crossArray[j+1];
         if (from != to) {
            for (int i = from; i < to; i++) {
               temp = x.getGene(i);
               x.setGene(i, y.getGene(i));
               y.setGene(i, temp);
            }
         }
      }
   }
}

