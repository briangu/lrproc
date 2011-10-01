package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;


class add_l_r implements CPUInstruction
{
  public String execute(CPU cpu)
  {
    int l = ((Integer) cpu.getLeft()).intValue();
    int r = ((Integer) cpu.getRight()).intValue();
    cpu.setLeft(new Integer(l + r));
    return toString();
  }

  public String toString()
  {
    return "add l,r";
  }
}
