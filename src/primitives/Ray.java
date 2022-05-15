package primitives;

import geometries.Intersectable.GeoPoint;
import java.util.List;

import geometries.Intersectable.GeoPoint;

public class Ray {

    Point p0;
    Vector dr;

    private static final double DELTA = 0.1;

    public Ray(Point p,Vector dr1) {
        p0=p;
        dr=dr1;
    }

	/*public Ray(Point p0, Vector dir)
    {
        if(!(dir.length() == 1))
            this.dr = dir.normalize();
        else this.dr = dir;
        this.p0 = p0;
    }*/

    public Ray(Point head, Vector lightDirection, Vector n)
    {
        if(primitives.Util.alignZero(lightDirection.dotProduct(n)) < 0)
            p0= head.add(n.scale(-DELTA));
        else if(primitives.Util.alignZero(lightDirection.dotProduct(n)) > 0)
            p0= head.add(n.scale(DELTA));
        else if(primitives.Util.isZero(lightDirection.dotProduct(n)))
            p0=head;
        dr=lightDirection;
        dr.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point)) return false;
        Ray other = (Ray)obj;
        return p0.equals(other.p0)&&dr.equals(other.dr);
    }
    public String toString() {
        return p0.toString()+" "+dr.toString();
    }
    public Point getPoint1(){
        return p0;
    }
    public Vector getDr() {
        return dr.normalize();
    }
    public Point getPoint(double t) throws IllegalArgumentException{
        return p0.add(dr.scale(t));
    }
    /**
     * The function returns the point closest to the beginning of the beam
     * from all the intersection points of the resulting list.
     *
     * @param points List<Point> value
     * @return Point3D value
     * */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : getClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    public GeoPoint getClosestGeoPoint(List<GeoPoint> intersections){

        if(intersections == null)
            return null;
        GeoPoint closet = intersections.get(0);
        for (GeoPoint geoPoint : intersections)
        {
            if(geoPoint.point.distance(p0) < closet.point.distance(p0))
                closet= geoPoint;

        }
        return closet;
    }
}