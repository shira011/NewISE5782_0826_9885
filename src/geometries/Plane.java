package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;
import java.util.LinkedList;

public class Plane extends Geometry {
    final Point q0;
    final Vector normal;


    /*************** ctors *****************/
    /**
     * ctor that gets 2 parameters
     * @param q2
     * @param normal2
     */

    public Plane(Vector normal2,Point q2) {
        this.normal = normal2.normalize();
        this.q0 = q2;
    }
    /**
     * ctor that gets 3 points
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point p1,Point p2,Point p3) {
        q0 = p1;
        Vector U = (Vector) p2.subtract(p1);
        Vector V = (Vector) p3.subtract(p1);
        Vector N = U.crossProduct(V);
        normal = N.normalize();
    }
    /*************** get *****************/
    /**
     * @return q0 of the plane
     */
    public Point GetQ0() {
        return q0;
    }

    /**
     * @param p
     * @return the normal vector
     */

    @Override
    public Vector getNormal(Point p) {
        /*return new Vector(p.add(normal));*/
        return normal;
    }

    @Override
    public Point getPositionPoint() {
        return q0;
    }

    /*************** normalize *****************/
    /**
     * @return the normal vector
     */

    public Vector getNormal() {
        return normal;
    }


    @Override
    public String toString() {
        return "Plane [q0=" + q0 + ", normal=" + normal + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Ray))
            return false;
        Plane other = (Plane) obj;
        return this.normal.equals(other.normal)&&this.q0.equals(other.q0);
    }


    /*************** intersections *****************/
    /**
     * @param ray
     * @return a list of GeoPoints- intersections of the ray with the plane, and this plane
     */



    public List<GeoPoint> findGeoIntersectionsParticular(Ray ray) {

        if (ray.getPoint().equals(q0) || isZero(this.normal.dotProduct(ray.getVector()))
                || isZero(this.normal.dotProduct(q0.subtract(ray.getPoint()))))
            return null;

        double t = (this.normal.dotProduct(q0.subtract(ray.getPoint()))) / (this.normal.dotProduct(ray.getVector()));
        if (t < 0) // In case there is no intersection with the plane return null
            return null;

        //In case there is intersection with the plane return the point
        GeoPoint p = new GeoPoint(this, ray.getPoint(t));
        LinkedList<GeoPoint> result = new LinkedList<GeoPoint>();
        result.add(p);
        return result;
    }

    @Override
    protected void findMinMaxParticular() {
        minX= Double.NEGATIVE_INFINITY;
        minY= Double.NEGATIVE_INFINITY;
        minZ= Double.NEGATIVE_INFINITY;

        maxX = Double.POSITIVE_INFINITY;
        maxY = Double.POSITIVE_INFINITY;
        maxZ =  Double.POSITIVE_INFINITY;
    }
}
