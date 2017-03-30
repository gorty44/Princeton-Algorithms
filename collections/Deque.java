
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private class Node {
		private Node next;
		private Item item;
	}

	private Node first;
	private Node last;
	private int N;

	public boolean isEmpty() {
		return N == 0;
	}

	public int size() {
		return N;
	}

	public void addFirst(Item item) {
		if (item == null)
			throw new NullPointerException();
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		first.next = oldFirst;
		if (isEmpty())
			last = first;
		N++;
	}

	public void addLast(Item item) {
		if (item == null)
			throw new NullPointerException();
		Node oldLast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		if (isEmpty())
			first = last;
		else
			oldLast.next = last;
		N++;
	}

	public Item removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException();
		Item item = first.item;
		first = first.next;
		N--;
		return item;
	}

	public Item removeLast() {
		if (isEmpty())
			throw new NoSuchElementException();
		Item item;
		if (last == first) {
			item = removeFirst();
		} else {
			Node x = this.first;
			while (x.next.next != null) {
				x = x.next;
			}
			item = last.item;
			last = x;
			last.item = x.item;
			last.next = null;
			N--;
		}
		return item;
	}

	@Override
	public Iterator<Item> iterator() {
		return new ListIterator();
	}

	private class ListIterator implements Iterator<Item> {

		private Node current = first;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			Item item = current.item;
			current = current.next;

			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	public static void main(String[] args) {
		Deque<String> deq = new Deque<>();

		deq.addFirst("first");
		deq.addLast("last");
		deq.removeFirst();
		deq.removeLast();

	}
}
