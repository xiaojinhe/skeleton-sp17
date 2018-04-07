package synthesizer;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/** Tests the ArrayRingBuffer class.
 *  @author Xiaojin He
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);

        for (int i = 0; i < 10; i++) {
            arb.enqueue(i);
        }
        Integer fillCount = arb.fillCount();
        Integer capacity = arb.capacity();
        assertEquals((Integer)10, fillCount);
        assertEquals((Integer) 10, capacity);

        Iterator<Integer> IntIterator = arb.iterator();

        while (IntIterator.hasNext()) {
            System.out.println(IntIterator.next());
        }

        for (int i = 0; i < 5; i++) {
            Integer val = arb.peek();
            assertEquals((Integer) i, val);
            Integer remove = arb.dequeue();
            assertEquals((Integer) i, remove);
        }

        fillCount = arb.fillCount();
        assertEquals((Integer) 5, fillCount);

        for (int x : arb) {
            System.out.println(x);
        }

    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
