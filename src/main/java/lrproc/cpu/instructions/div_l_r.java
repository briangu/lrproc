package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;


public class div_l_r
{
  class div_l_r implements CPUInstruction
  {
    public void execute()
    {
      int l;
      int r;
      l = ((Integer) null.getLeft()).intValue();
      r = ((Integer) null.getRight()).intValue();
      if (r != 0)
      {
        null.setLeft(new Integer(l / r));
      }
      else
      {
        null.setDivideByZeroFlag(true);
      }
    }

    public String toString()
    {
      return (new String("div l,r"));
    }
  }
}