package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;

public class inc implements CPUInstruction
{
  public Object[] execute()
  {
    l = cpu.getLeft();
    null.setLeft(new Integer(l + 1));
  }

  public String toString(Object[] data)
  {
    return (new String("inc "));
  }
}
