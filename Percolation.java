import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private WeightedQuickUnionUF wquf;
    private boolean[][] open;
    private int size;
    private int top = 0;
    private int bottom;
    private int count;

    public Percolation(int n) {
        wquf = new WeightedQuickUnionUF(n * n + 2);
        open = new boolean[n][n];
        bottom = n * n + 1; // not +2 because 0 indexed
        size = n;
        count = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkIndex(row, col);
        if (isOpen(row, col))
            return;
        open[row - 1][col - 1] = true;
        count += 1;

        if (row == 1)
            wquf.union(0, xyto1D(row, col));

        if (row != 1 && isOpen(row - 1, col))
            wquf.union(xyto1D(row - 1, col), xyto1D(row, col));

        if (col != 1 && isOpen(row, col - 1))
            wquf.union(xyto1D(row, col - 1), xyto1D(row, col));

        if (row != size && isOpen(row + 1, col))
            wquf.union(xyto1D(row + 1, col), xyto1D(row, col));

        if (col != size && isOpen(row, col + 1))
            wquf.union(xyto1D(row, col + 1), xyto1D(row, col));

        if (row == size)
            wquf.union(bottom, xyto1D(row, col));


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkIndex(row, col);
        return open[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkIndex(row, col);
        return wquf.find(xyto1D(row, col)) == wquf.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return wquf.find(top) == wquf.find(bottom);
    }

    // return a 1D index from 2D indices
    private int xyto1D(int row, int col) {
        checkIndex(row, col);
        return ((row - 1) * size + col - 1) + 1; // +1 because extra node at first
    }

    // index tester
    private void checkIndex(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException();
    }

    // test client (optional)
    public static void main(String[] args) {
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                System.out.println(((i - 1) * 3 + j - 1) + 1);
            }
        }

    }
}
