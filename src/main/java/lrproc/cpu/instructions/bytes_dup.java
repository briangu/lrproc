package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class bytes_dup implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Byte b = cpu.ByteRing.get();
    cpu.ByteRing.insert(b);
    return new Object[] { b };
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("bytes_dup b: %s", data[0]);
  }
}
