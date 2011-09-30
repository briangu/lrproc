import java.io.*;

public class test {

	public static void main(String args[])
	{
		CPUEmulator cpu = new CPUEmulator();

		int[] p = new int[16];

		p[0] = 1;
		p[1] = 128;
		p[2] = 3;
		p[3] = 2;

		cpu.loadProgram(p);
		cpu.run(4);

		System.out.println(cpu.getAcc());
	}

}
