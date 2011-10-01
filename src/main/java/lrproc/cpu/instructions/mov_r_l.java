package lrproc.cpu.instructions;

import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;

class mov_r_l implements CPUInstruction
{
  public void execute(CPU cpu)
  {
    int l;
    l = ((Integer) cpu.getLeft()).intValue();
    cpu.setRight(new Integer(l));
  }

  public String toString()
  {
    return (new String("mov r,l"));
  }
}
