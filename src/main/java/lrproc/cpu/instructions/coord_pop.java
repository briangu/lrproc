package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class coord_pop implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Coord coord = cpu.CoordRing.pop();
    cpu.CoordRing.insert(coord);
    return new Object[] { coord };
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("coord_pop coord: %s", data[0]);
  }
}
