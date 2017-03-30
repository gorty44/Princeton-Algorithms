import edu.princeton.cs.algs4.Stack;

public class Board {
	private int[][] board;
	private int n;

	public Board(int[][] blocks) {
		n = blocks.length;
		board = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				board[i][j] = blocks[i][j];
			}
	}

	public int dimension() {
		return n;
	}

	public int hamming() {
		int count = -1;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (board[i][j] != (n * i + j + 1))
					count++;
		return count;
	}

	public int manhattan() {
		int count = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if ((board[i][j] != (n * i + j + 1)) && (board[i][j] != 0)) {
					int targetI = (board[i][j] - 1) / n;
					int targetJ = (board[i][j] - 1) % n;
					count += Math.abs(targetI - i) + Math.abs(targetJ - j);
				}
		return count;
	}

	public boolean isGoal() {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if ((i == n - 1 && j == n - 1) && board[i][j] == 0)
					break;
				if (board[i][j] != (n * i + j + 1))
					return false;
			}
		return true;
	}

	public Board twin() {
		int[][] tiles = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				tiles[i][j] = board[i][j];
		int iBlank = 0, jBlank = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (tiles[i][j] == 0) {
					iBlank = i;
					jBlank = j;
				}
		if (iBlank == 0 && jBlank == 0)
			exch(tiles, n - 1, n - 1, 0, n - 1);
		else if (iBlank == 0 && jBlank == n - 1)
			exch(tiles, n - 1, 0, n - 1, n - 1);
		else if (iBlank == n - 1 && jBlank == 0)
			exch(tiles, 0, 0, 0, n - 1);
		else if (iBlank == n - 1 && jBlank == n - 1)
			exch(tiles, 0, 0, n - 1, 0);
		else
			exch(tiles, 0, 0, n - 1, n - 1);
		return new Board(tiles);
	}

	private static void exch(int[][] tiles, int i1, int j1, int i2, int j2) {
		int temp = tiles[i1][j1];
		tiles[i1][j1] = tiles[i2][j2];
		tiles[i2][j2] = temp;
	}

	public Iterable<Board> neighbors() {
		int iPos = 0, jPos = 0;
		Stack<Board> stack = new Stack<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] == 0) {
					iPos = i;
					jPos = j;
				}
			}
		}
		if ((iPos == 0 && jPos == 0) || (iPos == n - 1 && jPos == 0) || (iPos == 0 && jPos == n - 1)
				|| (iPos == n - 1 && jPos == n - 1)) {
			Board n1 = new Board(this.board);
			Board n2 = new Board(this.board);
			if (iPos == 0 && jPos == 0) {
				exch(n1.board, iPos, jPos, iPos, jPos + 1);
				exch(n2.board, iPos, jPos, iPos + 1, jPos);
			} else if (iPos == n - 1 && jPos == 0) {
				exch(n1.board, iPos, jPos, iPos - 1, jPos);
				exch(n2.board, iPos, jPos, iPos, jPos + 1);
			} else if (iPos == 0 && jPos == n - 1) {
				exch(n1.board, iPos, jPos, iPos, jPos - 1);
				exch(n2.board, iPos, jPos, iPos + 1, jPos);
			} else {
				exch(n1.board, iPos, jPos, iPos - 1, jPos);
				exch(n2.board, iPos, jPos, iPos, jPos - 1);
			}
			stack.push(n1);
			stack.push(n2);
		} else if ((iPos == 0) || (iPos == n - 1) || (jPos == 0 && (iPos != 0 && iPos != n - 1))
				|| (jPos == n - 1 && (iPos != 0 && iPos != n - 1))) {
			Board n1 = new Board(this.board);
			Board n2 = new Board(this.board);
			Board n3 = new Board(this.board);
			if (iPos == 0) {
				exch(n1.board, iPos, jPos, iPos, jPos - 1);
				exch(n2.board, iPos, jPos, iPos, jPos + 1);
				exch(n3.board, iPos, jPos, iPos + 1, jPos);
			} else if (iPos == n - 1) {
				exch(n1.board, iPos, jPos, iPos, jPos - 1);
				exch(n2.board, iPos, jPos, iPos, jPos + 1);
				exch(n3.board, iPos, jPos, iPos - 1, jPos);
			} else if (jPos == 0 && (iPos != 0 && iPos != n - 1)) {
				exch(n1.board, iPos, jPos, iPos - 1, jPos);
				exch(n2.board, iPos, jPos, iPos + 1, jPos);
				exch(n3.board, iPos, jPos, iPos, jPos + 1);
			} else {
				exch(n1.board, iPos, jPos, iPos - 1, jPos);
				exch(n2.board, iPos, jPos, iPos + 1, jPos);
				exch(n3.board, iPos, jPos, iPos, jPos - 1);
			}
			stack.push(n1);
			stack.push(n2);
			stack.push(n3);
		} else {
			Board n1 = new Board(this.board);
			Board n2 = new Board(this.board);
			Board n3 = new Board(this.board);
			Board n4 = new Board(this.board);

			exch(n1.board, iPos, jPos, iPos - 1, jPos);
			exch(n2.board, iPos, jPos, iPos + 1, jPos);
			exch(n3.board, iPos, jPos, iPos, jPos - 1);
			exch(n4.board, iPos, jPos, iPos, jPos + 1);

			stack.push(n1);
			stack.push(n2);
			stack.push(n3);
			stack.push(n4);
		}
		return stack;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(n + "\n");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				s.append(String.format("%2d ", board[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	@Override
	public boolean equals(Object x) {
		if (this == x)
			return true;
		if (x == null)
			return false;
		if (this.getClass() != x.getClass())
			return false;
		Board that = (Board) x;
		if (this.dimension() != that.dimension())
			return false;
		for (int i = 0; i < this.n; i++)
			for (int j = 0; j < this.n; j++)
				if (this.board[i][j] != that.board[i][j])
					return false;
		return true;
	}

	public static void main(String[] args) {
		int blocks[][] = { { 1, 0 }, { 2, 3 } };
		Board b = new Board(blocks);
		System.out.println(b);
		// System.out.println(b.hamming());
		// System.out.println(b.manhattan());
		System.out.println(b.twin());
		// System.out.println(b.isGoal());

		// Iterable<Board> s = b.neighbors();
		// for (Board item : s)
		// System.out.println(item);
	}
}
