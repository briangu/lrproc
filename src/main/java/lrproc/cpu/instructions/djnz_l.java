package lrproc.cpu.instructions;


import lrproc.cpu.CPUInstruction;


public class djnz_l
{
  class djnz_l implements CPUInstruction
  {
    boolean jumped;
    int rel;

    public void execute()
    {
      int l;
      jumped = false;
      l = ((Integer) null.getLeft()).intValue() - 1;
      null.setLeft(new Integer(l));
      if (l != 0)
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
        s = new String("djnz l," + rel);
      }
      else
      {
        s = new String("djnz (left == 0)");
      }
      return (s);
    }
  }
}