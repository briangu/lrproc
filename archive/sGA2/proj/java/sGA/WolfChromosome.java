public class cpuBrain extends ByteChromosome {// this user-defined class

	static private MPU8051 cpu;

                                               // implements evalChromosome,
   protected WolfChromosome() {              // toPhenotype;
      super();                               // defines _chromosomeLength,
   }                                        // _knownSolutionFitness, and
                                           // _solutionFitness
   static {
      chromosomeLength = 16;
      System.out.println("lrproc.examples.cpuBrain: chromosome length is " + chromosomeLength);
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
	double fitness3;

	double x = getCalcResult();

	if (cpu.isHalted())
	{
		double y = 122;
		x = x - y;
		if (Math.abs(x) > y)
		{
			fitness1 = 0.0;
		}
		else
		{
			fitness1 = Math.sqrt(y*y - x*x) / y; 
		}

		x = cpu.getRegister(0);
		y = y / 2.;
		x = x - y;
		if (Math.abs(x) > y)
		{
			fitness2 = 0.0;
		}
		else
		{
			fitness2 = Math.sqrt(y*y - x*x) / y; 
		}

		x = cpu.getPokeCnt();
		x = x - y;		
		if (Math.abs(x) > y)
		{
			fitness3 = 0.0;
		}
		else
		{
			fitness3 = Math.sqrt(y*y - x*x) / y; 
		}

		fitness = fitness1*0.3 + fitness2*0.3 + fitness3*0.4;

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

 	s = s + " acc = " +  cpu.getAcc() + " r0 = "+cpu.getRegister(0) + " pokeCnt = " + cpu.getPokeCnt();
      return s;
   }
}

