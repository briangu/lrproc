package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class bytes_add_to_coord_val implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Byte b = cpu.ByteRing.get();
    Coord coord = cpu.CoordRing.get();
    Byte b2 = cpu.Memory.get(coord);
    Byte b3 = (byte)((b2 + b) & 0xFF);
    cpu.Memory.set(coord, b3);
    return cpu.isTracing() ? new Object[] { coord, b, b2, b3 } : null;
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("bytes_add_to_coord_val coord: %s @coord: %s b2: %s b3: %s", data[0], data[1], data[2], data[3]);
  }
}
