package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;


public class bytes_new_zero implements CPUInstruction
{
  public Object[] execute(CPU cpu)
  {
    cpu.ByteRing.insert((byte)0);
    return cpu.isTracing() ? new Object[0] : null;
  }

  public String toString(Object[] data)
  {
    return (new String("bytes_new_zero"));
  }
}
