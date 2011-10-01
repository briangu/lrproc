/*
 * This chromosome class implements encoding the problem of maximizing
 * 21.5+x1*sin(4*PIx1)+x2*sin(20*PI*x2)+x3 for x1 in [-3.0,12.1],
 * x2 in [4.1,5.8], and x3 in [0.0, 1.0].
 */
public class DogChromosome extends IntegerChromosome {// this user-defined class
                                               // implements evalChromosome,
   protected DogChromosome() {              // toPhenotype;
      super();                               // defines _chromosomeLength,
   }                                        // _knownSolutionFitness, and
                                           // _solutionFitness
   static {
      chromosomeLength = 5;
      System.out.println("lrproc.examples.DogChromosome: chromosome length is "
         + chromosomeLength);
      knownSolutionFitness = true;
      solutionFitness = 23145;
      // compiler forbids this :-( geneLowerBound = {-3.0, 4.1, 0.0};
      int[] lower = {1, 1, 1, 1, 1}; geneLowerBound = lower;
      int[] upper = {4, 256, 256, 4, 256}; geneUpperBound = upper;
   }

	private
	double
	evalOperation(
		int	op,
		int	arg1,
		int	arg2)
	{
		double r = 0.0;

		switch (op)
		{
			case 1:
				r = arg1 + arg2;
				break;
			case 2:
				r = arg1 - arg2;
				break;
			case 3:
				r = arg1 * arg2;
				break;
			case 4:
				r = arg1 / arg2;
				break;
		}

		return r;
	}

   protected double evalChromosome() {
	double fitness = 0.0;

	fitness = evalOperation(gene[0], gene[1], gene[2]);
	fitness = evalOperation(gene[3], (int)fitness, gene[4]);

	if (fitness > solutionFitness)
	{
		fitness = (int)fitness % solutionFitness;
	}

	if (fitness <= 0)
	{
		fitness = 1.0;
	}

      return fitness;
   }

	private
	String
	opToPhenotype(
			int	op)
	{
		String s;

		s = new String();

		switch (op)
		{
			case 1:
				s += '+';
				break;
			case 2:
				s += '-';
				break;
			case 3:
				s += '*';
				break;
			case 4:
				s += '/';
				break;
		}

		return s;
	}

   public String toPhenotype() {
	String s;

	s = new String();

	s = s + "_fitness = " + gene[1] + opToPhenotype(gene[0]) + gene[2] + opToPhenotype(gene[3]) + gene[4];

      return s;
   }
}

