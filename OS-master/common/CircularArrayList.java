package common;

import java.util.ArrayList;
import java.util.Collection;

public class CircularArrayList<E> extends ArrayList<E> {
	private static final long serialVersionUID = 1L;

public CircularArrayList() {
    super();
  }

  public CircularArrayList(int initialCapacity) {
    super(initialCapacity);
  }

  public CircularArrayList(Collection<? extends E> c) {
    super(c);
  }

  @Override
  public E get(int index) {
    if (isEmpty()) {
      throw new IndexOutOfBoundsException("The list is empty");
    }

    while (index < 0) {
      index = size() + index;
    }

    return super.get(index % size());
  }

}