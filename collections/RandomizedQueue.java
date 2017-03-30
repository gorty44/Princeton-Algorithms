import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	
	private Item[] a = (Item[]) new Object[1];
	private int N;

	private void resize(int cap) {
		
		Item[] temp = (Item[]) new Object[cap];
		for (int i = 0; i < N; i++) {
			temp[i] = a[i];
		}
		a = temp;
	}

	public boolean isEmpty() {
		return N == 0;
	}

	public int size() {
		return N;
	}

	public void enqueue(Item item) {
		if (item == null)
			throw new NullPointerException();
		if (N == a.length)
			resize(a.length * 2);
		a[N++] = item;
	}

	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException();
		int i = StdRandom.uniform(N);
		Item item = a[i];
		a[i] = a[N - 1];
		a[--N] = null;
		if (N > 0 && N == a.length / 4)
			resize(a.length / 2);
		return item;
	}

	public Item sample() {
		if (isEmpty())
			throw new NoSuchElementException();
		int i = StdRandom.uniform(N);
		return a[i];
	}

	@Override
	public Iterator<Item> iterator() {
		return new QueueIterator();
	}

	private class QueueIterator implements Iterator<Item> {
		private RandomizedQueue<Item> rq;

		public QueueIterator() {
			rq = new RandomizedQueue<Item>();
			rq.a = a;
			rq.N = N;
		}

		private int i = N;

		@Override
		public boolean hasNext() {
			return i > 0;
		}

		@Override
		public Item next() {
			if (i == 0)
				throw new NoSuchElementException();
			i--;
			return rq.dequeue();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public static void main(String[] args) {
		RandomizedQueue<Integer> rq = new RandomizedQueue<>();
		for (int i = 0; i < 20; i++)
			rq.enqueue(i);

		System.out.println(rq.dequeue());
		System.out.println(rq.dequeue());
		System.out.println();
		for (int i : rq)
			System.out.println(i);

	}
}
