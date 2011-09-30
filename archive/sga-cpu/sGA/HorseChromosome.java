public class HorseChromosome extends IntegerChromosome {// this user-defined class

	static private CPUEmulator cpu;
	int myLastAcc;
                                               // implements evalChromosome,
   protected HorseChromosome() {              // toPhenotype;
      super();                               // defines chromosomeLength,
   }                                        // knownSolutionFitness, and
                                           // solutionFitness
   static {
      chromosomeLength = 128;
      System.out.println("HorseChromosome: chromosome length is "
         + chromosomeLength);
      knownSolutionFitness = true;
      solutionFitness = 1.0;
      // compiler forbids this :-( geneLowerBound = {-3.0, 4.1, 0.0};

	int[] lower = new int[chromosomeLength];
	int[] upper = new int[chromosomeLength];

	geneLowerBound = lower;
	geneUpperBound = upper;

	for (int i = 0; i < chromosomeLength; i++)
	{
		geneLowerBound[i] = 1;
		geneUpperBound[i] = 4;
	}

	cpu = new CPUEmulator(chromosomeLength);
   }

	private
	int
	getCalcResult()
	{
		cpu.reset();
		cpu.loadProgram(gene);
		cpu.run(chromosomeLength);

		return (cpu.getAcc());
	}

   protected double evalChromosome() {
	double fitness;

	double x = getCalcResult();
	double y = 251;
	x = x - y;
	fitness = (-(x*x)*.1 + y) / y; 

	if (fitness < 0)
	{
		fitness = 0.0;
	}

      return fitness;
   }

   public String toPhenotype() {
	String s;

	s = new String();

	for(int i = 0; i < chromosomeLength; i++)
	{
		s += gene[i];
	}

 	s = s + " acc = " +  getCalcResult();
      return s;
   }
}

