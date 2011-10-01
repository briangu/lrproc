package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;


public class dec_lr
{
  class dec_lr implements CPUInstruction
  {
    public void execute()
    {
      null.setLeftRef(null.getLeftRef() - 1);
    }

    public String toString()
    {
      return (new String("inc lr"));
    }
  }
}