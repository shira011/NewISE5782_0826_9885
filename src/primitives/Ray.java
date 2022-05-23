package primitives;

import static primitives.Util.isZero;
//import static renderer.RayTracerBasic.DELTA;

import geometries.Intersectable;
import geometries.Intersectable.*;
import  primitives.Vector;

import java.util.List;
import java.util.Objects;

public class Ray {
    Point p;
    Vector v;
    private static final double DELTA = 0.1;
    /*************** ctor *****************/
    /**
     * ctor that gets 2 parameteres
     * @param p2
     * @param v2
     */
    public Ray(Vector v2, Point p2) {
        super();
        p = p2;
        v = v2.normalize();
    }
    public Ray( Point p2,Vector v2) {
        super();
        p = p2;
        v = v2.normalize();
    }

    public Ray(Point head, Vector lightDirection, Vector n)
    {
        if(primitives.Util.alignZero(lightDirection.dotProduct(n)) < 0)
            p= head.add(n.scale(-DELTA));
        else if(primitives.Util.alignZero(lightDirection.dotProduct(n)) > 0)
            p= head.add(n.scale(DELTA));
        else if(primitives.Util.isZero(lightDirection.dotProduct(n)))
            p=head;
        v=lightDirection;
        v.normalize();
    }

    public Point getPoint(){
        return  this.p;}
    public Vector getVector(){return  this.v;}
    /**
     * this function gets a list of (intersection) points,
     * and returns the closest point from them to p0-beginning of this ray.
     * @param points
     * @return closestP
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new Intersectable.GeoPoint(null, p)).toList()).point;
    }

    /**
     *
     * @param geoPoints
     * @return The closest point to the began of the ray
     */
    public Intersectable.GeoPoint findClosestGeoPoint(List<Intersectable.GeoPoint> geoPoints) {

        if (geoPoints == null) //In case of an empty list
            return null;
        Intersectable.GeoPoint closePoint = geoPoints.get(0); //Save the first point in the list
        for (Intersectable.GeoPoint p : geoPoints) {
            if (closePoint.point.distance(this.p) > p.point.distance(this.p)) //In case the distance of closes point is bigger than the p point
                closePoint = p;
        }
        return closePoint;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray other = (Ray)obj;
        return this.v.equals(other.v) && this.p.equals(other.p);
    }

    public Point getPoint(double t)
    {
        if(isZero(t)){
            throw new IllegalArgumentException("t is equal to 0 produce an illegal ZERO vector");
        }
        return p.add(v.scale(t));
        //Point tmp=new Point(p.dPoint.d1 ,p.dPoint.d2,p.dPoint.d3);
        //return isZero(t) ? p : tmp.add(v.scale(t));//takes the beginning of the ray and adds the vector*scalar point that we get.
    }

    @Override
    public String toString()
    {
        return "Ray [Point=" + p + ", Vector=" +v + "]";
    }
    @Override
    public int hashCode() {
        return Objects.hash(p, v);
    }
}
