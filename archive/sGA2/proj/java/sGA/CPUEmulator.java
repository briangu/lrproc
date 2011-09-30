abstract public class CPUEmulator
{

	boolean			myHaltFlag;
	boolean			myInvalidOpCodeFlag;
	boolean			myTraceFlag;
	int			myPC;
	byte[]			myMemory;
	CPUInstruction[]	myInstructionSet;

	public
	CPUEmulator()
	{
		this(16);
	}

	public
	CPUEmulator(
			int	memorySize)
	{
		myMemory = new byte[memorySize];
		traceOff();
	}

	public
	void
	reset()
	{
		setPC(0);
		setHalt(false);
		setInvalidOpCodeFlag(false);
	}
	
	protected	
	void
	createInstructionSet(
				int	count)
	{
		myInstructionSet = new CPUInstruction[count];
	}

	protected
	int
	getInstructionCount()
	{
		return myInstructionSet.length;
	}

	protected
	void
	setInstruction(
			int		op,
			CPUInstruction	ins)
	{
		myInstructionSet[op] = ins;
	}

	protected
	CPUInstruction
	getInstruction(
			int	op)
	{
		CPUInstruction	ins = null;

		if (isValidOpCode(op))
		{
			ins = myInstructionSet[op];
		}
		else
		{
			setInvalidOpCodeFlag(true);
			halt();
		}

		return(ins); 
	}

	public
	void
	run()
	{
		run(128);
	}

	public
	void
	run(
		int	ic)
	{
		CPUInstruction	ins = null;
		int		op;

		setPC(0);

		for(int i = 0; !isHalted() && i < ic; i++)
		{
			op = getCurrentByte();
			ins = getInstruction(op);
			ins.execute();
			if (tracing())
			{
				System.out.println(ins.toString());
			}
			incPC();
		}
	}

	public
	void
	loadProgram(
		byte[]	program)
	{
		for (int i = 0; i < program.length; i++)
		{
			setMemoryLoc(i, program[i]);
		}
	}

	protected
	byte
	getCurrentByte()
	{
		return(getMemoryLoc(getPC()));
	}

	protected
	byte
	getNextByte()
	{
		incPC();
		return(getCurrentByte());
	}

	public
	boolean
	isValidOpCode(
			int	op)
	{
		return (op >= 0 && op <= getInstructionCount() ? true : false);
	}

	public
	boolean
	isValidAddr(
			int	addr)
	{
		return (addr >= 0 && addr < getMemorySize() ? true : false);
	}

	public
	void
	clearMemory()
	{
		for(int i = 0; i < getMemorySize(); i++)
		{
			setMemoryLoc(i, (byte)0);
		}
	}

	public
	void
	setMemoryLoc(
			int	addr,
			byte	b)
	{
		myMemory[addr] = b;
	}

	public
	byte
	getMemoryLoc(
			int	addr)
	{
		return (myMemory[addr]);	
	}

	public
	int
	getMemorySize()
	{
		return(myMemory.length);
	}

	protected
	void
	setPC(
		int	pc)
	{
		myPC = pc;
	}

	public
	int
	getPC()
	{
		return myPC;
	}

	protected
	void
	incPC()
	{
		setPC((getPC() + 1) % getMemorySize());
	}

	protected
	int
	getRelativeAddr(
			int	addr)
	{
		int x = getPC() + addr;	
		while(x < 0)
		{
			x = getMemorySize() + x;
		}
		return x;
	}

	public
	void
	setTraceFlag(
		boolean	state)	
	{
		myTraceFlag = state;
	}

	protected
	boolean
	getTraceFlag()
	{
		return myTraceFlag;
	}

	public
	void
	traceOn()
	{
		setTraceFlag(true);
	}

	public
	void
	traceOff()
	{
		setTraceFlag(false);
	}

	public
	boolean
	tracing()
	{
		return(getTraceFlag());
	}

	void
	setHalt(	
		boolean	value)
	{
		myHaltFlag = value;
	}

	public
	boolean
	getHalt()
	{
		return myHaltFlag;
	}

	public
	boolean
	isHalted()
	{
		return (getHalt());
	}

	public
	void
	halt()
	{
		setHalt(true);
	}

	public
	boolean
	haltFromInvalidOpCode()
	{
		return(getInvalidOpCodeFlag());
	}

	protected
	boolean
	getInvalidOpCodeFlag()
	{
		return myInvalidOpCodeFlag;
	}


	protected
	void
	setInvalidOpCodeFlag(	
		boolean	value)
	{
		myInvalidOpCodeFlag = value;
	}

	public
	String
	toString()
	{
		return(new String(""));		
	}

};

