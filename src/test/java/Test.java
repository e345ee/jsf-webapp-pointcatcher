import itmo.web.demo1.utils.PointHitManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class Test {

   
    @Test
    public void testHit() {

        assertTrue(PointHitManager.Checker.hit(1, 0.1, 2));

        assertTrue(PointHitManager.Checker.hit(-0.2, -0.5, 2));

        assertTrue(PointHitManager.Checker.hit(0.5, -0.5, 1));

        assertFalse(PointHitManager.Checker.hit(3, 3, 1));
    }


    @Test
    public void testInRect() {
        assertTrue(PointHitManager.Checker.hit(0.5, 0.2, 1));
        assertTrue(PointHitManager.Checker.hit(1.0, 0.4, 2));

        assertFalse(PointHitManager.Checker.hit(-1, 0.2, 1));
        assertFalse(PointHitManager.Checker.hit(0.5, 2, 1));
    }

    
    @Test
    public void testInTriangle() {
        assertTrue(PointHitManager.Checker.hit(-0.2, -0.5, 2));
        assertTrue(PointHitManager.Checker.hit(-1, -0.5, 2));
        assertFalse(PointHitManager.Checker.hit(0.1, -0.2, 1));
        assertFalse(PointHitManager.Checker.hit(-1, 1, 1));
    }


    @Test
    public void testInCircle() {
        assertTrue(PointHitManager.Checker.hit(0.5, -0.5, 1));
        assertTrue(PointHitManager.Checker.hit(0, -1, 1));
        assertFalse(PointHitManager.Checker.hit(1, -1, 1));
        assertFalse(PointHitManager.Checker.hit(-0.1, -0.5, 1)); }

    @Test
    public void testValidatorX() {
        assertTrue(PointHitManager.Validator.validateX(0));
        assertFalse(PointHitManager.Validator.validateX(5));
    }
    @Test
    public void testValidatorY() {
        assertTrue(PointHitManager.Validator.validateY(1));
        assertFalse(PointHitManager.Validator.validateY(-6));
    }
    @Test
    public void testValidatorR() {
        assertTrue(PointHitManager.Validator.validateR(1.5));
        assertFalse(PointHitManager.Validator.validateR(2.7));
    }
}
