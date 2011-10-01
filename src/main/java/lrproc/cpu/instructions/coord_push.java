package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class coord_push implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Coord coord = cpu.CoordRing.get();
    cpu.CoordRing.push();
    return cpu.isTracing() ? new Object[] { coord } : null;
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("coord_push coord: %s", data[0]);
  }
}
