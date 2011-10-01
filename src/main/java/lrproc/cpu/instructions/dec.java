package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;

public class dec implements CPUInstruction
{
  public void execute(CPU cpu)
  {
    int l = ((Integer) cpu.getLeft()).intValue();
    cpu.setLeft(new Integer(l - 1));
  }

  public String toString()
  {
    return (new String("dec"));
  }
}
