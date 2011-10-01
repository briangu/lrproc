package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;


public class mov_rr_r
{
  class mov_rr_r implements CPUInstruction
  {
    public void execute()
    {
      null.setRightRef(((Integer) null.getRight()).intValue());
    }

    public String toString()
    {
      return (new String("mov rr,r"));
    }
  }
}