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
	Vector			myObjects;
	Vector			myInstructionSet;
	Object[]		myMemory;

	public
	CPUEmulator()
	{
		myInstructionSet = new Vector();
		myObjects = new Vector();
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
		myObjects = new Vector();
	}

	protected
	void
	createMemory(
			int	size)
	{
		myMemory = new Object[size];
	}
	
	protected
	int
	getInstructionCount()
	{
		return myInstructionSet.size();
	}

	protected
	void
	addInstruction(
			CPUInstruction	ins)
	{
		myInstructionSet.addElement(ins);
	}

	protected
	void
	setInstruction(
			int		op,
			CPUInstruction	ins)
	{
		myInstructionSet.setElementAt(ins, op);
	}

	protected
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

	protected
	Object
	getCurrentDatum()
	{
		return(getMemoryLoc(getPC()));
	}

	protected
	Object
	getNextDatum()
	{
		incPC();
		return(getCurrentDatum());
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
	setMemoryLoc(
			Object	o,
			int	addr)
	{
		myMemory[addr] = o;
	}

	public
	Object
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
		myPC = pc % getMemorySize();
		while(myPC < 0)
		{
			myPC += getMemorySize();
		}
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
		setPC(getPC() + 1);
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
        void
        loadProgram(
                        int[]        program)
        {
		createMemory(program.length);
                for(int i = 0; i < program.length; i++)
                {
                        setMemoryLoc(new Integer(program[i]), i);
                }
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
              	int     ic)
        {
                CPUInstruction  ins = null;
                int             op;
		String		s;

                setPC(0);

                for(int i = 0; !isHalted() && i < ic; i++)
                {
                        op = ((Integer)getCurrentDatum()).intValue();
                        ins = getInstruction(op);
                        ins.execute();
                        if (tracing())
                        {
                                System.out.println(ins.toString() + "\t\t" + toString());
                        }
                        incPC();
                }
        }

	public
	String
	toString()
	{
		int	l;
		int	r;
		String	s;

		s = new String("lr="+getLeftRef()+" rr="+getRightRef());
		
		for (int i = 0; i < getObjectCount(); i++)
		{
			s += " "+((Integer)getObject(i)).intValue();
		}

		return(s);
	}	

	protected
	void
	addObject(
		Object	o)
	{
		myObjects.addElement(o);
	}
	
	protected
	void
	setObject(
		Object	o,
		int	i)
	{
		myObjects.setElementAt(o, i);
	}
	
	protected
	Object
	getObject(
		int	i)
	{
		return myObjects.elementAt(i);
	}

	protected
	int
	getObjectCount()
	{
		return myObjects.size();
	}
	
	protected
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

	public
	int
	getLeftRef()
	{
		return myLeftRef; 
	}

	protected
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

	public
	int
	getRightRef()
	{
		return myRightRef; 
	}

	protected
	void
	setLeft(
		Object	o)
	{
		setObject(o, getLeftRef());
	}

	public
	Object
	getLeft()
	{
		return getObject(getLeftRef()); 
	}

	protected
	void
	setRight(	
		Object	o)
	{
		setObject(o, getRightRef());
	}

	public
	Object
	getRight()
	{
		return getObject(getRightRef()); 
	}

	protected
	void
	setDivideByZeroFlag(	
		boolean	value)
	{
		myDivideByZeroFlag = value;
	}

	public
	boolean
	getDivideByZeroFlag()
	{
		return myDivideByZeroFlag; 
	}

}


