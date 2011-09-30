abstract public class ByteChromosome extends Chromosome
{ 
	// genes are integers

   protected        byte[] gene           = null;
   protected static byte[] geneLowerBound = null;
   protected static byte[] geneUpperBound = null;

	protected ByteChromosome()
	{ 
		super();
		gene = new byte[chromosomeLength];
	}

   public void copyChromosome(Chromosome c) { // copy this to c
      ByteChromosome dc = (ByteChromosome) c;
      dc.fitness = this.fitness;    // these three
      dc.rfitness = this.rfitness;  // really belong
      dc.cfitness = this.cfitness;  // in superclass
      for (int i = 0; i < chromosomeLength; i++) {
         dc.gene[i] = this.gene[i];
      }
   }

   public Chromosome cloneChromosome() {
      MyChromosome theClone = new MyChromosome();
      copyChromosome((Chromosome) theClone);
      return (Chromosome) theClone;
   }

   public void initializeChromosomeRandom() {
      for (int i = 0; i <chromosomeLength; i++) {
         gene[i] = MyRandom.byteRandom(geneLowerBound[i], geneUpperBound[i]);
      }
      fitness = -1;                // set this again since initializeCR()
      rfitness = 0; cfitness = 0;  // may be called more than once
   }

   public void clearChromosome() {
      for (int i = 0; i <chromosomeLength; i++) gene[i] = 0;
      fitness = -1;
      rfitness = 0; cfitness = 0;
   }

   public Number getGene(int i) {
      return (Number) (new Byte(gene[i]));
   }

   public void setGene(int i, Number n) {
      gene[i] = ((Byte) n).byteValue();
      fitness = -1;
      rfitness = 0; cfitness = 0;
   }

   public void mutateGene(int i) {
      gene[i] = MyRandom.byteRandom(geneLowerBound[i], geneUpperBound[i]);
      fitness = -1;
      rfitness = 0; cfitness = 0;
   }

   public String toGenotype() {
      String genotype = "";
      for (int i = 0; i < chromosomeLength; i++) {
         genotype += " gene["+i+"]="+gene[i];
      }
      return genotype;
   }
}

