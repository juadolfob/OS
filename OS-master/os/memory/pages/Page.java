package os.memory.pages;

public class Page {
	public int id;
	public int physical;
	public byte V;
	public byte R;
	public byte M;
	public int inMemTime;
	public int lastTouchTime;
	public long high;
	public long low;
	
	public Page(int id, int physical,byte V, byte R, byte M, int inMemTime, int lastTouchTime, long high, long low) {
		this.id = id;
		this.physical = physical;
		this.V = V;
		this.R = R;
		this.M = M;
		this.inMemTime = inMemTime;
		this.lastTouchTime = lastTouchTime;
		this.high = high;
		this.low = low;
	}
}
