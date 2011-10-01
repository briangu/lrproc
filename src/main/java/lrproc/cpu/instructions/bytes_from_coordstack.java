package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class bytes_from_coordstack implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Coord coord = cpu.CoordRing.remove();
    cpu.ByteRing.insert(coord.X);
    cpu.ByteRing.insert(coord.Y);
    cpu.ByteRing.insert(coord.Z);
    return new Object[] { coord };
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("bytes_from_coordstack coord: %s", data[0]);
  }
}
