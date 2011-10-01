package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;


public class mov_lr_r
{
  class mov_lr_r implements CPUInstruction
  {
    public void execute()
    {
      null.setLeftRef(((Integer) null.getRight()).intValue());
    }

    public String toString()
    {
      return (new String("mov lr,r"));
    }
  }
}