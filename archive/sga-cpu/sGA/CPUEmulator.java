public class CPUEmulator
{

	boolean	myHaltFlag;
	int	myAcc;	
	int	myPC;
	boolean	myInvalidOpFlag;
	int[]	myMemory;

	public
	CPUEmulator()
	{
		this(16);
	}

	public
	CPUEmulator(
			int	memorySize)
	{
		reset();
		myMemory = new int[memorySize];
	}

	public
	void
	reset()
	{
		setAcc(0);
		setPC(0);
		setHalt(false);
		setInvalidOpFlag(false);
	}
	
	private
	void
	evalCurrentOpCode()
	{
		int	b;
		int	op;

		op = getCurrentByte();

	//	System.out.println(op);

		switch (op)
		{
			case 1:
				b = getNextByte();
			//	System.out.println(b);
				setAcc(getAcc() + b);
				break;
			case 2:
				b = getNextByte();
			//	System.out.println(b);
				setAcc(getAcc() - b);
				break;
			case 3:
				b = getNextByte();
			//	System.out.println(b);
				setAcc(getAcc() * b);
				break;
			case 4:
				b = getNextByte();
			//	System.out.println(b);
				setAcc(getAcc() / b);
				break;
			default:
				setInvalidOpFlag(true);
				halt();
				break;
		}
	}

	public
	void
	run(
		int	ic)
	{
		int i = 0;

		setPC(0);
		while(!isHalted() && i < ic)
		{
			evalCurrentOpCode();
			incPC();
			i++;
		}
	}

	public
	void
	loadProgram(
		int[]	program)
	{
		for (int i = 0; i < program.length; i++)
		{
			setMemoryLoc(i, program[i]);
		}
	}

	private
	int
	getCurrentByte()
	{
		return(getMemoryLoc(getPC()));
	}

	private
	int
	getNextByte()
	{
		incPC();
		return(getCurrentByte());
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
			setMemoryLoc(i, 0);
		}
	}

	public
	void
	setMemoryLoc(
			int	addr,
			int	b)
	{
		myMemory[addr] = b;
	}

	public
	int
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

	private
	void
	incPC()
	{
		setPC((getPC() + 1) % getMemorySize());
	}

	private
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

	private
	void
	setAcc(	
		int	value)
	{
		myAcc = value;
	}

	public
	int
	getAcc()
	{
		return myAcc;
	}


	private
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
	haltFromInvalidOp()
	{
		return(getInvalidOpFlag());
	}

	private
	boolean
	getInvalidOpFlag()
	{
		return myInvalidOpFlag;
	}


	private
	void
	setInvalidOpFlag(	
		boolean	value)
	{
		myInvalidOpFlag = value;
	}

}

