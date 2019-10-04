package os.memory.pages;

public class PageTable {
	
	PageEntry[] pagetable;
	
	public PageTable(int pages) {
		pagetable=new PageEntry[pages];
	}
}
