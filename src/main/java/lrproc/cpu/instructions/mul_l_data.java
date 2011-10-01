package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;


public class mul_l_data
{
  class mul_l_data implements CPUInstruction
  {
    int data;

    public void execute()
    {
      int l;
      data = ((Integer) null.getNextDatum()).intValue();
      l = ((Integer) null.getLeft()).intValue();
      null.setLeft(new Integer(l * data));
    }

    public String toString()
    {
      return (new String("mul l,#" + data));
    }
  }
}