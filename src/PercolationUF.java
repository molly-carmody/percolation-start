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
// n^2 sets in an nxn
//set where open cell is found is to be unioned with teh sets of each neighboring cell 
public class PercolationUF implements IPercolate {
	private final int OUT_BOUNDS = -1;
	private int myOpenSites;
	/**
	 * Constructs a Percolation object for a nxn grid that that creates
	 * a IUnionFind object to determine whether cells are full
	 */
	IUnionFind full;
	IUnionFind perc;
public	int N;
public int[][] myGrid;
int index;
public QuickFind finder;
int SinkIndex;
int SourceIndex;
;
	public PercolationUF(int n) {
		myGrid = new int[n][n];
		int N = n;
		myOpenSites = 0;
		myGrid = new int[N][N];
		for (int[] row:myGrid){
			Arrays.fill(row, BLOCKED);
		}
		finder = new QuickFind(n*n+2);
		SinkIndex = n*n+1;
		SourceIndex = n*n;
		
	}

	/**
	 * Return an index that uniquely identifies (row,col), typically an index
	 * based on row-major ordering of cells in a two-dimensional grid. However,
	 * if (row,col) is out-of-bounds, return OUT_BOUNDS.
	 */
	int count = 0;
	public int getIndex(int row, int col) {
		// TODO complete getIndex
		if (row<0||row>=myGrid.length||col<0||col>=myGrid[0].length){ //checking if in bounds
		return OUT_BOUNDS;}
		count++;
		return row*myGrid.length + col; //
	}


		public void open(int i, int j) {
			// TODO complete open
			if (myGrid[i][j] != BLOCKED)
			return;
			myOpenSites++;
			myGrid[i][j] = OPEN;
			connect(i,j);

			}

	public boolean isOpen(int i, int j) {
		if(i<0||i>=myGrid.length||j<0||j>=myGrid[0].length){
			throw new IndexOutOfBoundsException("Index " + i + "," +j+ " is bad!");
		}
		// TODO complete isOpen
		return ((myGrid[i][j]==FULL)||(myGrid[i][j] == OPEN));
		
	}

	public boolean isFull(int i, int j) {
		if(i<0||i>=myGrid.length||j<0||j>=myGrid[0].length){
			throw new IndexOutOfBoundsException("Index " + i + "," +j+ " is bad!");
		}
		if(finder.connected(SourceIndex, getIndex(i,j))){
			return true;
		}
		// TODO complete isFull
		return false;
	}

	public int numberOfOpenSites() {
		// TODO return the number of calls to open new sites
		return myOpenSites;
	}

	public boolean percolates() {
		//if top row and btoom row are in teh same set, then percolates
		// TODO complete percolates
		if(finder.connected(SinkIndex, SourceIndex)){
			return true;
		}
		return false;
		
		}
		
	

	/**
	 * Connect new site (row, col) to all adjacent open sites
	 */
	private void connect(int row, int col) {
		if(row<0||row>=myGrid.length||col<0||col>=myGrid[0].length){
			return;
		}
		int perfIndex = getIndex(row,col);
		if(row-1>=0||row<=myGrid.length){ //checks up 
			if(isOpen(row-1,col)){
				finder.union(getIndex(row-1,col),perfIndex);
			}
		}
		if(row+1>=0||row+1<=myGrid.length){ //checks down 
			if(isOpen(row-1,col)){
				finder.union(getIndex(row-1,col),perfIndex);
			}
		}
		if(col-1>=0||col-1<=myGrid.length){ //checks left 
			if(isOpen(row-1,col)){
				finder.union(getIndex(row-1,col),perfIndex);
			}
		}
		if(col+1>=0||col+1<=myGrid.length){ //checks right
			if(isOpen(row-1,col)){
				finder.union(getIndex(row-1,col),perfIndex);
			}
		}
		// TODO complete connect
		
		
	}

}
