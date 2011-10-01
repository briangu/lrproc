import java.util.Vector;

public class MPU8051 extends CPUEmulator 
{
	public
	MPU8051()
	{
		super();

		addInstruction(new CPUInstruction_halt());
		
		addInstruction(new CPUInstruction_cobj());
		addInstruction(new CPUInstruction_inc_lr());
		addInstruction(new CPUInstruction_dec_lr());
//		addInstruction(new CPUInstruction_mov_lr_r());
//		addInstruction(new CPUInstruction_mov_lr_l());
		addInstruction(new CPUInstruction_inc_rr());
		addInstruction(new CPUInstruction_dec_rr());
//		addInstruction(new CPUInstruction_mov_rr_r());
//		addInstruction(new CPUInstruction_mov_rr_l());

		addInstruction(new CPUInstruction_mov_l_r());
		addInstruction(new CPUInstruction_mov_r_l());

//		addInstruction(new CPUInstruction_add_l_data());
//		addInstruction(new CPUInstruction_sub_l_data());
//		addInstruction(new CPUInstruction_mul_l_data());
//		addInstruction(new CPUInstruction_div_l_data());
		addInstruction(new CPUInstruction_add_l_r());
		addInstruction(new CPUInstruction_sub_l_r());
		addInstruction(new CPUInstruction_mul_l_r());
//		addInstruction(new CPUInstruction_div_l_r());

		addInstruction(new CPUInstruction_sjmp_rel());
		addInstruction(new CPUInstruction_ljmp_addr());

		addInstruction(new CPUInstruction_djnz_l());
		addInstruction(new CPUInstruction_inc());
		addInstruction(new CPUInstruction_dec());
		addInstruction(new CPUInstruction_jz());
		addInstruction(new CPUInstruction_jnz());

		reset();
	}
	
	public
	void
	reset()
	{
		super.reset();
		addObject(0);
		setLeftRef(0);
		setRightRef(0);
	}

	//
	//
	//

	class CPUInstruction_halt implements CPUInstruction
	{
		public void execute()
		{
			halt();
		}
		
		public String toString()
		{
			return(new String("halt"));
		}
	}

	class CPUInstruction_cobj implements CPUInstruction
	{
		public void execute()
		{
			addObject(0);
		}
		
		public String toString()
		{
			return(new String("cobj"));
		}
	}

	class CPUInstruction_inc_lr implements CPUInstruction
	{
		public void execute()
		{
			setLeftRef(getLeftRef()+1);
		}
		
		public String toString()
		{
			return(new String("inc lr"));
		}
	}

	class CPUInstruction_dec_lr implements CPUInstruction
	{
		public void execute()
		{
			setLeftRef(getLeftRef() - 1);
		}
		
		public String toString()
		{
			return(new String("inc lr"));
		}
	}

	class CPUInstruction_mov_lr_r implements CPUInstruction
	{
		public void execute()
		{
			setLeftRef(getRight());
		}
		
		public String toString()
		{
			return(new String("mov lr,r"));
		}
	}

	class CPUInstruction_mov_lr_l implements CPUInstruction
	{
		public void execute()
		{
			setLeftRef(getLeft());
		}
		
		public String toString()
		{
			return(new String("mov lr,l"));
		}
	}

	class CPUInstruction_inc_rr implements CPUInstruction
	{
		public void execute()
		{
			setRightRef(getRightRef() + 1);
		}
		
		public String toString()
		{
			return(new String("inc rr"));
		}
	}

	class CPUInstruction_dec_rr implements CPUInstruction
	{
		public void execute()
		{
			setRightRef(getRightRef() - 1);
		}
		
		public String toString()
		{
			return(new String("dec rr"));
		}
	}

	class CPUInstruction_mov_rr_r implements CPUInstruction
	{
		public void execute()
		{
			setRightRef(getRight());
		}
		
		public String toString()
		{
			return(new String("mov rr,r"));
		}
	}

	class CPUInstruction_mov_rr_l implements CPUInstruction
	{
		public void execute()
		{
			setRightRef(getLeft());
		}
		
		public String toString()
		{
			return(new String("mov rr,l"));
		}
	}

	class CPUInstruction_add_l_data implements CPUInstruction
	{
		int	data;

		public void execute()
		{
			data = getNextDatum();
			setLeft(getLeft() + data);
		}
		
		public String toString()
		{
			return(new String("add l,#"+data));
		}
	}

	class CPUInstruction_sub_l_data implements CPUInstruction
	{
		int	data;

		public void execute()
		{
			data = getNextDatum();
			setLeft(getLeft() - data);
		}
		
		public String toString()
		{
			return(new String("sub l,#"+data));
		}
	}

	class CPUInstruction_mul_l_data implements CPUInstruction
	{
		int	data;

		public void execute()
		{
			data = getNextDatum();
			setLeft(getLeft() * data);
		}
		
		public String toString()
		{
			return(new String("mul l,#"+data));
		}
	}

	class CPUInstruction_div_l_data implements CPUInstruction
	{
		int	data;

		public void execute()
		{
			data = getNextDatum();
			
			if (data == 0)
			{
				setDivideByZeroFlag(true);
			}
			else
			{
				setLeft(getLeft() / data);
			}
		}
		
		public String toString()
		{
			return(new String("div l,#"+data));
		}
	}

	class CPUInstruction_add_l_r implements CPUInstruction
	{
		public void execute()
		{
			setLeft(getLeft() + getRight());
		}
		
		public String toString()
		{
			return(new String("add l,r"));
		}
	}

	class CPUInstruction_sub_l_r implements CPUInstruction
	{
		public void execute()
		{
			setLeft(getLeft() - getRight());
		}
		
		public String toString()
		{
			return(new String("add l,r"));
		}
	}

	class CPUInstruction_mul_l_r implements CPUInstruction
	{
		public void execute()
		{
			setLeft(getLeft() * getRight());
		}
		
		public String toString()
		{
			return(new String("mul l,r"));
		}
	}

	class CPUInstruction_div_l_r implements CPUInstruction
	{
		public void execute()
		{
			int r;	
			r = getRight();
			if (r != 0)
			{
				setLeft(getLeft() / r);
			}
			else
			{
				setDivideByZeroFlag(true);	
			}
		}
		
		public String toString()
		{
			return(new String("div l,r"));
		}
	}

	class CPUInstruction_sjmp_rel implements CPUInstruction
	{
		int	rel;

		public void execute()
		{
			rel = getNextDatum();
			setPC(getPC() + rel);
		}
		
		public String toString()
		{
			return(new String("sjmp "+rel));
		}
	}

	class CPUInstruction_ljmp_addr implements CPUInstruction
	{
		int	addr;

		public void execute()
		{
			addr = getNextDatum();
			setPC(addr);
		}
		
		public String toString()
		{
			return(new String("ljmp "+addr));
		}
	}

	class CPUInstruction_inc implements CPUInstruction
	{
		public void execute()
		{
			setLeft(getLeft() + 1);
		}
		
		public String toString()
		{
			return(new String("inc"));
		}
	}

	class CPUInstruction_dec implements CPUInstruction
	{
		public void execute()
		{
			setLeft(getLeft() - 1);
		}
		
		public String toString()
		{
			return(new String("dec"));
		}
	}

	class CPUInstruction_jz implements CPUInstruction
	{
		boolean	jumped;
		int	rel;

		public void execute()
		{
			jumped = false;
			if (getLeft() == 0)
			{
				jumped = true;
				rel = getNextDatum();
				setPC(getPC() + rel);
			}
		}
		
		public String toString()
		{
			String s;
			if (jumped)
			{
				s = new String("jz "+rel);
			}
			else
			{
				s = new String("jz (!0)");
			}
			return(s);
		}
	}

	class CPUInstruction_jnz implements CPUInstruction
	{
		boolean	jumped;
		int	rel;

		public void execute()
		{
			jumped = false;
			if (getLeft() != 0)
			{
				jumped = true;
				rel = getNextDatum();
				setPC(getPC() + rel);
			}
		}
		
		public String toString()
		{
			String s;
			if (jumped)
			{
				s = new String("jnz "+rel);
			}
			else
			{
				s = new String("jnz (=0)");
			}
			return(s);
		}
	}

	class CPUInstruction_djnz_l implements CPUInstruction
	{
		boolean	jumped;
		int	rel;

		public void execute()
		{
			int	l;
			jumped = false;
			l = getLeft() - 1;
			setLeft(l);
			if (l != 0)
			{
				jumped = true;
				rel = getNextDatum();
				setPC(getPC() + rel);
			}
		}
		
		public String toString()
		{
			String s;
			if (jumped)
			{
				s = new String("djnz l,"+rel);
			}
			else
			{
				s = new String("djnz (left == 0)");
			}
			return(s);
		}
	}

	class CPUInstruction_mov_l_r implements CPUInstruction
	{
		public void execute()
		{
			setLeft(getRight());
		}
		
		public String toString()
		{
			return(new String("mov l,r"));
		}
	}

	class CPUInstruction_mov_r_l implements CPUInstruction
	{
		public void execute()
		{
			setRight(getLeft());
		}
		
		public String toString()
		{
			return(new String("mov r,l"));
		}
	}

}

