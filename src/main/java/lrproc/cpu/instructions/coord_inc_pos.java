package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class coord_inc_pos implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Coord c1 = cpu.CoordRing.get();
    Coord c2 = new Coord(c1);
    c2.inc();
    cpu.CoordRing.set(c2);
    return new Object[] { c1, c2 };
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("coord_inc_pos coord: %s => %s", data[0], data[1]);
  }
}
