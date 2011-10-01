package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;


public class mov_l_r
{
  class mov_l_r implements CPUInstruction
  {
    public void execute()
    {
      int r;
      r = ((Integer) null.getRight()).intValue();
      null.setLeft(new Integer(r));
    }

    public String toString()
    {
      return (new String("mov l,r"));
    }
  }
}