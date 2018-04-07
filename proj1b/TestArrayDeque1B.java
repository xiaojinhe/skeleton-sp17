import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDeque1B {

    @Test
    public void testStudentArrayDeque() {

        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> sol = new ArrayDequeSolution<>();
        OperationSequence failureSequence = new OperationSequence();

        for (int i = 0; i < 1000; i++) {
            double random = StdRandom.uniform();
            if (random < 0.25) {
                sad.addFirst(i);
                sol.addFirst(i);
                DequeOperation opAf = new DequeOperation("addFirst", i);
                failureSequence.addOperation(opAf);
            } else if (random < 0.5) {
                sad.addLast(i);
                sol.addLast(i);
                DequeOperation opAl = new DequeOperation("addLast", i);
                failureSequence.addOperation(opAl);
            } else if (random < 0.75) {
                DequeOperation opIe = new DequeOperation("isEmpty");
                failureSequence.addOperation(opIe);
                assertEquals(failureSequence.toString(), sol.isEmpty(), sad.isEmpty());

                if (!sad.isEmpty() && !sol.isEmpty()) {
                    DequeOperation opGetFirst = new DequeOperation("get", 0);
                    failureSequence.addOperation(opGetFirst);
                    assertEquals(failureSequence.toString(), sol.get(0), sad.get(0));

                    Integer sadRemoveFirst = sad.removeFirst();
                    Integer solRemoveFirst = sol.removeFirst();
                    DequeOperation opRf = new DequeOperation("removeFirst");
                    failureSequence.addOperation(opRf);
                    assertEquals(failureSequence.toString(), solRemoveFirst, sadRemoveFirst);
                }
            } else {
                DequeOperation opIe = new DequeOperation("isEmpty");
                failureSequence.addOperation(opIe);
                assertEquals(failureSequence.toString(), sol.isEmpty(), sad.isEmpty());

                if (!sad.isEmpty() && !sol.isEmpty()) {
                    DequeOperation opGetLast = new DequeOperation("get", sol.size() - 1);
                    failureSequence.addOperation(opGetLast);
                    assertEquals(failureSequence.toString(), sol.get(sol.size() - 1), sad.get(sad.size() - 1));

                    Integer sadRemoveLast = sad.removeLast();
                    Integer solRemoveLast = sol.removeLast();
                    DequeOperation opRl = new DequeOperation("removeLast");
                    failureSequence.addOperation(opRl);
                    assertEquals(failureSequence.toString(), solRemoveLast, sadRemoveLast);
                }
            }
            DequeOperation opSize = new DequeOperation("size");
            failureSequence.addOperation(opSize);
            assertEquals(failureSequence.toString(), sol.size(), sad.size());
        }

        for (int i = 0; i < sol.size(); i++) {
            DequeOperation opGet = new DequeOperation("get", i);
            failureSequence.addOperation(opGet);
            Integer sadGet = sad.get(i);
            Integer solGet = sol.get(i);
            assertEquals(failureSequence.toString(), solGet, sadGet);
        }
    }

}
