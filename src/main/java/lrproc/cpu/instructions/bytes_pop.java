package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class bytes_pop implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Byte b = cpu.ByteRing.pop();
    cpu.ByteRing.insert(b);
    return new Object[] { b };
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("bytes_pop b: %s", data[0]);
  }
}
