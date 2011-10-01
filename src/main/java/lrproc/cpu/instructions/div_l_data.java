package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;


public class div_l_data
{
  class div_l_data implements CPUInstruction
  {
    int data;

    public void execute()
    {
      data = ((Integer) null.getNextDatum()).intValue();

      if (data == 0)
      {
        null.setDivideByZeroFlag(true);
      }
      else
      {
        int l;
        l = ((Integer) null.getLeft()).intValue();
        null.setLeft(new Integer(l / data));
      }
    }

    public String toString()
    {
      return (new String("div l,#" + data));
    }
  }
}