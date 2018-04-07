package synthesizer;
import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T> {

    int capacity(); // return size of the buffer
    int fillCount(); // return number of items currently in the buffer
    void enqueue(T x); // add item x to the end of the queue
    T dequeue(); // delete and return item from the front
    T peek(); // return item from the front

    @Override
    Iterator<T> iterator();

    /** Return true if the buffer is empty or false. */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /** Return true if the buffer is full or false. */
    default boolean isFull() {
        return fillCount() == capacity();
    }


}
