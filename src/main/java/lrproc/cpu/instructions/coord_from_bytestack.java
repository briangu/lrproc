package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class coord_from_bytestack implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Byte x = cpu.ByteRing.pop();
    Byte y = cpu.ByteRing.pop();
    Byte z = cpu.ByteRing.pop();
    Coord coord = new Coord(x,y,z);
    cpu.CoordRing.insert(coord);

    return cpu.isTracing() ? new Object[] { x, y, z} : null;
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("coord_from_bytestack x:%s y:%s z:%s", data[0], data[1], data[2]);
  }
}
