import java.util.Vector;

abstract public class CPUEmulator
{

	boolean			myHaltFlag;
	boolean			myInvalidOpCodeFlag;
	boolean			myTraceFlag;
	boolean                 myDivideByZeroFlag;
	int			myPC;
	int			myLeftRef;
	int			myRightRef;
	int[]			myObjects;
	int			myObjectCount;
	Vector			myInstructionSet;
	int[]			myMemory;

	public
	CPUEmulator()
	{
		myInstructionSet = new Vector();
		myObjectCount = 0;
		myObjects = new int[256];
		myMemory = null;
	}

	public 
	void
	reset()
	{
//		setPC(0);
		setHalt(false);
		setInvalidOpCodeFlag(false);
		traceOff();
		setDivideByZeroFlag(false);
		myObjectCount = 0;
		myObjects = new int[256];
	}

	protected final
	int
	getInstructionCount()
	{
		return myInstructionSet.size();
	}

	protected final
	void
	addInstruction(
			CPUInstruction	ins)
	{
		myInstructionSet.addElement(ins);
	}

	protected final
	void
	setInstruction(
			int		op,
			CPUInstruction	ins)
	{
		myInstructionSet.setElementAt(ins, op);
	}

	protected final
	CPUInstruction
	getInstruction(
			int	op)
	{
		CPUInstruction	ins = null;

		if (isValidOpCode(op))
		{
			ins = (CPUInstruction)myInstructionSet.elementAt(op);
		}
		else
		{
			setInvalidOpCodeFlag(true);
			halt();
		}

		return(ins); 
	}

	protected final
	int
	getCurrentDatum()
	{
		return(getMemoryLoc(getPC()));
	}

	protected final
	int
	getNextDatum()
	{
		incPC();
		return(getCurrentDatum());
	}

	public final
	boolean
	isValidOpCode(
			int	op)
	{
		return (op >= 0 && op <= getInstructionCount() ? true : false);
	}

	public final
	boolean
	isValidAddr(
			int	addr)
	{
		return (addr >= 0 && addr < getMemorySize() ? true : false);
	}

	public final
	void
	setMemoryLoc(
			int	o,
			int	addr)
	{
		myMemory[addr] = o;
	}

	public final
	int
	getMemoryLoc(
			int	addr)
	{
		return (myMemory[addr]);	
	}

	public final
	int
	getMemorySize()
	{
		return(myMemory.length);
	}

	protected final
	void
	setPC(
		int	pc)
	{
		myPC = pc % getMemorySize();
		while(myPC < 0)
		{
			myPC += getMemorySize();
		}
	}

	public final
	int
	getPC()
	{
		return myPC;
	}

	protected final
	void
	incPC()
	{
		setPC(getPC() + 1);
	}

	protected final
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

	public final
	void
	setTraceFlag(
		boolean	state)	
	{
		myTraceFlag = state;
	}

	protected final
	boolean
	getTraceFlag()
	{
		return myTraceFlag;
	}

	public final 
	void
	traceOn()
	{
		setTraceFlag(true);
	}

	public final
	void
	traceOff()
	{
		setTraceFlag(false);
	}

	public final
	boolean
	tracing()
	{
		return(getTraceFlag());
	}

	private final
	void
	setHalt(	
		boolean	value)
	{
		myHaltFlag = value;
	}

	public final
	boolean
	getHalt()
	{
		return myHaltFlag;
	}

	public final
	boolean
	isHalted()
	{
		return (getHalt());
	}

	public final
	void
	halt()
	{
		setHalt(true);
	}

	public final
	boolean
	haltFromInvalidOpCode()
	{
		return(getInvalidOpCodeFlag());
	}

	protected final
	boolean
	getInvalidOpCodeFlag()
	{
		return myInvalidOpCodeFlag;
	}


	protected final
	void
	setInvalidOpCodeFlag(	
		boolean	value)
	{
		myInvalidOpCodeFlag = value;
	}

        public final
        void
        loadProgram(
                        int[]        program)
        {
		myMemory = program;	
        }

	public final
	void
	run()
	{
		run(128);
	}

        public final
	void
	run(
              	int     ic)
        {
                CPUInstruction  ins = null;
                int             op;
		String		s;

                setPC(0);

                for(int i = 0; !isHalted() && i < ic; i++)
                {
                        op = getCurrentDatum();
                        ins = getInstruction(op);
                        ins.execute();
                        if (tracing())
                        {
                                System.out.println(ins.toString() + "\t\t" + toString());
                        }
                        incPC();
                }
        }

	public final
	String
	toString()
	{
		int	l;
		int	r;
		String	s;

		s = new String("lr="+getLeftRef()+" rr="+getRightRef());
		
		for (int i = 0; i < getObjectCount(); i++)
		{
			s += " "+getObject(i);
		}

		return(s);
	}	

	protected final
	void
	addObject(
		int	o)
	{
		myObjects[++myObjectCount] = o;
	}
	
	protected final
	void
	setObject(
		int	o,
		int	i)
	{
		myObjects[i] = o;
	}
	
	protected final
	int
	getObject(
		int	i)
	{
		return myObjects[i];
	}

	protected final
	
	int
	getObjectCount()
	{
		return myObjectCount;
	}
	
	protected final
	void
	setLeftRef(
		int	i)
	{
		myLeftRef = i % getObjectCount();
		while(myLeftRef < 0)
		{
			myLeftRef += getObjectCount();
		}
	}

	public final
	int
	getLeftRef()
	{
		return myLeftRef; 
	}

	protected final
	void
	setRightRef(
		int	i)
	{
		myRightRef = i % getObjectCount();
		while(myRightRef < 0)
		{
			myRightRef += getObjectCount();
		}
	}

	public final
	int
	getRightRef()
	{
		return myRightRef; 
	}

	protected final
	void
	setLeft(
		int	o)
	{
		setObject(o, getLeftRef());
	}

	public final
	int
	getLeft()
	{
		return getObject(getLeftRef()); 
	}

	protected final
	void
	setRight(	
		int	o)
	{
		setObject(o, getRightRef());
	}

	public final
	int
	getRight()
	{
		return getObject(getRightRef()); 
	}

	protected final
	void
	setDivideByZeroFlag(	
		boolean	value)
	{
		myDivideByZeroFlag = value;
	}

	public final
	boolean
	getDivideByZeroFlag()
	{
		return myDivideByZeroFlag; 
	}

}


