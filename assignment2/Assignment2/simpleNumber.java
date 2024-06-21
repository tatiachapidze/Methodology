import acm.program.ConsoleProgram;

public class simpleNumber extends ConsoleProgram{
	public void run() {
		int n= readInt("Enter number: ");
		for(int i=2; i<=n; i++) {
			if(checkNumber(i)) {
				println(i);
			}
		}
		
		
	}

	private boolean checkNumber(int n) {
		for (int i=2; i<= Math.sqrt(n); i++) {
			if(n%i==0) {
				return false;
			}
		}
		
		return true;
	}


}
