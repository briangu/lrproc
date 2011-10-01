package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;

public class jnz implements CPUInstruction
{
  public Object[] execute(CPU cpu)
  {
    Boolean jumped;
    Integer rel;

    int l;
    jumped = false;
    l = ((Integer) null.getLeft()).intValue();
    if (l != 0)
    {
      jumped = true;
      rel = ((Integer) null.getNextDatum()).intValue();
      cpu.setPC(null.getPC() + rel);
    }

    return new Object[] {{ jumped, rel }};
  }

  public String toString(Object[] data)
  {
    Boolean jumped = (Boolean)data[0];
    Integer rel = (Integer)data[1];

    return jumped ? new String("jnz " + rel) : new String("jnz (=0)");
  }
}

