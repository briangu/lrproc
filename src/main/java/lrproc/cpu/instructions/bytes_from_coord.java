package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class bytes_from_coord implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Coord coord = cpu.removeCurrentCoord();
    cpu.pushByte(coord.X);
    cpu.pushByte(coord.Y);
    cpu.pushByte(coord.Z);
    return new Object[] { coord };
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("bytes_from_coord coord: %s", data[0]);
  }
}
