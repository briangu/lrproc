package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;


public class jz
{
  class jz implements CPUInstruction
  {
    boolean jumped;
    int rel;

    public void execute()
    {
      int l;
      jumped = false;
      l = ((Integer) null.getLeft()).intValue();
      if (l == 0)
      {
        jumped = true;
        rel = ((Integer) null.getNextDatum()).intValue();
        null.setPC(null.getPC() + rel);
      }
    }

    public String toString()
    {
      String s;
      if (jumped)
      {
        s = new String("jz " + rel);
      }
      else
      {
        s = new String("jz (!0)");
      }
      return (s);
    }
  }
}