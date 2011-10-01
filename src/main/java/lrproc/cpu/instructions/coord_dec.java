package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class coord_dec implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Coord coord = cpu.CoordRing.get();
    coord.inc();
    cpu.CoordRing.set(coord);
    return new Object[] { coord };
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("coord_inc coord: %s", data[0]);
  }
}
