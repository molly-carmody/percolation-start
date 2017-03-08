import java.util.Random;

import javax.swing.JOptionPane;

/**
 * Compute statistics on Percolation afte performing T independent experiments on an N-by-N grid.
 * Compute 95% confidence interval for the percolation threshold, and  mean and std. deviation
 * Compute and print timings
 * 
 * @author Kevin Wayne
 * @author Jeff Forbes
 * @author Josh Hug
 */

public class PercolationStats {
	public static int RANDOM_SEED = 1234;
	public static Random ourRandom = new Random(RANDOM_SEED);


		   public PercolationStats(int N, int T){
		   // perform T independent experiments on an N-by-N grid
		   public double mean(){
			   return;
			   // sample mean of percolation threshold
		   }
		   public double stddev()   {
			   // sample standard deviation of percolation threshold
		   }
		   public double confidenceLow() {
			   // low  endpoint of 95% confidence interval
		   }
		   public double confidenceHigh() {
			   // high endpoint of 95% confidence interval
		   }
		   public static void main(String[] args){
			   // print out values for testing &  analysis
		   }
		}
}
