package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Sphere extends Geometry {
    Point center;
    double radius;


    /*************** ctor *****************/
    /**
     * ctor that gets 2 parameters
     * @param center
     * @param radius
     */
    public Sphere(Point center, double radius) {
        super();
        if(isZero(radius) || radius < 0)
            throw new IllegalArgumentException("Zero or negative radius");
        this.center = center;
        this.radius = radius;
    }

    /*************** normalize *****************/
    /**
     * @return the normal
     */
    @Override
    public Vector getNormal(Point p)
    {
        Vector N = p.subtract(center);
        return N.normalize();//the normal to sphere is the subtraction of the given point from the center. we get the normal vector
    }

    @Override
    public Point getPositionPoint() {
        return center;
    }
    /*************** gets *****************/
    /**
     * @return the center
     */
    public Point getCenter() {
        return center;
    }

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /*************** admin *****************/
    @Override
    public String toString() {
        return "Sphere [center=" + center + ", radius=" + radius + "]";
    }

    /*************** intersections *****************/
    /**
     * @param ray
     * @return a list of GeoPoints- intersections of the ray with the sphere, and this sphere
     */
 /*   @Override
    public List<Point> findIntersections(Ray ray)
    {
        Point P0 = ray.getPoint();
        Vector v = ray.getVector();

        if (P0.equals(center)) {
            return List.of(center.add(v.scale(radius)));
        }

        Vector u = center.subtract(P0);
        double tm = alignZero(v.dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));

        // no intersections : the ray direction is above the sphere
        if (d >= radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 > 0 && t2 > 0) {
            Point P1 = P0.add(v.scale(t1));
            Point P2 = P0.add(v.scale(t2));
            return List.of(P1, P2);
        }

        if (t1 > 0) {
            Point P1 = P0.add(v.scale(t1));
            return List.of(P1);
        }

        if (t2 > 0) {
            Point P2 = P0.add(v.scale(t2));
            return List.of(P2);
        }

        return null;

    }*/
    @Override
    public List<GeoPoint> findGeoIntersectionsParticular(Ray ray)
    {
        double r = this.radius;

        // Special case: if point p0 == center, that mean that all we need to calculate
        // is the radios mult scalar with the direction, and add p0
        if (center.equals(ray.getPoint())) {
            LinkedList<GeoPoint> result = new LinkedList<GeoPoint>();
            result.add(new GeoPoint(this, ray.getPoint(r)));
            return result;
        }

        Vector u = center.subtract(ray.getPoint());
        double tm = u.dotProduct(ray.getVector());
        double d = Math.sqrt(alignZero(u.lengthSquared() - tm * tm));

        if (d >= r) //also In case the cut is tangent to the object still return null - d = r
            return null;

        double th = Math.sqrt(r * r - d * d);
        double t1 = tm + th;
        double t2 = tm - th;

        if(alignZero(t1) > 0 || alignZero(t2) > 0){
            LinkedList<GeoPoint> result = new LinkedList<GeoPoint>();
            if(alignZero(t1) > 0){
                Point p1 = ray.getPoint(t1);
                result.add(new GeoPoint(this, p1));
            }
            if(alignZero(t2) > 0){
                Point p2 = ray.getPoint(t2);
                result.add(new GeoPoint(this, p2));
            }
            return result;
        }
        else { //In case there are no intersections points
            return null;
        }

    }


    @Override
    protected void findMinMaxParticular() {
        minX = center.getD1() - radius;
        maxX = center.getD1() + radius;
        minY = center.getD2() - radius;
        maxY = center.getD2() + radius;
        minZ = center.getD3() - radius;
        maxZ = center.getD3() + radius;
    }
}