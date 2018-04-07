public class OffByOne implements CharacterComparator {

    /** Returns true if two char is next to each other. */
    @Override
    public boolean equalChars(char x, char y) {
        int offset = x - y;

        if (offset == 1 || offset == -1) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        OffByOne object = new OffByOne();
        System.out.println(object.equalChars('a', 'b'));
        System.out.println(object.equalChars('r', 'q'));
        System.out.println(object.equalChars('a', 'a'));
        System.out.println(object.equalChars('a', 'e'));
    }

}
