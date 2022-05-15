package geometries;

import java.util.List;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Plane extends Geometry {
    Point p0;
    Vector normal;
    public Plane(Point p1,Vector normal1){
        p0=p1;
        normal=normal1;
    }
    public Plane(Point p1,Point p2,Point p3){
        p0=p1;
        Vector vec1=(p2.subtract(p1));
        Vector vec2=(p3.subtract(p1));
        normal = vec1.crossProduct(vec2).normalize();//According to the formula
    }
    public Vector getNormal(Point p){
        return normal;}

    public Point getPoint(){
        return p0;
    }

    public Vector getNormal(){
        return normal;}
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) throws IllegalArgumentException {
        double nv = normal.dotProduct(ray.getDr());
        if (isZero(nv))
        {
            return null;
        }

        try
        {
            Vector pSubtractP0 = p0.subtract(ray.getPoint1());
            double t = alignZero((normal.dotProduct(pSubtractP0))/nv);

            if(t <= 0)
            {
                return null;
            }
            return List.of(new GeoPoint(this,ray.getPoint(t)));
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point P0 = ray.getPoint1();
        Vector v = ray.getDr();

        Vector n = normal;

        if (p0.equals(P0)) {
            return null;
        }

        Vector P0_Q0 = p0.subtract(P0);

        //numerator
        double nP0Q0 = alignZero(n.dotProduct(P0_Q0));

        //
        if (isZero(nP0Q0)) {
            return null;
        }

        //denominator
        double nv = alignZero(n.dotProduct(v));

        // ray is lying in the plane axis
        if (isZero(nv)) {
            return null;
        }

        double t = alignZero(nP0Q0 / nv);

        if (t <= 0) {
            return null;
        }


        Point point = ray.getPoint(t);
        GeoPoint gPoint = new GeoPoint(this,point);

        return List.of(gPoint);
    }

}