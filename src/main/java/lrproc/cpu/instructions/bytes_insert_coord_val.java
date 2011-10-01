package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class bytes_insert_coord_val implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Coord coord = cpu.CoordRing.get();
    Byte b = cpu.Memory.get(coord);
    cpu.ByteRing.insert(b);
    return cpu.isTracing() ? new Object[] { coord, b } : null;
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("bytes_insert_coord_val coord: %s b: %s", data[0], data[1]);
  }
}
