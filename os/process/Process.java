package os.process;

import java.util.Random;

public class Process extends Thread {


	static int process_number=0;
	static Random r=new Random();
	public int name;
	public int size_in_bytes;
	public int time_in_seconds;
	
	
	public Process(int name,int time,int size) {
		 
		this.name=name;
		this.size_in_bytes= time;
		this.time_in_seconds=size;
		
	}

	public void randomize(int max_time,int max_size) {
		 
		this.name=process_number++;;
		this.size_in_bytes= (int) Math.round(r.nextInt(max_size)+1);
		this.time_in_seconds=(int) Math.round(r.nextInt(max_time)+1);
		
	}

	@Override
	public void run() 
	{ 
		System.out.println(Thread.currentThread().getName() 
						 + ", executing run() method!"); 
	} 
}
