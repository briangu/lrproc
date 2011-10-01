package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;


public class mov_rr_l
{
  class mov_rr_l implements CPUInstruction
  {
    public void execute()
    {
      null.setRightRef(((Integer) null.getLeft()).intValue());
    }

    public String toString()
    {
      return (new String("mov rr,l"));
    }
  }
}