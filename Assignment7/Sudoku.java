import acm.program.ConsoleProgram;

public class Sudoku extends ConsoleProgram {
	private static final int SENTINEL=45;
	public void run() {
		int[][] sudoku = { {7,9,4,5,8,3,2,1,6},
				           {8,3,6,7,2,1,4,9,5},
				           {1,5,2,4,9,6,7,8,3},
				           {3,7,1,2,6,4,9,5,8},
				           {5,2,9,3,7,8,1,6,4},
				           {6,4,8,9,1,5,3,2,7},
				           {9,6,7,8,3,2,5,4,1},
				           {4,8,3,1,5,9,6,7,2},
				           {2,1,5,6,4,7,8,3,9},};
		if(isSudokuRight(sudoku)) {
			println("sudoku is right");
		}else {
			println("sudoku isn't right");
		}
		
		
	}
	private static boolean isSudokuRight(int [][]sudoku) {
		int [] arr= new int[3];
		arr[0]=checkHorizontals(sudoku);
		arr[1]=checkVerticales(sudoku);
		arr[2]=checkCubes(sudoku);
		for (int i=0; i<3; i++) {
			if(arr[i]!=0) {
				return false;
			}
		}
		return true;
	}
	private static int checkCubes(int[][] sudoku) {
		int sum=0;
		for( int i=0; i<9; i+=3) {
			for(int j=0; j<3;j++) {
				sum=sum+sudoku[i][j]+sudoku[i+1][j]+sudoku[i+2][j];
			}
			if(sum!=SENTINEL) {
				return 1;
			}
			sum=0;
			for(int j=3; j<6; j++) {
			 sum=sum+sudoku[i][j]+sudoku[i+1][j]+sudoku[i+2][j];	
			}
			if(sum!=SENTINEL) {
				return 1;
			}
			sum=0;
			for(int j=6; j<9; j++) {
				sum = sum+ sudoku[i][j]+sudoku[i+1][j]+sudoku[i+2][j];
			}
			if(sum!=SENTINEL) {
				return 1;
			}
			sum=0;
		}
		return 0;
	}
	private static int checkVerticales(int[][] sudoku) {
		int sum =0; 
		for(int i=0; i<9; i++) {
			for( int j=0; j<9; j++) {
				sum+=sudoku[j][i];
			}
			if(sum!=SENTINEL) {
				return 1;
			}
			sum=0;
		}
		return 0;
	}
	private static int checkHorizontals(int[][] sudoku) {
		int sum=0;
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				sum+= sudoku[i][j];
			}
			if(sum!=SENTINEL) {
				return 1;
			}
			sum=0;
		}
		return 0;
		
	}

}
