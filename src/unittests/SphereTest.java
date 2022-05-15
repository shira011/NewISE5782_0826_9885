/**
 *
 */
package unittests;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * @author shira swissa and talya moshe
 *
 */
class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        try {
            // ============ Equivalence Partitions Tests ==============
            double radius = 5;
            Point center = new Point(1, 2, 3);
            Sphere mySphere = new Sphere(center, radius);
            Vector normal = new Vector(4d / 5, 0, 3d / 5);
            assertEquals( normal, mySphere.getNormal(new Point(5, 2, 6)),"bad normal to sphere");
        } catch (Exception ex) {
            fail("for vectors that not zero vector does not need throw an exception");
        }
    }   }