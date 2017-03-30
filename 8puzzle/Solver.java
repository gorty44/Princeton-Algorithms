import java.util.ArrayList;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
	private BoardWrapper goalBW;
	private Board initial;
	private ArrayList<Board> trace = new ArrayList<>();

	public Solver(Board initial) {
		this.initial = initial;
		MinPQ<BoardWrapper> pq = new MinPQ<>();
		if (isSolvable())
			goalBW = solve(initial, pq);
	}

	private class BoardWrapper implements Comparable<BoardWrapper> {
		private Board board;
		private int priority;
		private int move;

		public BoardWrapper(Board board, int move) {
			this.board = board;
			this.move = move;
			this.priority = this.board.manhattan() + this.move;
		}

		@Override
		public int compareTo(BoardWrapper o) {
			if (this.priority > o.priority)
				return 1;
			else if (this.priority < o.priority)
				return -1;
			else {
				if (this.board.hamming() > o.board.hamming())
					return 1;
				else if (this.board.hamming() < o.board.hamming())
					return -1;
				return 0;
			}
		}
	}

	private BoardWrapper solve(Board initial, MinPQ<BoardWrapper> pq) {
		pq.insert(new BoardWrapper(initial, 0));
		BoardWrapper minimum = pq.min();
		//System.out.println("DODAJE" + minimum.board);
		trace.add(minimum.board);

		if (minimum.board.isGoal())
			return minimum;

		while (!minimum.board.isGoal()) {
			Iterable<Board> it = minimum.board.neighbors();
			for (Board b : it)
				if (!trace.contains(b)) {
					pq.insert(new BoardWrapper(b, minimum.move + 1));
					//System.out.println("WKLADAM");
				}
			minimum = pq.delMin();
			if (!trace.contains(minimum.board)) {
				trace.add(minimum.board);
				//System.out.println("DODAJE" + minimum.board);
			}
		}
		return minimum;
	}

	public boolean isSolvable() {
		MinPQ<BoardWrapper> pq = new MinPQ<>();
		ArrayList<Board> sTrace = new ArrayList<>();
		pq.insert(new BoardWrapper(initial, 0));
		BoardWrapper minimum = pq.min();
		sTrace.add(minimum.board);

		// TWIN
		MinPQ<BoardWrapper> twin_pq = new MinPQ<>();
		ArrayList<Board> twin_sTrace = new ArrayList<>();
		twin_pq.insert(new BoardWrapper(initial.twin(), 0));
		BoardWrapper twin_minimum = twin_pq.min();
		twin_sTrace.add(twin_minimum.board);

		if (minimum.board.isGoal())
			return true;
		if (twin_minimum.board.isGoal())
			return false;

		while (!minimum.board.isGoal() && !twin_minimum.board.isGoal()) {
			Iterable<Board> it = minimum.board.neighbors();
			for (Board b : it)
				if (!sTrace.contains(b))
					pq.insert(new BoardWrapper(b, minimum.move + 1));
			minimum = pq.delMin();
			if (!sTrace.contains(minimum.board))
				sTrace.add(minimum.board);

			// TWIN
			Iterable<Board> twin_it = twin_minimum.board.neighbors();
			for (Board b : twin_it)
				if (!twin_sTrace.contains(b))
					twin_pq.insert(new BoardWrapper(b, twin_minimum.move + 1));
			twin_minimum = twin_pq.delMin();
			if (!twin_sTrace.contains(twin_minimum.board))
				twin_sTrace.add(twin_minimum.board);

		}
		if (minimum.board.isGoal())
			return true;
		return false;
	}

	public int moves() {
		if (isSolvable())
			return trace.size() - 1;
		return -1;
	}

	public Iterable<Board> solution() {
		if (isSolvable())
			return trace;
		return null;
	}

	public static void main(String[] args) {
		// int[][] tiles = { { 1, 2, 3 }, { 0, 4, 8 }, { 7, 6, 5 } };
		int[][] tiles = { { 1, 2, 3 }, { 0, 7, 6 }, { 5, 4, 8 } };
		// int[][] tiles = { { 0,1 }, { 2, 3 } };
		Board b = new Board(tiles);
		Solver s = new Solver(b);
		ArrayList<Board> it = (ArrayList<Board>) s.solution();
		// for (Board item : it)
		// System.out.println(item);
		System.out.println("number of moves: " + s.moves());
		System.out.println("solution length " + it.size());
		// System.out.println(s.isSolvable());
	}
}
