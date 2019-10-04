package os.memory;

public class Memory {
	int addresSpace = 0;
	public int bytesize = 0;
	int bytes[];

	public Memory(int addresSpace) {
		this.addresSpace = addresSpace;
		bytesize = (int) (Math.pow(2, addresSpace) / 8);
		System.out.println(bytesize);
		bytes = new int[bytesize];
	}

	public void print() {
		for(int b=0;b<bytesize;b++) {
			System.out.print(bytes[b]);
			if(b%64==63)
				System.out.println();
		}
	}
}