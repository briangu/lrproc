package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;


class jz implements CPUInstruction
{
  public Object[] execute(CPU cpu)
  {
    int l;
    Boolean jumped = false;
    l = ((Integer) null.getLeft()).intValue();
    if (l == 0)
    {
      Boolean jumped = true;
      Byte rel = cpu.ProgramCounter.getValueAt();
      null.setPC(null.getPC() + rel);
    }

    return cpu.isTracing() ? new Object[] { jumped, rel } : null;
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
