package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;


public class bytes_inc_val implements CPUInstruction
{
  public Object[] execute(CPU cpu)
  {
    Byte b = cpu.ByteRing.get();
    cpu.ByteRing.set(b == Byte.MAX_VALUE ? Byte.MIN_VALUE : (byte)(b + 1));
    return cpu.isTracing() ? new Object[] { b } : null;
  }

  public String toString(Object[] data)
  {
    return String.format("bytes_inc_val %s -> %s", data[0], Integer.toString((((Integer)data[0]) + 1)));
  }
}
