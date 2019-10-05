package os.process;

import java.util.ArrayList;
import java.util.List;


public class ProcessList {
	List<Process> Processes = new ArrayList<Process>();
	
	
	public ProcessList(){
		
	}
	public void add(Process process) {
		Processes.add(process);
	}
	public void print() {
		for(int nprocess=0;nprocess<Processes.size();nprocess++) {
			System.out.print(Processes.get(nprocess).name+ " ");

			System.out.print(Processes.get(nprocess).size_in_bytes+ " ");

			System.out.print(Processes.get(nprocess).time_in_seconds+ "\n");
		}
	}
}