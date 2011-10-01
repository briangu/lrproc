package lrproc.cpu;


import java.util.BitSet;


public class Coord
{
  public byte X;
  public byte Y;
  public byte Z;

  public static Coord ZZZ = new Coord((byte)0, (byte)0, (byte)0);

  BitSet _dirBitSet = new BitSet(3);

  public Coord(byte x, byte y, byte z)
  {
    X = x;
    Y = y;
    Z = z;
  }

  public Coord(Coord c)
  {
    X = c.X;
    Y = c.Y;
    Z = c.Z;
  }

  public void setDirX()
  {
    _dirBitSet.set(0, 1);
  }

  public void setDirY()
  {
    _dirBitSet.set(1, 1);
  }

  public void setDirZ()
  {
    _dirBitSet.set(2, 1);
  }

  public void clrDirX()
  {
    _dirBitSet.set(0, 0);
  }

  public void clrDirY()
  {
    _dirBitSet.set(1, 0);
  }

  public void clrDirZ()
  {
    _dirBitSet.set(2, 0);
  }

  public void inc()
  {
    offsetCoord(
        _dirBitSet.get(0) ? 1 : 0,
        _dirBitSet.get(1) ? 1 : 0,
        _dirBitSet.get(2) ? 1 : 0);
  }

  public void inc(Coord coord)
  {
    offsetCoord(coord.X, coord.Y, coord.Z);
  }

  public void dec()
  {
    offsetCoord(
        _dirBitSet.get(0) ? -1 : 0,
        _dirBitSet.get(1) ? -1 : 0,
        _dirBitSet.get(2) ? -1 : 0);
  }

  public void offsetCoord(int x, int y, int z)
  {
    X += x;
    Y += y;
    Z += z;

    while(X < 0) X += Byte.MAX_VALUE;
    while(Y < 0) Y += Byte.MAX_VALUE;
    while(Z < 0) Z += Byte.MAX_VALUE;
  }

  public String toString()
  {
    return String.format("x:%s y:%s z:%s", X, Y, Z);
  }
}
