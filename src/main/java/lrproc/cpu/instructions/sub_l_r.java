package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;


public class sub_l_r
{
  class sub_l_r implements CPUInstruction
  {
    public void execute()
    {
      int l;
      int r;
      l = ((Integer) null.getLeft()).intValue();
      r = ((Integer) null.getRight()).intValue();
      null.setLeft(new Integer(l - r));
    }

    public String toString()
    {
      return (new String("add l,r"));
    }
  }
}