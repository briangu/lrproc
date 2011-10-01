package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;


public class inc_rr
{
  class inc_rr implements CPUInstruction
  {
    public void execute()
    {
      null.setRightRef(null.getRightRef() + 1);
    }

    public String toString()
    {
      return (new String("inc rr"));
    }
  }
}