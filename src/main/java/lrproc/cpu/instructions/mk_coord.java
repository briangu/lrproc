package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


public class mk_coord implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Byte x = cpu.popByte();
    Byte y = cpu.popByte();
    Byte z = cpu.popByte();
    Coord coord = new Coord(x,y,z);
    cpu.insertCoord(coord);

    return cpu.isTracing() ? new Object[] { x, y, z} : null;
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("mk_coord x:%s y:%s z:%s", data[0], data[1], data[2]);
  }
}
