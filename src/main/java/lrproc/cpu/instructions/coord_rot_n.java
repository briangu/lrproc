package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;


public class coord_rot_n implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Byte b = cpu.ByteRing.get();
    int ptr = cpu.CoordRing.getPtr();
    int ptr2 = cpu.CoordRing.rot(b);
    return cpu.isTracing() ? new Object[] { b, ptr, ptr2 } : null;
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("coord_rot_n b: %s, ptr: %s => %s", data[0], data[1], data[2]);
  }
}
