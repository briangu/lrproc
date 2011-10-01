package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;


public class mov_lr_l
{
  class mov_lr_l implements CPUInstruction
  {
    public void execute()
    {
      null.setLeftRef(((Integer) null.getLeft()).intValue());
    }

    public String toString()
    {
      return (new String("mov lr,l"));
    }
  }
}