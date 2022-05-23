package geometries;

import primitives.Ray;
import primitives.Vector;

import primitives.Point;

import java.util.List;

public class Cylinder extends Tube {
    double height;
    /*************** ctor *****************/
    /**
     * ctor that gets 3 parameters
     * @param radius
     * @param ray
     * @param height
     */
    public Cylinder(double radius, Ray ray, double height) {
        super(ray, radius);
        if (height<0)
            throw new IllegalArgumentException("height must be bigger than zero");
        this.height = height;
    }
    /*************** calculating functions *****************/

    /*************** get *****************/
    /**
     * @return the height of the cylinder
     */
    public double getHeight() {return height;}
    /*************** admin *****************/
    /**
     * @param p
     * @return the noraml
     */
    @Override
    public Vector getNormal(Point p) {
        // TODO Auto-generated method stub
        Point p0 = this.axisRay.getPoint();
        Vector v = this.axisRay.getVector();
        Vector pSUBp0 = p.subtract(p0);
        double t = v.dotProduct(pSUBp0);
        if (t == 0) {
            return v.scale(-1);
        }
        if(t == height){
            return v;
        }
        Vector u = this.axisRay.getVector().scale(t);
        Point o = this.axisRay.getPoint().add(u);
        return p.subtract(o).normalize();
    }

    @Override
    public String toString() {return "Cylinder [height=" + height + "]";
    }


}