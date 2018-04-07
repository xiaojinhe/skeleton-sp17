public class ArrayDeque<Item> implements Deque<Item> {
   /* public static void main(String[] args) {
        ArrayDeque<Integer> object = new ArrayDeque<>();
        for (int i = 8; i > 0; i--) {
            object.addFirst(i);
        }
        for (int i = 9; i < 17; i++) {
            object.addLast(i);
        }
        System.out.println(object.size());
        object.printDeque();
        for (int i = 0; i < 7; i++) {
            System.out.print(object.removeFirst() + " ");
        }
        System.out.println();
        for (int i = 0; i < 7; i++) {
            System.out.print(object.removeLast() + " ");
        }
        System.out.println();
        object.printDeque();
        System.out.println(object.isEmpty());
        System.out.println(object.size());
        for (int i = 7; i > 0; i--) {
            object.addFirst(i);
        }
        object.printDeque();
        String s = object.get(1) + " " + object.get(3) + " " + object.get(5) + " " + object.get(7) + " " + object.get(10);
        System.out.println(s);
    } */

    private Item[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** Creates an empty array deque. */
    public ArrayDeque() {
        items = (Item[]) new Object[8];
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    /** Resizes the underlying array to the target capacity. */
    private void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];
        int p = nextFirst;
        for (int i = 1; i < size + 1; i++) {
            p = plusOne(p);
            a[i] = items[p];
        }
        items = a;
        nextFirst = 0;
        nextLast = size + 1;
    }

    /** Resizes the underlying array to half of the size when the array is too empty (ratio of size to items.length is
     * less than 0.25, for arrays of length 16 or more. For smaller arrays, do not need to resize if the usage factor
     * is arbitrarily low.
     */
    private void halfArraySize() {
        if (items.length >= 16 && ((double) size) / items.length < 0.25) {
            resize(items.length / 2);
        }
    }

    /** Adds an item to the front of the Deque. */
    @Override
    public void addFirst(Item i) {
        // check array is full - 2. If so , resize the array to double the size.
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = i;
        nextFirst = minusOne(nextFirst);
        size++;
    }

    /** Returns the index of nextFirst when add an item to the front of the Deque or the index of the nextLast when an
     * item is deleted from the end of the Deque. Also returns the index of the last item when input is nextLast.
     * @param x
     * @return
     */
    public int minusOne(int x) {
        if (x == 0) {
            x = items.length - 1;
        } else {
            x--;
        }
        return x;
    }

    /** Returns the index of nextLast when add an item to the end of the Deque or the index of the nextFast when an
     * item is deleted from the front of the Deque. Also returns the index of the first item when input is nextFirst.
     * @param x
     * @return
     */
    public int plusOne(int x) {
        if (x == items.length - 1) {
            x = 0;
        } else {
            x++;
        }
        return x;
    }

    /** Adds an item to the back of the Deque. */
    @Override
    public void addLast(Item i) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = i;
        nextLast = plusOne(nextLast);
        size++;
    }

    /** Returns true if deque is empty, false otherwise. */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the Deque. */
    @Override
    public int size() {
        return size;
    }

    /** Prints the items in the Deque from first to last, separated by a space. */
    @Override
    public void printDeque() {
        int pointer = nextFirst;
        for (int i = 0; i < size; i++) {
            pointer = plusOne(pointer);
            System.out.print(items[pointer] + " ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the Deque. If no such item exists, returns null. */
    @Override
    public Item removeFirst() {
        if (size == 0) {
            return null;
        }
        nextFirst = plusOne(nextFirst);
        Item remove = items[nextFirst];
        items[nextFirst] = null;
        size--;
        halfArraySize();
        return remove;
    }

    /** Removes and returns the item at the back of the Deque. If no such item exists, returns null. */
    @Override
    public Item removeLast() {
        //sentinel.prev is the last item at the end of the Deque.
        if (size == 0) {
            return null;
        }
        nextLast = minusOne(nextLast);
        Item remove = items[nextLast];
        items[nextLast] = null;
        size--;
        halfArraySize();
        return remove;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such
     * item exists, returns null. Must not alter the deque.
     */
    @Override
    public Item get(int index) {
        if (index >=  size || index < 0) {
            return null;
        }
        int p = index + nextFirst + 1;
        if (p > items.length - 1) {
            p = p - items.length;
        }
        return items[p];
    }
}
