
public class ArrayList<T> {
	
	T [] arr;
	int  size;
	
	
	@SuppressWarnings("hiding")
	private class Iterator<T> {
		
		private int nextItem;
		
		public Iterator() {
			nextItem = 0;
		}

		public boolean hasNext() {
			if (nextItem < size)
				return true;
			return false;
		}

		@SuppressWarnings("unchecked")
		public T next() {
			return (T) arr[nextItem++];
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList() {
		size = 0;
		arr = (T[]) new Object[10];
	}
	
	public Iterator<T> iterator() {
		return new Iterator<T>();
	}
	
	private void growArray () {
		@SuppressWarnings("unchecked")
		T[] newarr = (T[]) new Object[arr.length * 2];
		for (int i = 0; i < arr.length; i++) 
			newarr[i] = arr[i];
		arr = newarr;
	}

	public void add(T item) throws Exception {  // Best: O(1); Worst: O(1) -- "amortised"?
		if (size == arr.length)  // Array is full, so double the array
			growArray();
		arr[size++] = item;
	}


	public void add(int pos, T item) throws Exception {  // Best: O(1); Worst: O(n)
		if (pos < 0 || pos > size-1 )
			throw new Exception("List index out of bounds");
		if (size == arr.length)
			growArray();
		for (int i = size; i > pos; i--) {
			arr[i] = arr[i-1];
		}
		arr[pos] = item;
		++size;
	}


	public T get(int pos) throws Exception {  // // O(1)
		if (pos < 0 || pos > size-1 )
			throw new Exception("List index out of bounds");
		return arr[pos];
	}


	public T remove(int pos) throws Exception {  // Best O(1), worst O(n)
		if (pos < 0 || pos > size-1 )
			throw new Exception("List index out of bounds");
		T item = arr[pos];
		for (int i = pos; i < size-1; i++)
			arr[i] = arr[i+1];
		--size;
		return item;
	}

	public int size() {  // Best/worst: O(1)
		return size;
	}
	
	
	public static void main (String [] args) {
		ArrayList<String> names = new ArrayList<String>();

	}
	

}
