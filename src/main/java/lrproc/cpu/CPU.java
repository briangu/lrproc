package lrproc.cpu;


import java.util.ArrayList;
import java.util.List;
import lrproc.cpu.instructions.NOP;
import org.apache.log4j.Logger;


public class CPU
{
  Logger log = Logger.getLogger(CPU.class);

  final CPUInstruction[] _instructionSet;

  boolean _haltFlag;
  boolean _isTracing;
  List<TraceReport> _traceReports = new ArrayList<TraceReport>();

  public Memory Memory;
  public Coord ProgramCounter;
  public Ring<Coord> CoordRing;
  public Ring<Byte> ByteRing;

  private class TraceReport
  {
    public Object[] data;
    public CPUInstruction instruction;

    public TraceReport(CPUInstruction instruction, Object[] data)
    {
      this.data = data;
      this.instruction = instruction;
    }

    public String toString()
    {
      return instruction.toString(data);
    }
  }

  public CPU(CPUInstruction[] instructions, Memory memory)
  {
    _instructionSet = instructions;
    reset(memory);
  }

  public void reset(Memory memory)
  {
    setHalt(false);
    traceOff();

    ProgramCounter = new Coord((byte)0, (byte)0, (byte)0);
    Memory = memory;
    CoordRing = new Ring(ProgramCounter);
    ByteRing = new Ring(0);
  }

  public CPUInstruction getInstruction(int op)
  {
    if (_instructionSet.length >= op)
    {
      return NOP.INSTANCE;
    }

    return _instructionSet[op];
  }

  public void traceOn()
  {
    _isTracing = true;
    _traceReports.clear();
  }

  public void traceOff()
  {
    _isTracing = false;
    _traceReports.clear();
  }

  public boolean isTracing()
  {
    return _isTracing;
  }

  public String generateTraceReport()
  {
    StringBuilder sb = new StringBuilder();

    for (TraceReport report : _traceReports)
    {
      sb.append(report.toString());
      sb.append("\n");
    }

    return sb.toString();
  }

  void setHalt(boolean value)
  {
    _haltFlag = value;
  }

  public boolean isHalted()
  {
    return _haltFlag;
  }

  public void halt()
  {
    setHalt(true);
  }

  public void step(int maxSteps)
  {
    for (int i = 0; !isHalted() && i < maxSteps; i++)
    {
      step();
    }
  }

  public void step()
  {
    byte op = Memory.get(ProgramCounter);
    CPUInstruction instruction = getInstruction(op);
    execute(instruction);
    ProgramCounter.inc();
  }

  public void execute(CPUInstruction[] instructions)
  {
    for (int i = 0; !isHalted() && i < instructions.length; i++)
    {
      execute(instructions[i]);
    }
  }

  private void execute(CPUInstruction instruction)
  {
    if (_isTracing)
    {
      Object[] data = instruction.execute(this);
      TraceReport report = new TraceReport(instruction, data);
      _traceReports.add(report);
      log.trace(report);
    }
    else
    {
      instruction.execute(this);
    }
  }
}
