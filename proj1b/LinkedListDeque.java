public class LinkedListDeque<Item> implements Deque<Item> {

/*    public static void main(String[] args) {
        LinkedListDeque<Integer> object = new LinkedListDeque<>();
        object.addFirst(5);
        object.addLast(17);
        object.addLast(9);
        object.addLast(15);
        System.out.println(object.size());
        System.out.println(object.isEmpty());
        object.printDeque();
        System.out.println(object.removeLast());
        System.out.println(object.removeFirst());
        object.printDeque();
        System.out.println(object.removeLast());
        System.out.println(object.removeFirst());
        object.printDeque();
        object.addFirst(5);
        object.addLast(17);
        object.addLast(9);
        object.addLast(15);
        object.printDeque();
        System.out.println(object.get(1));
        System.out.println(object.getRecursive(1));
    } */

    private class ItemNode {
        private Item item;
        private ItemNode next;
        private ItemNode prev;

        ItemNode(Item i, ItemNode p, ItemNode n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private ItemNode sentinel;
    private int size;

    /** Creates an empty linked list deque. */
    public LinkedListDeque() {
        sentinel = new ItemNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** Adds an item to the front of the Deque. */
    @Override
    public void addFirst(Item i) {
        //sentinel.next is the first item in the Deque, sentinel.prev is the last item in the Deque.
        ItemNode first = new ItemNode(i, sentinel, sentinel.next);
        sentinel.next.prev = first;
        sentinel.next = first;
        size++;
    }

    /** Adds an item to the back of the Deque. */
    @Override
    public void addLast(Item i) {
        // sentinel.prev is the last item in the Deque.
        ItemNode last = new ItemNode(i, sentinel.prev, sentinel);
        sentinel.prev.next = last;
        sentinel.prev = last;
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
        ItemNode p = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the Deque. If no such item exists, returns null. */
    @Override
    public Item removeFirst() {
        //sentinel.next is the first item at the front of the Deque.
        if (sentinel.next == sentinel) {
            return null;
        }
        ItemNode removeFirst = sentinel.next;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return removeFirst.item;
    }

    /** Removes and returns the item at the back of the Deque. If no such item exists, returns null. */
    @Override
    public Item removeLast() {
        //sentinel.prev is the last item at the end of the Deque.
        if (sentinel.prev == sentinel) {
            return null;
        }
        ItemNode removeLast = sentinel.prev;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return removeLast.item;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such
     * item exists, returns null. Must not alter the deque.
     */
    @Override
    public Item get(int index) {
        if (index >=  size || index < 0) {
            return null;
        }

        ItemNode p = sentinel.next;

        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    public Item getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        ItemNode result = getRecursiveHelper(index, sentinel.next);
        return result.item;
    }

    public ItemNode getRecursiveHelper(int index, ItemNode front) {
        if (index == 0) {
            return front;
        } else {
            return getRecursiveHelper(--index, front.next);
        }
    }
}

