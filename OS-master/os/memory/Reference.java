package os.memory;

public class Reference {
	static String WRITE="WRITE";
	static String READ="READ";
	static String CLEAN="CLEAN";
	public String inst;
	public long addr;

	  public Reference( String inst, long addr ) 
	  {
	    this.inst = inst;
	    this.addr = addr;
	  } 	
}
