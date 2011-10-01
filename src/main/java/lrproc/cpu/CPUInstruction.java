package lrproc.cpu;


public interface CPUInstruction
{
  public Object[] execute(CPU cpu);
  public String toString(Object[] data);
}

