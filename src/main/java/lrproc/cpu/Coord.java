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

  public void advance()
  {

  }
}
