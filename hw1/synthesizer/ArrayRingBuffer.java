package synthesizer;

import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
        this.capacity = capacity;
        this.rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow.");
        }
        rb[last] = x;
        last = indexPlusOne(last);
        fillCount++;
    }

    private int indexPlusOne(int i) {
        if (i == capacity - 1) {
            i = 0;
            return i;
        }
        i = i + 1;
        return i;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update first
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow.");
        }
        T val = rb[first];
        rb[first] = null;
        first = indexPlusOne(first);
        fillCount--;
        return val;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer is Empty.");
        }
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArbIterator();
    }

    private class ArbIterator implements Iterator<T> {

        private int pointer;
        private int count;

        ArbIterator() {
            pointer = first;
            count = 0;
        }

        @Override
        public boolean hasNext() {
            return count != fillCount;
        }

        @Override
        public T next() {
            T element = rb[pointer];
            pointer = indexPlusOne(pointer);
            count++;
            return element;
        }
    }

}


