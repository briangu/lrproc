public class MPU8051 extends CPUEmulator 
{

	boolean			myDivideByZeroFlag;
	byte			myAcc;	
	byte[]			myRegisters;
	int			myPokeCnt;

	public
	MPU8051()
	{
		this((byte)16);
	}

	public
	MPU8051(
			int	memorySize)
	{
		super(memorySize);
		myRegisters = new byte[1];
		reset();

		createInstructionSet(21);

		byte	op = 0;

		setInstruction(op++, new CPUInstruction_halt());
		setInstruction(op++, new CPUInstruction_poke());
		setInstruction(op++, new CPUInstruction_add_acc_data());
		setInstruction(op++, new CPUInstruction_add_acc_data());
		setInstruction(op++, new CPUInstruction_sub_acc_data());
		setInstruction(op++, new CPUInstruction_mult_acc_data());
		setInstruction(op++, new CPUInstruction_div_acc_data());
		setInstruction(op++, new CPUInstruction_sjmp_rel());
		setInstruction(op++, new CPUInstruction_ljmp_addr());
//		setInstruction(op++, new CPUInstruction_jmp_a_dptr());
		setInstruction(op++, new CPUInstruction_djnz_r0());
		setInstruction(op++, new CPUInstruction_inc());
		setInstruction(op++, new CPUInstruction_dec());
		setInstruction(op++, new CPUInstruction_jz());
		setInstruction(op++, new CPUInstruction_jnz());
		setInstruction(op++, new CPUInstruction_mov_r0_data());
		setInstruction(op++, new CPUInstruction_mov_acc_r0());
		setInstruction(op++, new CPUInstruction_mov_r0_acc());
		setInstruction(op++, new CPUInstruction_add_acc_r0());
		setInstruction(op++, new CPUInstruction_sub_acc_r0 ());
		setInstruction(op++, new CPUInstruction_mult_acc_r0());
		setInstruction(op++, new CPUInstruction_div_acc_r0());

	}

        public
        void
        run(
                int     ic)
        {
                CPUInstruction  ins = null;
                int             op;

                setPC(0);

                for(int i = 0; !isHalted() && i < ic; i++)
                {
                        op = getCurrentByte();
                        ins = getInstruction(op);
                        ins.execute();
                        if (tracing())
                        {
                                System.out.println(ins.toString() + "\t\t\t" + " acc = " + getAcc() + " r0 = " + getRegister(0));
                        }
                        incPC();
                }
        }


	public
	void
	reset()
	{
		super.reset();
		setDivideByZeroFlag(false);
		setAcc((byte)0);
		setRegister(0, (byte)0);
		setPokeCnt((byte)0);
	}
	
	protected
	void
	setAcc(	
		byte	value)
	{
		myAcc = value;
	}

	public
	byte
	getAcc()
	{
		return myAcc;
	}

	protected
	void
	poke(
		byte	x)
	{
		setPokeCnt((int)(getPokeCnt() + x));
	}

	protected
	void
	setPokeCnt(	
		int	value)
	{
		myPokeCnt = value;
	}

	public
	int
	getPokeCnt()
	{
		return myPokeCnt;
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

	protected
	void
	setRegister(
			int	i,
			byte	value)
	{
		myRegisters[i] = value;
	}

	public
	byte
	getRegister(
			int	i)
	{
		return(myRegisters[i]);
	}

	public
	String
	toString()
	{
		return(new String(""));		
	}

	//
	//
	//

	class CPUInstruction_add_acc_data implements CPUInstruction
	{
		byte	data;

		public
		void
		execute()
		{
			data = getNextByte();
			setAcc((byte)(getAcc() + data));
		}
		
		public
		String
		toString()
		{
			return(new String("add acc,#"+data));
		}
	}

	class CPUInstruction_sub_acc_data implements CPUInstruction
	{
		byte	data;

		public
		void
		execute()
		{
			data = getNextByte();
			setAcc((byte)(getAcc() - data));
		}
		
		public
		String
		toString()
		{
			return(new String("sub acc,#"+data));
		}
	}

	class CPUInstruction_mult_acc_data implements CPUInstruction
	{
		byte	data;

		public
		void
		execute()
		{
			data = getNextByte();
			setAcc((byte)(getAcc() * data));
		}
		
		public
		String
		toString()
		{
			return(new String("mult acc,#"+data));
		}
	}

	class CPUInstruction_div_acc_data implements CPUInstruction
	{
		byte	data;

		public
		void
		execute()
		{
			data = getNextByte();
			
			if (data == 0)
			{
				setDivideByZeroFlag(true);
			}
			else
			{
				setAcc((byte)(getAcc() / data));
			}
		}
		
		public
		String
		toString()
		{
			return(new String("div acc,#"+data));
		}
	}

	class CPUInstruction_sjmp_rel implements CPUInstruction
	{
		byte	rel;

		public
		void
		execute()
		{
			rel = getNextByte();
			setPC(getPC() + rel);
		}
		
		public
		String
		toString()
		{
			return(new String("sjmp "+rel));
		}
	}

	class CPUInstruction_ljmp_addr implements CPUInstruction
	{
		byte	addr;

		public
		void
		execute()
		{
			addr = getNextByte();
			setPC(addr);
		}
		
		public
		String
		toString()
		{
			return(new String("ljmp "+addr));
		}
	}

	class CPUInstruction_jmp_a_dptr implements CPUInstruction
	{
		byte	addr;

		public
		void
		execute()
		{
			addr = getNextByte();
			setPC(getAcc() + addr);
		}
		
		public
		String
		toString()
		{
			return(new String("jmp @a+"+addr));
		}
	}

	class CPUInstruction_inc implements CPUInstruction
	{
		public
		void
		execute()
		{
			setAcc((byte)(getAcc() + 1));
		}
		
		public
		String
		toString()
		{
			return(new String("inc"));
		}
	}

	class CPUInstruction_dec implements CPUInstruction
	{
		public
		void
		execute()
		{
			setAcc((byte)(getAcc() - 1));
		}
		
		public
		String
		toString()
		{
			return(new String("bytes_dec"));
		}
	}

	class CPUInstruction_jz implements CPUInstruction
	{
		boolean	jumped;
		byte	rel;

		public
		void
		execute()
		{
			jumped = false;
			if (getAcc() == 0)
			{
				jumped = true;
				rel = getNextByte();
				setPC(getPC() + rel);
			}
		}
		
		public
		String
		toString()
		{
			String s;
			if (jumped)
			{
				s = new String("jz "+rel);
			}
			else
			{
				s = new String("jz (acc != 0)");
			}
			return(s);
		}
	}

	class CPUInstruction_jnz implements CPUInstruction
	{
		boolean	jumped;
		byte	rel;

		public
		void
		execute()
		{
			jumped = false;
			if (getAcc() != 0)
			{
				jumped = true;
				rel = getNextByte();
				setPC(getPC() + rel);
			}
		}
		
		public
		String
		toString()
		{
			String s;
			if (jumped)
			{
				s = new String("jnz "+rel);
			}
			else
			{
				s = new String("jnz (acc == 0)");
			}
			return(s);
		}
	}

	class CPUInstruction_djnz_r0 implements CPUInstruction
	{
		boolean	jumped;
		byte	rel;

		public
		void
		execute()
		{
			jumped = false;
			setRegister(0, (byte)(getRegister(0) - 1));
			if (getRegister(0) != 0)
			{
				jumped = true;
				rel = getNextByte();
				setPC(getPC() + rel);
			}
		}
		
		public
		String
		toString()
		{
			String s;
			if (jumped)
			{
				s = new String("djnz r0,"+rel);
			}
			else
			{
				s = new String("jnz (acc == 0)");
			}
			return(s);
		}
	}

	class CPUInstruction_mov_r0_data implements CPUInstruction
	{
		byte	data;

		public
		void
		execute()
		{
			data = getNextByte();
			setRegister(0, data);
		}
		
		public
		String
		toString()
		{
			return(new String("mov r0,#"+data));
		}
	}

	class CPUInstruction_mov_acc_r0 implements CPUInstruction
	{
		public
		void
		execute()
		{
			setAcc(getRegister(0));
		}
		
		public
		String
		toString()
		{
			return(new String("mov acc,r0"));
		}
	}

	class CPUInstruction_mov_r0_acc implements CPUInstruction
	{
		public
		void
		execute()
		{
			setRegister(0, getAcc());
		}
		
		public
		String
		toString()
		{
			return(new String("mov r0, acc"));
		}
	}

	class CPUInstruction_add_acc_r0 implements CPUInstruction
	{
		public
		void
		execute()
		{
			setAcc((byte)(getAcc() + getRegister(0)));
		}
		
		public
		String
		toString()
		{
			return(new String("add acc,r0"));
		}
	}

	class CPUInstruction_sub_acc_r0 implements CPUInstruction
	{
		public
		void
		execute()
		{
			setAcc((byte)(getAcc() - getRegister(0)));
		}
		
		public
		String
		toString()
		{
			return(new String("sub acc,r0"));
		}
	}

	class CPUInstruction_mult_acc_r0 implements CPUInstruction
	{
		public
		void
		execute()
		{
			setAcc((byte)(getAcc() * getRegister(0)));
		}
		
		public
		String
		toString()
		{
			return(new String("mult acc,r0"));
		}
	}

	class CPUInstruction_div_acc_r0 implements CPUInstruction
	{
		public
		void
		execute()
		{
			if (getRegister(0) == 0)
			{
				setDivideByZeroFlag(true);
			}
			else
			{
				setAcc((byte)(getAcc() / getRegister(0)));
			}
		}
		
		public
		String
		toString()
		{
			return(new String("div acc,r0"));
		}
	}

	class CPUInstruction_poke implements CPUInstruction
	{
		public
		void
		execute()
		{
			poke(getRegister(0));
		}
		
		public
		String
		toString()
		{
			return(new String("poke"));
		}
	}

	class CPUInstruction_halt implements CPUInstruction
	{
		public
		void
		execute()
		{
			halt();
		}
		
		public
		String
		toString()
		{
			return(new String("halt"));
		}
	}

}

