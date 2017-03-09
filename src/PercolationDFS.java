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
	public int[][] myGrid;
	private int myOpenSites;

	/**
	 * Initialize a grid so that all cells are blocked.
	 * 
	 * @param n
	 *            is the size of the simulated (square) grid
	 */
	public PercolationDFS(int n) {
		if(n<=0){ //checks if n is in bounds
			throw new IllegalArgumentException("n is less than or equal to 0");
		}

		myOpenSites = 0;
		myGrid = new int[n][n];
		for (int[] row:myGrid){ //sets boxes as blocked
			Arrays.fill(row, BLOCKED);
		}
	}
	//opens respective point on grid
	public void open(int i, int j) {
		if(myGrid[i][j] != BLOCKED){
			return;
		}
		//iterates through the rows and columns and checks if they're full
		//if full, it sets it as open
		//aka flushing
		for(int r=0; r<myGrid.length; r++){
			for (int c=0; c<myGrid.length;c++){
				if(myGrid[r][c] == FULL){
					myGrid[r][c] = OPEN;

				}
			}
		}
		myOpenSites++;
		myGrid[i][j] = OPEN;
		//calls dfs on open sites
		for(int r=0; r<myGrid.length; r++){
			if(myGrid[0][r] ==OPEN){ 
				dfs(0,r);
			}
		}
	}
	//boolean to check if the respective site is open
	public boolean isOpen(int i, int j) {
		if(i<0||i>=myGrid.length||j<0||j>=myGrid[0].length){
			throw new IndexOutOfBoundsException("Index " + i + "," +j+ " is bad!");
		}
		return myGrid[i][j] == OPEN; //returns true or false
	}
	//boolean to check if the respective site is full
	public boolean isFull(int i, int j) {
		if(i<0||i>=myGrid.length||j<0||j>=myGrid[0].length){
			throw new IndexOutOfBoundsException("Index " + i + "," +j+ " is bad!");
		}
		return myGrid[i][j] == FULL;
	}
	//returns the number of calls to open new sites
	public int numberOfOpenSites() {
		return myOpenSites;
	}

	//checks if the grid has percolated by seeing if any 
	//site on bottom row is full
	public boolean percolates() {
		for(int j=0; j<myGrid.length ;j++){
			if(isFull(myGrid.length-1,j)){
				return true;
			}

		}
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
		//checks the necessary conditions
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
