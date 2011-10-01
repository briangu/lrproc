package lrproc.cpu;


public class Memory
{
  private byte[][][] _memory;

  public Memory(byte x, byte y, byte z)
  {
    _memory = new byte[x][y][z];
  }

  public Memory(byte[][][] memory)
  {
    _memory = memory;
  }

  public byte get(Coord coord)
  {
    return _memory[coord.X][coord.Y][coord.Z];
  }

  public void set(Coord coord, byte b)
  {
    _memory[coord.X][coord.Y][coord.Z] = b;
  }

  public int getRelativeAddr(Coord start, Coord offset)
  {

    int x = getPC() + addr;
    while (x < 0)
    {
      x = getMemorySize() + x;
    }
    return x;
  }

  public void bind(Coord c1, Coord c2)
  {

  }
}
