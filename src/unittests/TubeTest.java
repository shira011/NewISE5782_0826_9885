/**
 *
 */
package unittests;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * @author èìéä
 *
 */
class TubeTests {

    @Test
    public void testGetNormal() {
        try {
            // ============ Equivalence Partitions Tests ==============
            Ray myRay=new Ray(new Point(1, 0, 0), new Vector(0, 1, 0));
            Tube myTube=new Tube(myRay,5);
            Vector normal = new Vector(1,0,0);
            assertEquals(normal , myTube.getNormal(new Point(3,1,0)),"Bad normal to tube");
           // assertEquals("Bad normal to tube", normal , myTube.getNormal(new Point(3,1,0)));

            // =============== Boundary Values Tests ==================
            // Check if the distance between a point that is on the perimeter and the center point is the same as the radius.
            double lengthNormal=Math.sqrt(21);
            normal=new Vector(4d/lengthNormal, 0, Math.sqrt(5)/lengthNormal);
            assertEquals(normal , myTube.getNormal(new Point(5,4,Math.sqrt(5))),"Bad normal to tube");
        }
        catch (Exception e) {
            fail("for vectors that not zero vector does not need throw an exception");
        }


    }

}