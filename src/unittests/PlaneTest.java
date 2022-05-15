/**
 *
 */
package unittests;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * @author èìéä
 *
 */
class PlaneTests {

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormalPoint() {
        try {
            // ============ Equivalence Partitions Tests ==============
            Point p1 = new Point(1, 2, 3);
            Point p2 = new Point(4, 5, 6);
            Point p3 = new Point(1, 7, 5);
            Plane myPlane = new Plane(p1, p2, p3);
            double normalLength = Math.sqrt(38) * 3;
            Vector normal = new Vector(-9 / normalLength, -6 / normalLength, 15 / normalLength);
            assertEquals( normal, myPlane.getNormal(),"Bad normal to plane");
        } catch (Exception ex) {
            fail("for vectors that are not zero vector should not throw an exception");
        }
    }
}