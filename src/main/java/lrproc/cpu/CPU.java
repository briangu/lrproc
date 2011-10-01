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
  Stack<Coord> _coordStack;
  List<Coord> _coordRing;
  int _coordPtr;

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
    _memory = memory;
  }

  public void reset()
  {
    setHalt(false);
    traceOff();

    _pc = new Coord((byte)0, (byte)0, (byte)0);
    _coordStack = new Stack<Coord>();
    _coordRing = new ArrayList<Coord>();
    _coordPtr = 0;
  }

  public void setMemory(Memory memory)
  {
    _memory = memory;
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

  public void insertCoord(Coord coord)
  {
    _coordRing.add(_coordPtr, coord);
  }

  public void removeCurrentCoord()
  {
    Coord coord = _coordRing.get(_coordPtr);
    if (coord == _pc) return;
    _coordRing.remove(_coordPtr);
  }

  public void pushCoord()
  {
    _coordStack.push(getCurrentCoord());
  }

  public Coord popCoord()
  {
    return _coordStack.empty() ? _pc : _coordStack.pop();
  }

  public Coord getCurrentCoord()
  {
    return _coordRing.get(_coordPtr);
  }

  public void shiftLeftCoordPtr()
  {
    if (_coordPtr == 0)
    {
      _coordPtr = _coordRing.size() - 1;
    }
    else
    {
      _coordPtr--;
    }
  }

  public void shiftRightCoordPtr()
  {
    _coordPtr = (_coordPtr + 1) % _coordRing.size();
  }
}
