package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class coord_dec_pos implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Coord c1 = cpu.CoordRing.get();
    Coord c2 = new Coord(c1);
    c2.inc();
    cpu.CoordRing.set(c1);
    return cpu.isTracing() ? new Object[] { c1, c2 } : null;
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("coord_dec_pos coord: %s => %s", data[0], data[1]);
  }
}
