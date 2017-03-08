import java.util.Arrays;

/**
 * Simulate percolation thresholds for a grid-base system using depth-first-search,
 * aka 'flood-fill' techniques for determining if the top of a grid is connected
 * to the bottom of a grid.
 * <P>
 * Modified from the COS 226 Princeton code for use at Duke. The modifications
 * consist of supporting the <code>IPercolate</code> interface, renaming methods
 * and fields to be more consistent with Java/Duke standards and rewriting code
 * to reflect the DFS/flood-fill techniques used in discussion at Duke.
 * <P>
 * @author Kevin Wayne, wayne@cs.princeton.edu
 * @author Owen Astrachan, ola@cs.duke.edu
 * @author Jeff Forbes, forbes@cs.duke.edu
 */


public class PercolationDFS implements IPercolate {
	// possible instance variable for storing grid state
	public int[][] myGrid;
	private int myOpenSites;
	
	

	/**
	 * Initialize a grid so that all cells are blocked.
	 * 
	 * @param n
	 *            is the size of the simulated (square) grid
	 */
public int N;
	public PercolationDFS(int n) {
		if(n<=0){
			throw new IllegalArgumentException("n is less than or equal to 0");
		}
		N = n;
		myOpenSites = 0;
		myGrid = new int[N][N];
		for (int[] row:myGrid){
			Arrays.fill(row, BLOCKED);
		}
		// TODO complete constructor and add necessary instance variables

	}

	public void open(int i, int j) {
		if(i<0||i>=myGrid.length||j<0||j>=myGrid[0].length){
			throw new IndexOutOfBoundsException("Index " + i + "," +j+ " is bad!");
		}
		if(myGrid[i][j] != BLOCKED){
			return;
		}
		
		// TODO complete open
		
		for(int r=0; r<myGrid.length; r++){
			for (int c=0; c<myGrid.length;c++){
				if(myGrid[r][c] == FULL){
					myGrid[r][c] = OPEN;
			
				}
			}
		}
		myOpenSites++;
		myGrid[i][j] = OPEN;
		for(int r=0; r<myGrid.length; r++){
				if(myGrid[0][r] ==OPEN){ //!BLOCKED
					dfs(0,r);
				}
			}
		
		
	}
	public boolean isOpen(int i, int j) {
		if(i<0||i>=myGrid.length||j<0||j>=myGrid[0].length){
			throw new IndexOutOfBoundsException("Index " + i + "," +j+ " is bad!");
		}
		return myGrid[i][j] == OPEN; //returns true or false
	}

	public boolean isFull(int i, int j) {
		if(i<0||i>=myGrid.length||j<0||j>=myGrid[0].length){
			throw new IndexOutOfBoundsException("Index " + i + "," +j+ " is bad!");
		}
		if(isOpen(i,j)==false){
		return myGrid[i][j] == FULL;
		}
		return myGrid[i][j] == FULL;
	}

	public int numberOfOpenSites() {
		// TODO return the number of calls to open new sites
		return N;
	}

	public boolean percolates() {
		for(int j=0; j<myGrid.length ;j++){
			if(isFull(myGrid.length-1,j)){
				return true;
			}
			
		}
		// TODO: run DFS to find all full sites
		return false;
	}

	/**
	 * Private helper method to mark all cells that are open and reachable from
	 * (row,col).
	 * 
	 * @param row
	 *            is the row coordinate of the cell being checked/marked
	 * @param col
	 *            is the col coordinate of the cell being checked/marked
	 */
	private void dfs(int row, int col) {
		if (row<0||row>=myGrid.length||col<0||col>=myGrid[0].length){ //checking if in bounds
			return;}
		if ((isFull(row,col))|| (!(isOpen(row,col)))){
			return;
		}
		
		
		myGrid[row][col] = FULL;
		dfs(row-1,col); //check up
		dfs(row, col-1); //check right
		dfs(row, col+1); //check left
		dfs(row +1, col); //check down
	
	}
}
//if blocked -- done
//if out of bounds -- done
//if already full --done 
//need to flush it every single time -- before all dfs on top == means everythign full and make open again 
