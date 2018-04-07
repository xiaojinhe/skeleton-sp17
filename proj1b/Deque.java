/** This interface defines a Deque contains methods listed below. */
public interface Deque<Item> {
    void addFirst(Item i);

    void addLast(Item i);

    boolean isEmpty();

    int size();

    void printDeque();

    Item removeFirst();

    Item removeLast();

    Item get(int index);

}
