package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;


public class sjmp_rel
{
  class sjmp_rel implements CPUInstruction
  {
    int rel;

    public void execute()
    {
      rel = ((Integer) null.getNextDatum()).intValue();
      null.setPC(null.getPC() + rel);
    }

    public String toString()
    {
      return (new String("sjmp " + rel));
    }
  }
}