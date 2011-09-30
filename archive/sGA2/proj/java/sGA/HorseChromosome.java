public class HorseChromosome extends ByteChromosome {// this user-defined class

	static private MPU8051 cpu;

                                               // implements evalChromosome,
   protected HorseChromosome() {              // toPhenotype;
      super();                               // defines chromosomeLength,
   }                                        // knownSolutionFitness, and
                                           // solutionFitness
   static {
      chromosomeLength = 17;
      System.out.println("HorseChromosome: chromosome length is " + chromosomeLength);
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

		return (cpu.getAcc());
	}

   protected double evalChromosome() {
	double fitness;
	double fitness1;
	double fitness2;

	double x = getCalcResult();

	if (cpu.isHalted())
	{
		double y = 122;
		x = x - y;
	//	fitness1 = (-(x*x) + y) / y; 
		fitness1 = Math.sqrt(y*y - x*x) / y; 

		double z = cpu.getRegister(0);
		y = y / 2.;
		z = z - y;
	//	fitness2 = (-(z*z) + y) / y; 
		fitness2 = Math.sqrt(y*y - z*z) / y; 

		fitness = fitness1*0.5 + fitness2*0.5;

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

 	s = s + " acc = " +  cpu.getAcc() + " r0 = "+cpu.getRegister(0);
      return s;
   }
}

