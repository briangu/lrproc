package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;


public class cobj implements CPUInstruction
{
  public void execute(CPU cpu)
  {
    cpu.addObject(new Integer(0));
  }

  public String toString()
  {
    return (new String("cobj"));
  }
}
