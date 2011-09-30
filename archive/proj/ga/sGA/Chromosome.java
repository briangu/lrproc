public class Chromosome {

   protected static int     chromosomeLength = -1;        // will be set by
   protected static boolean knownSolutionFitness = false; // MyChromosome
   protected static double  solutionFitness  = -1;        // if known
   protected        double  fitness          = -1;
   protected        double  rfitness         = -1; // relative fitness
   protected        double  cfitness         = -1; // cumulative fitness

   protected        int[] gene           = null;
   protected static int[] geneLowerBound = null;
   protected static int[] geneUpperBound = null;
	static private MPU8051 cpu = null;

   protected Chromosome() 
	{
		chromosomeLength = 32;
		gene = new int[chromosomeLength];
		knownSolutionFitness = true;
		solutionFitness = 1.0;

		if (cpu == null)
		{
			cpu = new MPU8051();

			geneLowerBound = new int[chromosomeLength];
			geneUpperBound = new int[chromosomeLength];

			for (int i = 0; i < chromosomeLength; i++)
			{
				geneLowerBound[i] = 0;
				geneUpperBound[i] = cpu.getInstructionCount() - 1;
			}
		}

	}	

   public static final int getChromosomeLength() {
      if (chromosomeLength <= 0) {
         System.err.println("Chromosome: length has not yet been set.");
         System.exit(1);
      }
      return chromosomeLength;
   }

   public final double getFitness() {
      if (fitness < 0) fitness = evalChromosome();
      if (fitness < 0) {
         System.err.println("getFitness: negative fitness so quit");
         System.exit(1);
      }
      return fitness;
   }

   public final double rfitnessGet() { return rfitness; }
   public final double cfitnessGet() { return cfitness; }
   public final void rfitnessSet(double value) { rfitness = value; return; }
   public final void cfitnessSet(double value) { cfitness = value; return; }

   public static final boolean isSolutionFitnessKnown() {
      return knownSolutionFitness;
   }

   public static final double getSolutionFitness() {
      return solutionFitness;
   }

   public              String toString() {return this.toGenotype();}

	// genes are integers

   public void copyChromosome(Chromosome c) { // copy this to c
      Chromosome dc = (Chromosome) c;
      dc.fitness = this.fitness;    // these three
      dc.rfitness = this.rfitness;  // really belong
      dc.cfitness = this.cfitness;  // in superclass
      for (int i = 0; i < chromosomeLength; i++) {
         dc.gene[i] = this.gene[i];
      }
   }

   public Chromosome cloneChromosome() {
      Chromosome theClone = new Chromosome();
      copyChromosome((Chromosome) theClone);
      return (Chromosome) theClone;
   }

   public void initializeChromosomeRandom() {
      for (int i = 0; i <chromosomeLength; i++) {
         gene[i] = MyRandom.intRandom(geneLowerBound[i], geneUpperBound[i]);
      }
      fitness = -1;                // set this again since initializeCR()
      rfitness = 0; cfitness = 0;  // may be called more than once
   }

   public void clearChromosome() {
      for (int i = 0; i <chromosomeLength; i++) gene[i] = 0;
      fitness = -1;
      rfitness = 0; cfitness = 0;
   }

   public int getGene(int i) {
      return (gene[i]);
   }

   public void setGene(int i, int n) {
      gene[i] = n; 
      fitness = -1;
      rfitness = 0; cfitness = 0;
   }

   public void mutateGene(int i) {
      gene[i] = MyRandom.intRandom(geneLowerBound[i], geneUpperBound[i]);
      fitness = -1;
      rfitness = 0; cfitness = 0;
   }

   public String toGenotype() {
      String genotype = "";
//      for (int i = 0; i < chromosomeLength; i++) {
//         genotype += " gene["+i+"]="+gene[i];
//      }
      return genotype;
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

		x = cpu.getObject(0);
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

		x = cpu.getObject(1);
		y = 128;
		x = x - y;
		if (Math.abs(x) > y)
		{
			fitness2 = 0.0;
		}
		else
		{
			fitness2 = Math.sqrt(y*y - x*x) / y; 
		}

		x = cpu.getObject(2); 
		y = 128;
		x = x - y;
		if (Math.abs(x) > y)
		{
			fitness3 = 0.0;
		}
		else
		{
			fitness3 = Math.sqrt(y*y - x*x) / y; 
		}

		x = cpu.getObject(3); 
		y = 128;
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


