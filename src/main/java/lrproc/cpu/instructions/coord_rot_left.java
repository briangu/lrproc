package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class coord_rot_left implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    int ptr = cpu.CoordRing.getPtr();
    int ptr2 = cpu.CoordRing.shiftLeft();
    return cpu.isTracing() ? new Object[] { ptr, ptr2 } : null;
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("coord_rot_left ptr: %s -> %s", data[0], data[1]);
  }
}
