package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;


public class ljmp_addr
{
  class ljmp_addr implements CPUInstruction
  {
    int addr;

    public void execute()
    {
      addr = ((Integer) null.getNextDatum()).intValue();
      null.setPC(addr);
    }

    public String toString()
    {
      return (new String("ljmp " + addr));
    }
  }
}