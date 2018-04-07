import java.util.Formatter;

/** Scheme-like pairs that can be used to form a list of integers.
 *  @author P. N. Hilfinger, Josh Hug, Melanie Cebula.
 */
public class IntList {
    /** First element of list. */
    public int head;
    /** Remaining elements of list. */
    public IntList tail;

    /** A List with head HEAD0 and tail TAIL0. */
    public IntList(int head0, IntList tail0) {
        head = head0;
        tail = tail0;
    }

    /** A List with null tail, and head = 0. */
    public IntList() {
        /* NOTE: public IntList () { }  would also work. */
        this(0, null);
    }
    /** Returns a list equal to L with all elements squared. Destructive. */
    public static void dSquareList(IntList L) {
        while (L != null) {
            L.head = L.head * L.head;
            L = L.tail;
        }
    }

    /** Returns a list equal to L with all elements squared. Non-destructive. */
    public static IntList squareListIterative(IntList L) {
        if (L == null) {
            return null;
        }
        IntList res = new IntList(L.head * L.head, null);
        IntList ptr = res;
        L = L.tail;
        while (L != null) {
            ptr.tail = new IntList(L.head * L.head, null);
            L = L.tail;
            ptr = ptr.tail;
        }
        return res;
    }

    /** Returns a list equal to L with all elements squared. Non-destructive. */
    public static IntList squareListRecursive(IntList L) {
        if (L == null) {
            return null;
        }
        return new IntList(L.head * L.head, squareListRecursive(L.tail));
    }

    /** DO NOT MODIFY ANYTHING ABOVE THIS LINE! */

    @Override
    public boolean equals(Object obj) {
        IntList otherList = (IntList) obj;
             
        IntList L1 = null;
        IntList L2 = null;
        for (L1 = this, L2 = otherList; L1 != null && L2 != null;
             L1 = L1.tail, L2 = L2.tail) {

            if (L1.head != L2.head) {
                return false;
            }
        }
        
        return L1 == L2; //Size check.
    }

    /** Test .equals. */
    public static void main(String... ignored) {
        // Write something to test .equals here.
      /**  IntList L1 = list(1,2,3),
        * L2 = list(1,2);
       System.out.println(L1.equals(L2));
    */ 
        //Fails because there is not a null check
        //I've added a simple check to the equals method to prevent this.
        IntList A = IntList.list(1, 2, 3);
       /* IntList B = IntList.list(4, 5, 6);
        IntList exp = IntList.list(1, 2, 3, 4, 5, 6);
        System.out.println(A);
        IntList catenateL = IntList.dcatenate2(A, B);
        System.out.println(catenateL); 
        System.out.println(A.equals(catenateL)); 
        System.out.println(A);
        System.out.println(exp.equals(catenateL));
        System.out.println(B);
        System.out.println(IntList.list(1, 2, 3, 4, 5, 6).equals(A)); */
        IntList expA = IntList.list(3, 2, 1);
        System.out.println("A; " + A);
        System.out.println("Expected A: " + expA);
        System.out.println("Reversed A: " + reverse2(A));
        System.out.println("A: " + A);
    }

    public static IntList catenate(IntList A, IntList B) {
        if (A == null) {
            return B;
        }
        IntList catenateList = new IntList(A.head, null);
        A = A.tail;
        IntList p1 = catenateList;
        while (A != null) {
            p1.tail = new IntList(A.head, null);
            A = A.tail;
            p1 = p1.tail;
        }
        IntList p2 = B;
        while (p2 != null) {
            p1.tail = new IntList(p2.head, null);
            p2 = p2.tail;
            p1 = p1.tail;
        }
        return catenateList;
    }

    public static IntList catenate2(IntList A, IntList B) {
        if (A == null) {
            return B;
        }
        return new IntList(A.head, catenate2(A.tail, B));
    }

    public static IntList dcatenate(IntList A, IntList B) {
        if (A == null) {
            return B;
        } 
        IntList p1 = A;
        while (p1.tail != null) {
            p1 = p1.tail;
        }
        p1.tail = B;
        return A;
    }

    public static IntList dcatenate2(IntList A, IntList B) {
        if (A == null) {
            return B;
        } 
        
        A.tail = dcatenate2(A.tail, B);
        return A;
    }

    public static IntList reverse(IntList A) {
        if (A == null || A.tail == null) {
            return A;
        }
        IntList reversed = reverse(A.tail);
        A.tail.tail = A;
        A.tail = null;
        return reversed;
    }

    public static IntList reverse2(IntList A) {
        IntList reversed = null;

        while (A != null) {
            IntList temp = A.tail;
            A.tail = reversed;
            reversed = A;
            A = temp;
        }
        return reversed;
    }

    /** DO NOT MODIFY ANYTHING BELOW THIS LINE! In fact, I wouldn't even
     * look below this line since it's likely to confuse you. */

    /** Return an integer value such that if x1 and x2 represent two
     *  IntLists that represent identical sequences of ints, then
     *  x1.hashCode() == x2.hashCode().  (Any class that overrides
     *  equals should override this method,) */
    @Override
    public int hashCode() {
        return head;
    }

    /** Returns a new IntList containing the ints in ARGS. You are not
     * expected to read or understand this method. */
    public static IntList list(Integer ... args) {
        IntList result, p;

        if (args.length > 0) {
            result = new IntList(args[0], null);
        } else {
            return null;
        }

        int k;
        for (k = 1, p = result; k < args.length; k += 1, p = p.tail) {
            p.tail = new IntList(args[k], null);
        }
        return result;
    }

    /** If a cycle exists in A, return an integer equal to
     *  the item number of the location where the cycle is detected.
     *  If there is no cycle, returns 0. */
    private int detectCycles(IntList A) {
        IntList tortoise;
        IntList hare;

        if (A == null) {
            return 0;
        }

        tortoise = hare = A;

        for (int cnt = 0;; cnt += 1) {
            cnt += 1;
            if (hare.tail != null) {
                hare = hare.tail.tail;
            } else {
                return 0;
            }

            tortoise = tortoise.tail;

            if (tortoise == null || hare == null) {
                return 0;
            }

            if (hare == tortoise) {
                return cnt;
            }
        }
    }

    /** Return a printable representation of an IntList. */
    @Override
    public String toString() {
        Formatter out = new Formatter();
        String sep;
        sep = "[";
        int cycleLocation = detectCycles(this);
        int cnt;

        cnt = 0;
        for (IntList p = this; p != null; p = p.tail) {
            out.format("%s%d", sep, p.head);
            sep = ", ";

            cnt += 1;
            if (cnt > cycleLocation && cycleLocation > 0) {
                out.format("... (cycle)");
                break;
            }
        }
        out.format("]");
        return out.toString();
    }

}