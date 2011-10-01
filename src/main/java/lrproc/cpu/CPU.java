package lrproc.cpu;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import lrproc.cpu.instructions.NOP;


public class CPU
{
  boolean _haltFlag;
  boolean _isTracing;
  List<TraceReport> _traceReports = new ArrayList<TraceReport>();

  final CPUInstruction[] _instructionSet;

  Memory _memory;
  Coord _pc;
  Ring<Coord> _coordRing;
  Ring<Byte> _byteRing;

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

    _pc = new Coord((byte)0, (byte)0, (byte)0);

    _memory = memory;
    _coordRing = new Ring(_pc);
    _byteRing = new Ring(0);

    _coordRing.insert(_pc);
  }

  public Memory getMemory()
  {
    return _memory;
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

  public void execute(int maxSteps)
  {
    for (int i = 0; !isHalted() && i < maxSteps; i++)
    {
      byte op = _memory.get(_pc);
      CPUInstruction instruction = getInstruction(op);
      execute(instruction);
      _pc.advance();
    }
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
      _traceReports.add(new TraceReport(instruction, data));
    }
    else
    {
      instruction.execute(this);
    }
  }
}
