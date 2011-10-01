package lrproc.cpu.instructions;


import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import lrproc.cpu.CPU;
import lrproc.cpu.CPUInstruction;


public class NOP implements CPUInstruction
{
  public static NOP INSTANCE = new NOP();

  @Override
  public Byte[] execute(CPU cpu)
  {
    return new Byte[0];
  }

  public String toString(byte[] data)
  {
    return "NOP";
  }
}
