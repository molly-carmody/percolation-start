import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Compute statistics on Percolation afte performing T independent experiments on an N-by-N grid.
 * Compute 95% confidence interval for the percolation threshold, and  mean and std. deviation
 * Compute and print timings
 * 
 * @author Kevin Wayne
 * @author Jeff Forbes
 * @author Josh Hug
 */

public class PercolationStats {
	public static int RANDOM_SEED = 1234;
	public static Random ourRandom = new Random(RANDOM_SEED);
	private static final int BLOCKED = 0;
	private static final int OPEN = 1;
	private static final int FULL = 2;
	
	private double[] openSites;
	private int[][] myGrid;
	private IPercolate percs;
	private int t;
	private int n;

	public PercolationStats(int N, int T) {
		if(N <= 0 || T <= 0) { //checks if N and T are valid/in bounds
			throw new IllegalArgumentException("Bad N or T");
		}
		t = T;
		n=N;

		myGrid = new int[N][N];
		openSites = new double[T];
		//iterates through the grid and marks each as blocked
		for(int i=0; i<myGrid.length; i++) {
			for(int j=0; j<myGrid.length; j++) {
				myGrid[i][j] = BLOCKED;
			}
		}

		//double start = System.nanoTime(); //starts timer 
		//until the system percolates, counts the number of sites opened in each run 
		for(int k=0; k < T; k++) {
			//percs = new PercolationDFS(N);
			percs = new PercolationUF(N);
			int OSites=0;
			
			while(!percs.percolates()) {
				int j;
				int i;
				i = ourRandom.nextInt(N); //generates random i and 
				j = ourRandom.nextInt(N);//random j to "grab" a random location
				if(!(percs.isOpen(i, j))){ //checks if its open and if not
									//it opens it 
					percs.open(i, j);
					OSites++;
				} 
			}
			openSites[k] = (double) OSites/(n*n);
		}
		//double end = System.nanoTime(); //ends timer
		//System.out.printf("total time = %2.3f\n", (end-start)/1e9);
	}

	//calculates the mean 
	public double mean() {
		double add=0;
		for(int l=0; l<openSites.length;l++){
			add += openSites[l]; //adds up all the number of opened sites from each run 
		}
		return (add/t); //finds the average
	}
	//calculates the standard deviation
	public double stddev() {
		double sum = 0;
		for(double k:openSites){ //iterates through the opensites
			double hm = k-mean(); //(x-u)
			sum+= hm*hm; //adds on (x-u)^2 to the overall sum
		}
		return Math.sqrt(sum/(t-1)); //finds SD
	}
	//returns lower end of 95% confidence interval 
	public double confidenceLow() {
		return mean() - ((1.96*stddev())/Math.sqrt(t));
	}
	//returns upper end of 95% confidence interval 
	public double confidenceHigh() {
		return mean() + ((1.96*stddev())/Math.sqrt(t));
	}
	//calculates the mean, SD, low confidence, and high confidence of
	//an example percolation and prints out the results
	public static void main(String[] args) {
		PercolationStats percc = new PercolationStats(5,5);
		double mea = percc.mean();
		double SD = percc.stddev();
		double CL = percc.confidenceLow();
		double CH = percc.confidenceHigh();

		//print out values
		System.out.println(mea);
		System.out.println(SD);
		System.out.println(CL);
		System.out.println(CL);
	}
}