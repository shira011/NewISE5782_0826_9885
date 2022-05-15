/**
 *
 */
package unittests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;

/**
 * @author äîçùá ùìé
 *
 */
class VectorTests {

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAddVector() {
        Vector v;
        Vector v2;
        Vector v3;
        try {
            v=new Vector(1,1,1);
            assertEquals(new Vector(2,2,2),v.add(new Vector(1,1,1)), "error with add func");
        }
        catch (Exception e)
        {
            fail("Add() for vectors that not zero vector does not need throw an exception");
        }
        try
        {
            v= new Vector(-1,-6,-4.2);
            v2= new Vector(-2,-5.1,-9);
            v3=new Vector(-3,-11.1,-13.2);
            assertTrue(v3.equals(v.add(v2)), "Add() Did not add the vector correct");
        }
        catch (Exception e)
        {
            fail("Add() for vectors that not zero vector does not need throw an exception");
        }

        try
        {
            v= new Vector(-1,8,23);
            v2= new Vector(6,-5.1,3);
            v3=new Vector(5,2.9,26);
            assertTrue(v3.equals(v.add(v2)), "Add() Did not add the vector correct");
        }
        catch (Exception e)
        {
            fail("Add() for vectors that not zero vector does not need throw an exception");
        }
        try
        {
            v= new Vector(-1,8,23);
            //add (0,0,0):
            assertTrue(v.equals(new Point(0,0,0).add(v)), "Add() Did not add the vector correct");

            @SuppressWarnings("unused")
            Vector vZero = new Vector(0,0,0);
            //if we don't get an exception it is didn't work correct
            fail("can not create a new vector that his head equals to zero vector");
        }
        catch (IllegalArgumentException e) {}
        catch (Exception e) {}
    }
    public void testSubtract()
    {
        // ============ Equivalence Partitions Tests ==============
        Vector v1;
        Vector v2;
        Vector vTry;
        try
        {
            v1 = new Vector(2,4,6);
            v2=new Vector(7,8,9);
            vTry=new Vector(-5,-4,-3);
            assertTrue(vTry.equals(v1.subtract(v2)), "Subtract() Did not sub the vector correct");
        }
        catch (Exception e)
        {
            fail("Subtract() for vectors that not zero vector does not need throw an exception");
        }

        try
        {
            v1 = new Vector(9,5,4);
            v2=new Vector(5,2,3);
            vTry=new Vector(4,3,1);
            assertTrue(vTry.equals(v1.subtract(v2)), "Subtract() Did not sub the vector correct");
        }
        catch (Exception e)
        {
            fail("Subtract() for vectors that not zero vector does not need throw an exception");
        }

        try
        {
            v1 = new Vector(-5,-5,-4);
            v2=new Vector(-9,-1,-12);
            vTry=new Vector(4,-4,8);
            assertTrue(vTry.equals(v1.subtract(v2)), "Subtract() Did not sub the vector correct");
        }
        catch (Exception e)
        {
            fail("Subtract() for vectors that not zero vector does not need throw an exception");
        }

        try
        {
            v1 = new Vector(-5,-5,-4);
            v2=new Vector(3,9,4);
            vTry=new Vector(-8,-14,-8);
            assertTrue(vTry.equals(v1.subtract(v2)), "Subtract() Did not sub the vector correct");
        }
        catch (Exception e)
        {
            fail("Subtract() for vectors that not zero vector does not need throw an exception");
        }


        // =============== Boundary Values Tests ==================

        try
        {
            v1 = new Vector(-5,-5,-4);
            assertTrue(v1.equals(v1.subtract(new Vector(0,0,0))), "Subtract() Did not sub the vector correct when the other vector is the zero vector");

            @SuppressWarnings("unused")
            Vector vZero = new Vector(0,0,0);
            //if we don't get an exception it is didn't work correct
            fail("can not create a new vector that his head equals to zero vector");

        }
        catch (IllegalArgumentException e) {}
        catch (Exception e) {}
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testScale() {
        Vector v=new Vector(1,1,1);
        assertEquals(new Vector(2,2,2),v.scale(2), "error with Scale func");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {

        try{Vector v=new Vector(0,0,0);
            assertEquals(0,v.add(new Vector(1,1,1)), "error with DotProduct func need to be 0");
            fail("can not create a new vector that his head equals to zero vector");
        }
        catch(IllegalArgumentException ex) {

        }
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {
        Vector v=new Vector(1,1,1);
        try {
            assertEquals(new Vector(0,0,0),v.add(new Vector(0,0,0)), "error with CrossProduct func");
            fail("can not create a new vector that his head equals to zero vector");}
        catch(IllegalArgumentException ex) {

        }
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {

        Vector v=new Vector(1,1,1);
        assertEquals(3,v.lengthSquared(), "error with LengthSquared  func");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {
        double d= Math.sqrt(3);
        Vector v=new Vector(1,1,1);
        assertEquals(d,v.length(), "error with Length func");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        Vector v=new Vector(1,1,1);
        double d=1.0/ Math.sqrt(3);
        assertEquals(new Vector(d,d,d),v.normalize(), "error with Distance func");
    }


}