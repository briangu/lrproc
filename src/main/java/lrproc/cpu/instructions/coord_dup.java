package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class coord_dup implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Coord coord = cpu.CoordRing.get();
    cpu.CoordRing.insert(coord);
    return new Object[] { coord };
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("coord_dup coord: %s", data[0]);
  }
}
