import java.io.PrintWriter;
import java.util.*;

public class Maze{
	private DisjSets ds;
	private int rows;
	private int columns;
	private boolean[][] verticalLines;
	private boolean[][] horizontalLines;
	
	public Maze(int rows, int columns){
		ds = new DisjSets(rows*columns);
		this.rows = rows;
		this.columns = columns;
		verticalLines = new boolean[rows][columns+1];
		horizontalLines = new boolean[rows+1][columns];
		horizontalLines[0][0] = true;
		verticalLines[0][0] = true;
		horizontalLines[rows][columns-1] = true;
		verticalLines[rows-1][columns] = true;
	}
	
	/*direction 0-left,1-right,2-up,3-bottom*/
	private int[] random(int element){
		ArrayList<Integer> list = new ArrayList<>();
		if(element % columns != 0 && ds.find(element) != ds.find(element-1)){
			list.add(0);
		}
		if(element % columns != columns-1 && ds.find(element) != ds.find(element+1)){
			list.add(1);
		}
		if(element >= columns && ds.find(element) != ds.find(element - columns)){
			list.add(2);
		}
		if(element < (rows-1)*columns && ds.find(element) != ds.find(element + columns)){
			list.add(3);
		}
		
		if(list.size() == 0){
			return new int[]{};
		}
		
		int direction = list.get(new Random().nextInt(list.size()));
		int root = ds.find(element);
		int root1;
		
		if(direction == 0){
			root1 = ds.find(element - 1);
			verticalLines[element/columns][element%columns] = true;
		} else if(direction == 1){
			root1 = ds.find(element + 1);
			verticalLines[element/columns][element%columns+1] = true;
		} else if(direction == 2){
			root1 = ds.find(element - columns);
			horizontalLines[element/columns][element%columns] = true;
		} else {
			root1 = ds.find(element + columns);
			horizontalLines[element/columns+1][element%columns] = true;
		}
		
		return new int[]{root,root1};
	}
	
	private void createMaze() {
		Random r = new Random();
		int count=rows*columns-1;
		while(count>0){
			int element = r.nextInt(rows*columns);
			int[] array = random(element);
			
			if(array.length != 0){
				int root1 = array[0];
				int root2 = array[1];
				
				ds.union(root1,root2);
				count--;
			}
		}
	}
	
	public void displayMaze(){
		PrintWriter out = new PrintWriter(System.out);
		
		for(int j=0;j<columns;j++){
			out.print(" ");
			if(horizontalLines[0][j]){
				out.print(" ");
			}else{
				out.print("_");
			}
		}
		out.println("");
		
		for(int i=0;i<rows;i++){
			for(int j=0;j<columns;j++){
				if(verticalLines[i][j]){
					out.print(" ");
				}else{
					out.print("|");
				}
				
				if(horizontalLines[i+1][j]){
					out.print(" ");
				}else{
					out.print("_");
				}
			}
			if(verticalLines[i][columns]){
				out.print(" ");
			}else{
				out.print("|");
			}
			out.println("");
		}
		
		out.flush();
	}

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		int rows, columns;
		while(true){
			System.out.println("Enter the number of rows and columns : ");
			String row = scanner.next();
			String column = scanner.next();
			
			try{
				rows = Integer.parseInt(row);
				columns = Integer.parseInt(column);
			}catch(Exception e){
				System.out.println("Rows and Columns must be Integers. Please enter valid details");
				continue;
			}
			
			if(rows<=0 || columns<=0){
				System.out.println("Rows and Columns should be greater than 0. Please enter valid details");
			}else{
				break;
			}
		}
		
		
		Maze maze = new Maze(rows, columns);
		maze.createMaze();
		maze.displayMaze();
	}
}
