public class OffByN implements CharacterComparator {
    private int n;

    public OffByN(int N) {
        n = N;
    }

    /** Returns true if two characters that are off by N. */
    @Override
    public boolean equalChars(char x, char y) {
        int offset = x - y;

        if (offset == n || offset == -n) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        OffByN object = new OffByN(5);
        System.out.println(object.equalChars('a', 'f'));
        System.out.println(object.equalChars('b', 'g'));
        System.out.println(object.equalChars('a', 'a'));
        System.out.println(object.equalChars('a', 'e'));
    }
}
