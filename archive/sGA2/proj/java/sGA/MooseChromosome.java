public class MooseChromosome extends ByteChromosome {// this user-defined class

	static private MPU8051 cpu;

                                               // implements evalChromosome,
   protected MooseChromosome() {              // toPhenotype;
      super();                               // defines chromosomeLength,
   }                                        // knownSolutionFitness, and
                                           // solutionFitness
   static {
      chromosomeLength = 32;
      System.out.println("MooseChromosome: chromosome length is " + chromosomeLength);
      knownSolutionFitness = true;
      solutionFitness = 1.0;

	cpu = new MPU8051(chromosomeLength);

	byte[] lower = new byte[chromosomeLength];
	byte[] upper = new byte[chromosomeLength];

	geneLowerBound = lower;
	geneUpperBound = upper;

	for (int i = 0; i < chromosomeLength; i++)
	{
		geneLowerBound[i] = 0;
		geneUpperBound[i] = (byte)(cpu.getInstructionCount() - 1);
	}

   }

	private
	int
	getCalcResult()
	{
		cpu.reset();
		cpu.loadProgram(gene);
		cpu.run();

		return (cpu.getPokeCnt());
	}

   protected double evalChromosome() {
	double fitness;

	double x = getCalcResult();

	if (cpu.isHalted())
	{
		double y = 100;
		x = x - y;
		fitness = Math.sqrt(y*y - x*x) / y; 

		if (fitness < 0)
		{
			fitness = 0.0;
		}
	}
	else
	{
		fitness = 0.0;
	}

	return fitness;
   }

   public String toPhenotype() {
	String s;

	s = new String();

	cpu.reset();
	cpu.loadProgram(gene);
	cpu.traceOn();
	cpu.run();
	cpu.traceOff();

 	s = s + " PokeCnt = " +  cpu.getPokeCnt();
      return s;
   }
}

