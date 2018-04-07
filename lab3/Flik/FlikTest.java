import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {
    @Test
    public void FlikTest() {
        Integer a = 500;
        Integer b = 500;
        boolean exp = true;
        assertEquals(exp, Flik.isSameNumber(a, b));
    }
}
