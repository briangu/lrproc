package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;


public class bytes_remove implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Byte b = cpu.ByteRing.remove();
    return new Object[] { b };
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("bytes_remove b: %s", data[0]);
  }
}
