package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;


public class inc_lr implements CPUInstruction
{
  public Byte[] execute(CPU cpu)
  {
    cpu.setLeftRef(null.getLeftRef() + 1);
  }

  public String toString()
  {
    return (new String("inc lr"));
  }
}
