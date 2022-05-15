package geometries;

import java.util.List;
import static primitives.Util.alignZero;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Sphere extends Geometry  {
    Point center;
    double radius;

    public Sphere(Point center1,double radius1) {
        center=center1;
        radius=radius1;
    }

	/*public Vector getNormal(Point p){
		Vector v=new Vector(0,0,0);
		v=this.center.subtract(p).normalize();
	return v;
	}*/

    @Override
    public Vector getNormal(Point point) throws IllegalArgumentException
    {
        return point.subtract(this.center).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) throws IllegalArgumentException {
        if (ray.getPoint1().equals(center)) // if the begin of the ray in the center, the point, is on the radius
            return List.of(new GeoPoint(this,ray.getPoint(radius)));
        //List<Point3D> rayPoints = new ArrayList<Point3D>();
        Vector u = center.subtract(ray.getPoint1());
        double tM = alignZero(ray.getDr().dotProduct(u));
        double d = alignZero(Math.sqrt(u.length()*u.length()- tM * tM));
        double tH = alignZero(Math.sqrt(radius*radius - d*d));
        double t1 = alignZero(tM+tH);
        double t2 = alignZero(tM-tH);


        if (d > radius)
            return null; // there are no instructions


        if (t1 <=0 && t2<=0)
            return null;

        if (t1 > 0 && t2 >0)
            return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this,ray.getPoint(t2)));
        if (t1 > 0)
        {
            return List.of(new GeoPoint(this,ray.getPoint(t1)));
        }

        else
            return List.of(new GeoPoint(this,ray.getPoint(t2)));
    }

	/*public List<GeoPoint> findGeoIntersections(Ray ray) throws IllegalArgumentException
	{

		if (ray.getPoint1().equals(center)) // if the begin of the ray in the center, the point, is on the radius
			return List.of(new GeoPoint(this,ray.getPoint(radius)));
		//List<Point3D> rayPoints = new ArrayList<Point3D>();
		Vector u = center.subtract(ray.getPoint1());
		double tM = alignZero(ray.getDr().dotProduct(u));
		double d = alignZero(Math.sqrt(u.length()*u.length()- tM * tM));
		double tH = alignZero(Math.sqrt(radius*radius - d*d));
		double t1 = alignZero(tM+tH);
		double t2 = alignZero(tM-tH);


		if (d > radius)
			return null; // there are no instructions

		if (t1 <=0 && t2<=0)
			return null;

		if (t1 > 0 && t2 >0)
			return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this,ray.getPoint(t2)));
		if (t1 > 0)
		{
			return List.of(new GeoPoint(this,ray.getPoint(t1)));
		}
		else
			return List.of(new GeoPoint(this,ray.getPoint(t2)));
	}*/

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
    {
        Point P0 = ray.getPoint1();
        Vector v = ray.getDr();

        if (P0.equals(center)) {
            GeoPoint gPoint=new GeoPoint(this,center.add(v.scale(radius)));
            return List.of(gPoint);
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
            GeoPoint P1 =new GeoPoint(this, P0.add(v.scale(t1)));
            GeoPoint P2 =new GeoPoint(this, P0.add(v.scale(t2)));
            return List.of(P1, P2);
        }

        if (t1 > 0) {
            GeoPoint P1 = new GeoPoint(this,P0.add(v.scale(t1)));
            return List.of(P1);
        }

        if (t2 > 0) {
            GeoPoint P2 = new GeoPoint(this, P0.add(v.scale(t2)));
            return List.of(P2);
        }

        return null;

    }


}