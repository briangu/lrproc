package lrproc.examples;

import java.util.concurrent.Callable;
import lrproc.cpu.instructions.MPU8051;

public class cpuBrain implements Callable<Double>
{
  MPU8051 cpu = new MPU8051();

  public Double call()
  {
    double fitness;
    double fitness1;
    double fitness2;
    double fitness3;
    double fitness4;

    cpu.reset();
    cpu.setMemory(gene);
    cpu.run();

    if (cpu.isHalted() && cpu.getObjectCount() == 4)
    {
      double x;
      double y;

      x = ((Integer) cpu.getObject(0)).intValue();
      y = 128;
      x = x - y;
      if (Math.abs(x) > y)
      {
        fitness1 = 0.0;
      }
      else
      {
        fitness1 = Math.sqrt(y * y - x * x) / y;
      }

      x = ((Integer) cpu.getObject(1)).intValue();
      y = 32;
      x = x - y;
      if (Math.abs(x) > y)
      {
        fitness2 = 0.0;
      }
      else
      {
        fitness2 = Math.sqrt(y * y - x * x) / y;
      }

      x = ((Integer) cpu.getObject(2)).intValue();
      y = 64;
      x = x - y;
      if (Math.abs(x) > y)
      {
        fitness3 = 0.0;
      }
      else
      {
        fitness3 = Math.sqrt(y * y - x * x) / y;
      }

      x = ((Integer) cpu.getObject(3)).intValue();
      y = 16;
      x = x - y;
      if (Math.abs(x) > y)
      {
        fitness4 = 0.0;
      }
      else
      {
        fitness4 = Math.sqrt(y * y - x * x) / y;
      }

      //	_fitness = fitness1*0.5 + fitness2*0.5;
      //	_fitness = fitness1*0.4 + fitness2*0.3 + fitness3*0.3;
      fitness = fitness1 * 0.25 + fitness2 * 0.25 + fitness3 * 0.25 + fitness4 * 0.25;

      if (fitness < 0)
      {
        fitness = 0.0;
      }
    }
    else
    {
      fitness = 0.0;
    }

    return fitness;
  }

  public String toPhenotype()
  {
    cpu.reset();
    cpu.setMemory(gene);
    cpu.traceOn();
    cpu.run();
    cpu.traceOff();

    return cpu.toString();
  }
}

