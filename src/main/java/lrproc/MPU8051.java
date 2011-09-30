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
		addInstruction(new CPUInstruction_mov_lr_r());
		addInstruction(new CPUInstruction_mov_lr_l());
		addInstruction(new CPUInstruction_inc_rr());
		addInstruction(new CPUInstruction_dec_rr());
		addInstruction(new CPUInstruction_mov_rr_r());
		addInstruction(new CPUInstruction_mov_rr_l());

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
		addObject(new Integer(0));
		setLeftRef(0);
		setRightRef(0);
	}

	//
	//
	//

	class CPUInstruction_halt extends CPUInstruction
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

	class CPUInstruction_cobj extends CPUInstruction
	{
		public void execute()
		{
			addObject(new Integer(0));
		}
		
		public String toString()
		{
			return(new String("cobj"));
		}
	}

	class CPUInstruction_inc_lr extends CPUInstruction
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

	class CPUInstruction_dec_lr extends CPUInstruction
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

	class CPUInstruction_mov_lr_r extends CPUInstruction
	{
		public void execute()
		{
			setLeftRef(((Integer)getRight()).intValue());
		}
		
		public String toString()
		{
			return(new String("mov lr,r"));
		}
	}

	class CPUInstruction_mov_lr_l extends CPUInstruction
	{
		public void execute()
		{
			setLeftRef(((Integer)getLeft()).intValue());
		}
		
		public String toString()
		{
			return(new String("mov lr,l"));
		}
	}

	class CPUInstruction_inc_rr extends CPUInstruction
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

	class CPUInstruction_dec_rr extends CPUInstruction
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

	class CPUInstruction_mov_rr_r extends CPUInstruction
	{
		public void execute()
		{
			setRightRef(((Integer)getRight()).intValue());
		}
		
		public String toString()
		{
			return(new String("mov rr,r"));
		}
	}

	class CPUInstruction_mov_rr_l extends CPUInstruction
	{
		public void execute()
		{
			setRightRef(((Integer)getLeft()).intValue());
		}
		
		public String toString()
		{
			return(new String("mov rr,l"));
		}
	}

	class CPUInstruction_add_l_data extends CPUInstruction
	{
		int	data;

		public void execute()
		{
			int	l;
			data = ((Integer)getNextDatum()).intValue();
			l = ((Integer)getLeft()).intValue();
			setLeft(new Integer(l + data));
		}
		
		public String toString()
		{
			return(new String("add l,#"+data));
		}
	}

	class CPUInstruction_sub_l_data extends CPUInstruction
	{
		int	data;

		public void execute()
		{
			int	l;
			data = ((Integer)getNextDatum()).intValue();
			l = ((Integer)getLeft()).intValue();
			setLeft(new Integer(l - data));
		}
		
		public String toString()
		{
			return(new String("sub l,#"+data));
		}
	}

	class CPUInstruction_mul_l_data extends CPUInstruction
	{
		int	data;

		public void execute()
		{
			int	l;
			data = ((Integer)getNextDatum()).intValue();
			l = ((Integer)getLeft()).intValue();
			setLeft(new Integer(l * data));
		}
		
		public String toString()
		{
			return(new String("mul l,#"+data));
		}
	}

	class CPUInstruction_div_l_data extends CPUInstruction
	{
		int	data;

		public void execute()
		{
			data = ((Integer)getNextDatum()).intValue();
			
			if (data == 0)
			{
				setDivideByZeroFlag(true);
			}
			else
			{
				int	l;
				l = ((Integer)getLeft()).intValue();
				setLeft(new Integer(l / data));
			}
		}
		
		public String toString()
		{
			return(new String("div l,#"+data));
		}
	}

	class CPUInstruction_add_l_r extends CPUInstruction
	{
		public void execute()
		{
			int l;
			int r;
			l = ((Integer)getLeft()).intValue();
			r = ((Integer)getRight()).intValue();
			setLeft(new Integer(l + r));
		}
		
		public String toString()
		{
			return(new String("add l,r"));
		}
	}

	class CPUInstruction_sub_l_r extends CPUInstruction
	{
		public void execute()
		{
			int l;
			int r;
			l = ((Integer)getLeft()).intValue();
			r = ((Integer)getRight()).intValue();
			setLeft(new Integer(l - r));
		}
		
		public String toString()
		{
			return(new String("add l,r"));
		}
	}

	class CPUInstruction_mul_l_r extends CPUInstruction
	{
		public void execute()
		{
			int l;
			int r;
			l = ((Integer)getLeft()).intValue();
			r = ((Integer)getRight()).intValue();
			setLeft(new Integer(l * r));
		}
		
		public String toString()
		{
			return(new String("mul l,r"));
		}
	}

	class CPUInstruction_div_l_r extends CPUInstruction
	{
		public void execute()
		{
			int l;
			int r;	
			l = ((Integer)getLeft()).intValue();
			r = ((Integer)getRight()).intValue();
			if (r != 0)
			{
				setLeft(new Integer(l / r));
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

	class CPUInstruction_sjmp_rel extends CPUInstruction
	{
		int	rel;

		public void execute()
		{
			rel = ((Integer)getNextDatum()).intValue();
			setPC(getPC() + rel);
		}
		
		public String toString()
		{
			return(new String("sjmp "+rel));
		}
	}

	class CPUInstruction_ljmp_addr extends CPUInstruction
	{
		int	addr;

		public void execute()
		{
			addr = ((Integer)getNextDatum()).intValue();
			setPC(addr);
		}
		
		public String toString()
		{
			return(new String("ljmp "+addr));
		}
	}

	class CPUInstruction_inc extends CPUInstruction
	{
		public void execute()
		{
			int	l;
			l = ((Integer)getLeft()).intValue();
			setLeft(new Integer(l + 1));
		}
		
		public String toString()
		{
			return(new String("inc"));
		}
	}

	class CPUInstruction_dec extends CPUInstruction
	{
		public void execute()
		{
			int	l;
			l = ((Integer)getLeft()).intValue();
			setLeft(new Integer(l - 1));
		}
		
		public String toString()
		{
			return(new String("dec"));
		}
	}

	class CPUInstruction_jz extends CPUInstruction
	{
		boolean	jumped;
		int	rel;

		public void execute()
		{
			int 	l;
			jumped = false;
			l = ((Integer)getLeft()).intValue();
			if (l == 0)
			{
				jumped = true;
				rel = ((Integer)getNextDatum()).intValue();
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

	class CPUInstruction_jnz extends CPUInstruction
	{
		boolean	jumped;
		int	rel;

		public void execute()
		{
			int	l;
			jumped = false;
			l = ((Integer)getLeft()).intValue();
			if (l != 0)
			{
				jumped = true;
				rel = ((Integer)getNextDatum()).intValue();
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

	class CPUInstruction_djnz_l extends CPUInstruction
	{
		boolean	jumped;
		int	rel;

		public void execute()
		{
			int	l;
			jumped = false;
			l = ((Integer)getLeft()).intValue() - 1;
			setLeft(new Integer(l));
			if (l != 0)
			{
				jumped = true;
				rel = ((Integer)getNextDatum()).intValue();
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

	class CPUInstruction_mov_l_r extends CPUInstruction
	{
		public void execute()
		{
			int	r;
			r = ((Integer)getRight()).intValue();
			setLeft(new Integer(r));
		}
		
		public String toString()
		{
			return(new String("mov l,r"));
		}
	}

	class CPUInstruction_mov_r_l extends CPUInstruction
	{
		public void execute()
		{
			int	l;
			l = ((Integer)getLeft()).intValue();
			setRight(new Integer(l));
		}
		
		public String toString()
		{
			return(new String("mov r,l"));
		}
	}

}

