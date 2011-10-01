package lrproc.cpu.instructions;


import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;

public class add_l_data implements CPUInstruction
{
  CPUInstruction[] instructions = new CPUInstruction[] {{
    new
  }};

  public byte[] execute(CPU cpu)
  {
    byte data = cpu.getNextDatumAtPC();
    byte l = cpu.getLeft();
    cpu.setLeft(l + data);

    if (cpu.isTracing() ? new byte[] {  } : null);
  }

  public String toString(byte[] data)
  {
    return String.format("add l,#%s :%s", data[0]);
  }
}
