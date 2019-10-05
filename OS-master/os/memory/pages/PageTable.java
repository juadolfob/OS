package os.memory.pages;

import java.util.Collection;

import common.CircularArrayList;

public class PageTable extends CircularArrayList<Page> {
	private static final long serialVersionUID = 227401837590336860L;

	public PageTable() {
		super();
	}

	public PageTable(int initialCapacity) {
		super(initialCapacity);
	}

	public PageTable(Collection<Page> c) {
		super(c);
	}

	public Page get(int index) {
		return super.get(index);

	}
}
