package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;


public class coord_rot_right implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    int ptr = cpu.CoordRing.getPtr();
    int ptr2 = cpu.CoordRing.shiftRight();
    return cpu.isTracing() ? new Object[] { ptr, ptr2 } : null;
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("coord_rot_right ptr: %s -> %s", data[0], data[1]);
  }
}
