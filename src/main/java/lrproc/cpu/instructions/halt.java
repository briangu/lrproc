package lrproc.cpu.instructions;

import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;

public class halt implements CPUInstruction
{
  private CPU _cpu;

  public halt(CPU cpu)
  {
    _cpu = cpu;
  }

  public void execute()
  {
    _cpu.halt();
  }

  public String toString()
  {
    return (new String("halt"));
  }
}
