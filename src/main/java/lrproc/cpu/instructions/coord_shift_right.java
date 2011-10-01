package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;


public class coord_shift_right implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    int pos = cpu.CoordRing.getPtr();
    cpu.CoordRing.shiftRight();
    return cpu.isTracing() ? new Object[] { pos, cpu.CoordRing.getPtr() } : null;
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("coord_shift_right ptr: %s -> %s", data[0], data[1]);
  }
}
