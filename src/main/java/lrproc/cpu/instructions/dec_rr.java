package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;


public class dec_rr
{
  class dec_rr implements CPUInstruction
  {
    public void execute()
    {
      null.setRightRef(null.getRightRef() - 1);
    }

    public String toString()
    {
      return (new String("dec rr"));
    }
  }
}