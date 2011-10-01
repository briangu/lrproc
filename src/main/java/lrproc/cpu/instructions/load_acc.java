package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;

public class load_acc implements CPUInstruction
{
  @Override
  public Object[] execute(CPU cpu)
  {
    Coord pc = cpu.getPC();
    byte acc = cpu.getMemory().get(pc);
    cpu.setAcc(acc);
    return new Object[] { pc, acc };
  }

  @Override
  public String toString(Object[] data)
  {
    return String.format("load acc pc = %s, acc = %s", data[0].toString(), data[1].toString());
  }
}
