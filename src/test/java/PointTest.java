import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PointTest {
    @Test
    public void testPoint1() {
        assertTrue("checking point x = -1; y = -1; r = 2", AreaChecker.isHit(-1, -1, 2));
    }

    @Test
    public void testPoint2() {
        assertTrue("checking point x = -4; y = -2.5; r = 5", AreaChecker.isHit(-4, -2.5, 5));
    }

    @Test
    public void testPoint3() {
        assertFalse("checking point x = 1.7; y = 2.3; r = 3.75", AreaChecker.isHit(1.7, 2.3, 3.75));
    }

    @Test
    public void testPoint4() {
        assertFalse("checking point x = -2; y = 1; r = 2", AreaChecker.isHit(-2, 1, 2));
    }

}
