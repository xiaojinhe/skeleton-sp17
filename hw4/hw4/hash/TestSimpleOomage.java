package hw4.hash;

import edu.princeton.cs.algs4.StdRandom;
import hw4.hash.SimpleOomage;
import org.junit.Test;
import sun.java2d.pipe.SpanShapeRenderer;


import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class TestSimpleOomage {

    @Test
    public void testHashCodeDeterministic() {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    @Test
    public void testHashCodePerfect() {
        int[] redList = new int[52];
        int[] greenList = new int[52];
        int[] blueList = new int[52];

        for (int i = 0; i < 52; i++) {
            redList[i] = i * 5;
            greenList[i] = i * 5;
            blueList[i] = i * 5;
        }

        HashSet<SimpleOomage> set = new HashSet<>();
        for (int i = 0; i < redList.length; i++) {
            for (int j = 0; j < greenList.length; j++) {
                for (int k = 0; k < blueList.length; k++) {
                    assertFalse(set.contains(new SimpleOomage(redList[i], greenList[j], blueList[k])));
                    set.add(new SimpleOomage(redList[i], greenList[j], blueList[k]));
                    assertTrue(set.contains(new SimpleOomage(redList[i], greenList[j], blueList[k])));
                }
            }
        }
    }

    @Test
    public void testEquals() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }


    @Test
    public void testHashCodeAndEqualsConsistency() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<>();
        hashSet.add(ooA);
        assertTrue(hashSet.contains(ooA2));
    }

    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;
        for (int i = 0; i < N; i += 1) {
            oomages.add(SimpleOomage.randomSimpleOomage());
        }
        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
    }
}