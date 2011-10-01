package lrproc.cpu;


import java.util.List;
import java.util.Stack;


public class Ring<T>
{
  Stack<T> _stack;
  List<T> _ring;
  int _ptr;
  T _default;

  public Ring(T def)
  {
    _default = def;
  }

  public void insert(T item)
  {
    _ring.add(_ptr, item);
  }

  public T remove()
  {
    if (_ring.size() == 0) return _default;
    T item = _ring.remove(_ptr);
    return item;
  }

  public void push()
  {
    _stack.push(get());
  }

  public T pop()
  {
    return _stack.empty() ? _default : _stack.pop();
  }

  public T get()
  {
    return _ring.get(_ptr);
  }

  public void set(T item)
  {
    _ring.set(_ptr, item);
  }

  public int getPtr()
  {
    return _ptr;
  }

  public int rot(int b)
  {
    _ptr = (_ptr + b ) % _ring.size();
    return _ptr;
  }

  public int shiftLeft()
  {
    if (_ptr == 0)
    {
      _ptr = _ring.size() - 1;
    }
    else
    {
      _ptr--;
    }
    return _ptr;
  }

  public int shiftRight()
  {
    _ptr = (_ptr + 1) % _ring.size();
    return _ptr;
  }
}
