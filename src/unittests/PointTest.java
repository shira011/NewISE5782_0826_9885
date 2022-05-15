package unittests;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Vector;

/**
 * @author äîçùá ùìé
 *
 */
class PointTests {

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() {

        Point p= new Point(0, 0, 1);
        try {
            assertEquals(new Vector(0, 0, 1),p.subtract(new Point(0, 0, 0)), "error with add func");
        } catch (Exception e) {
            fail("error with add func");
        }


    }

    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {

        Point p= new Point(0, 0, 0);
        assertEquals(new Point(0, 0, 1),p.add(new Vector(0, 0, 1)), "error with add func");
        try
        {
            Point point=new Point(1,1,1);
            assertEquals( point, point.add(new Vector(0,0,0)),"Function add doesnt work correct");
            fail("No need to throw exception");
        }
        catch (Exception e) {}
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        double d=12;
        Point p= new Point(2, 2, 2);
        assertEquals(d,p.distanceSquared( new Point(0, 0, 0)), "error with DistanceSquared  func");
    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void testDistance() {
        double d= Math.sqrt(12);
        Point p= new Point(2, 2, 2);
        assertEquals(d,p.distanceSquared( new Point(0, 0, 0)), "error with Distance func");
    }

}