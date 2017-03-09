import java.util.Arrays;

import com.sun.org.apache.xerces.internal.impl.dv.xs.FullDVFactory;

/**
 * Simulate a system to see its Percolation Threshold, but use a UnionFind
 * implementation to determine whether simulation occurs. The main idea is that
 * initially all cells of a simulated grid are each part of their own set so
 * that there will be n^2 sets in an nxn simulated grid. Finding an open cell
 * will connect the cell being marked to its neighbors --- this means that the
 * set in which the open cell is 'found' will be unioned with the sets of each
 * neighboring cell. The union/find implementation supports the 'find' and
 * 'union' typical of UF algorithms.
 * <P>
 * 
 * @author Owen Astrachan
 * @author Jeff Forbes
 *
 */

public class PercolationUF implements IPercolate {
	private final int OUT_BOUNDS = -1;
	private int myOpenSites;
	public int[][] myGrid;
	public QuickFind finder;
	private final int SinkIndex;
	private final int SourceIndex;

	/**
	 * Constructs a Percolation object for a nxn grid that that creates
	 * a IUnionFind object to determine whether cells are full
	 */

	public PercolationUF(int n) {
		if(n<=0){ //checks if its a valid n
			throw new IllegalArgumentException("out of bounds n");
		}
		myOpenSites = 0;
		myGrid = new int[n][n];
		finder = new QuickFind(n*n +2);
		SinkIndex = n*n +1; //creates virtual sink
		SourceIndex = n*n; //creates virtual source
		for (int[] row:myGrid){  //sets all as blocked
			Arrays.fill(row, BLOCKED);
		}
	}

	/**
	 * Return an index that uniquely identifies (row,col), typically an index
	 * based on row-major ordering of cells in a two-dimensional grid. However,
	 * if (row,col) is out-of-bounds, return OUT_BOUNDS.
	 */
	//generates an index for row,col
	public int getIndex(int row, int col) {
		if (row<0||row>=myGrid.length||col<0||col>=myGrid[0].length){ //checking if in bounds
			throw new IndexOutOfBoundsException("Index " + row + "," +col+ " is bad!");
		}
		else{
			return row*myGrid.length + col;}
	}

	//opens the designated site
	public void open(int i, int j) {
		if (i<0||i>=myGrid.length||j<0||j>=myGrid[0].length){ //checking if in bounds
			throw new IndexOutOfBoundsException("Index " + i + "," +j+ " is bad!");
		}
		if (myGrid[i][j] != BLOCKED){
			return;
		}
		myOpenSites++;
		myGrid[i][j] = OPEN;
		connect(i,j);
	}
	//checks if (i,j) is open in the grid
	public boolean isOpen(int i, int j) {
		if(i<0||i>=myGrid.length||j<0||j>=myGrid[0].length){ //checking if in bounds
			throw new IndexOutOfBoundsException("Index " + i + "," +j+ " is bad!");
		}
		return myGrid[i][j]==OPEN||myGrid[i][j] == FULL;
	}

	//checks if (i,j) is full in the grid
	public boolean isFull(int i, int j) {
		if(i<0 || i>=myGrid.length ||j<0 || j>=myGrid[0].length){ //checking if in bounds
			throw new IndexOutOfBoundsException("Index " + i + "," +j+ " is bad!");
		}
		if(finder.connected(SourceIndex, getIndex(i,j))){ //checks if top and box are in same set
			return true;
		}
		return false;
	}
	//return the number of calls to open new sites
	public int numberOfOpenSites() {
		return myOpenSites;
	}
	//checks if system percolates by seeing if sink and source are in same set
	public boolean percolates() {
		return finder.connected(SinkIndex, SourceIndex);			
	}

	/**
	 * Connect new site (row, col) to all adjacent open sites
	 */
	private void connect(int row, int col) {
		if(row<0||row>=myGrid.length||col<0||col>=myGrid[0].length){
			return;
		}
		if(row==0){
			finder.union(SourceIndex, getIndex(row,col));
		}
		if(row==myGrid.length-1){
			finder.union(SinkIndex, getIndex(row,col));
		}
		int perfIndex = getIndex(row,col); //gets current box's index
		if(row-1>=0){ //checks up 
			if(isOpen(row-1,col)){
				finder.union(getIndex(row-1,col),perfIndex);
			}
		}
		if(row+1<myGrid.length){ //checks down 
			if(isOpen(row+1,col)){
				finder.union(getIndex(row+1,col),perfIndex);
			}
		}
		if(col-1>=0){ //checks left 
			if(isOpen(row,col-1)){
				finder.union(getIndex(row,col-1),perfIndex);
			}
		}
		if(col+1<myGrid.length){ //checks right
			if(isOpen(row,col+1)){
				finder.union(getIndex(row,col+1),perfIndex);
			}
		}
	}

}
