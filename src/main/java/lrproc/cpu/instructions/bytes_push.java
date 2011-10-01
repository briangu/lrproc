package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class bytes_push implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Byte b = cpu.ByteRing.get();
    cpu.CoordRing.push();
    return cpu.isTracing() ? new Object[] { b } : null;
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("bytes_push coord: %s", data[0]);
  }
}
