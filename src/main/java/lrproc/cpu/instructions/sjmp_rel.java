package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;
import lrproc.cpu.Coord;


class sjmp_rel implements CPUInstruction
{
  public void execute(CPU cpu)
  {
    Coord coord = cpu.CoordRing.get();
    cpu.ProgramCounter.inc(coord);
  }

  public String toString()
  {
    return (new String("sjmp " + rel));
  }
}
