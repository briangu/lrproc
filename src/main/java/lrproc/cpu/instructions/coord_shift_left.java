package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class coord_shift_left implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    int pos = cpu.CoordRing.getPtr();
    cpu.CoordRing.shiftLeft();
    return cpu.isTracing() ? new Object[] { pos, cpu.CoordRing.getPtr() } : null;
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("coord_shift_left ptr: %s -> %s", data[0], data[1]);
  }
}
