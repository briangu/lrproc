public class WolfChromosome extends IntegerChromosome {// this user-defined class

	static private MPU8051 cpu;

                                               // implements evalChromosome,
   protected WolfChromosome() {              // toPhenotype;
      super();                               // defines chromosomeLength,
   }                                        // knownSolutionFitness, and
                                           // solutionFitness
   static {
      chromosomeLength = 64;
      System.out.println("WolfChromosome: chromosome length is " + chromosomeLength);
      knownSolutionFitness = true;
      solutionFitness = 1.0;

System.out.println("About to init");
	cpu = new MPU8051();
System.out.println("Did init");

	int[] lower = new int[chromosomeLength];
	int[] upper = new int[chromosomeLength];

	geneLowerBound = lower;
	geneUpperBound = upper;

	System.out.println(cpu.getInstructionCount());
	for (int i = 0; i < chromosomeLength; i++)
	{
		geneLowerBound[i] = 0;
		geneUpperBound[i] = cpu.getInstructionCount() - 1;
	}

	System.out.println(cpu.getInstructionCount());

   }

   protected double evalChromosome() {
	double fitness;
	double fitness1;
	double fitness2;
	double fitness3;
	double fitness4;

	cpu.reset();
	cpu.loadProgram(gene);
	cpu.run();

	if (cpu.isHalted() && cpu.getObjectCount() == 4)
	{
		double	x;
		double	y;

		x = ((Integer)cpu.getObject(0)).intValue();
		y = 128;
		x = x - y;
		if (Math.abs(x) > y)
		{
			fitness1 = 0.0;
		}
		else
		{
			fitness1 = Math.sqrt(y*y - x*x) / y; 
		}

		x = ((Integer)cpu.getObject(1)).intValue();
		y = 32;
		x = x - y;
		if (Math.abs(x) > y)
		{
			fitness2 = 0.0;
		}
		else
		{
			fitness2 = Math.sqrt(y*y - x*x) / y; 
		}

		x = ((Integer)cpu.getObject(2)).intValue(); 
		y = 64;
		x = x - y;
		if (Math.abs(x) > y)
		{
			fitness3 = 0.0;
		}
		else
		{
			fitness3 = Math.sqrt(y*y - x*x) / y; 
		}

		x = ((Integer)cpu.getObject(3)).intValue(); 
		y = 16;
		x = x - y;
		if (Math.abs(x) > y)
		{
			fitness4 = 0.0;
		}
		else
		{
			fitness4 = Math.sqrt(y*y - x*x) / y; 
		}

	//	fitness = fitness1*0.5 + fitness2*0.5;
	//	fitness = fitness1*0.4 + fitness2*0.3 + fitness3*0.3;
		fitness = fitness1*0.25 + fitness2*0.25 + fitness3*0.25 + fitness4*0.25;

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

 	s = s + cpu.toString(); 
      return s;
   }
}

