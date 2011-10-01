package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class coord_dec_val implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Coord coord = cpu.CoordRing.get();
    byte b = cpu.Memory.get(coord);
    byte b2 = (byte)(b == 0 ? Byte.MAX_VALUE : (b - 1));
    cpu.Memory.set(coord, b2);
    return new Object[] { coord, b, b2 };
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("coord_dec_val coord: %s val: %s => %s", data[0], data[1], data[2]);
  }
}
