package geometries;

import java.util.List;
import primitives.Vector;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import primitives.*;

public class Triangle extends Polygon{

    public Triangle(Point p1,Point p2,Point p3) throws IllegalArgumentException {super(p1,p2,p3);}

	/*public Triangle(Point p1,Point p2,Point p3) throw exaption  {
		if(p1.equals(p3))
			throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
		plane = new Plane(p1, p2, p3);
		}*/


    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) //throws IllegalArgumentException
    {
        List<GeoPoint> rayPoints = plane.findGeoIntersections(ray);
        if (rayPoints == null)
            return null;
        for (GeoPoint geoPoint : rayPoints)
        {
            geoPoint.geometry = this;
        }

        Vector v1 = vertices.get(0).subtract(ray.getPoint1());
        Vector v2 = vertices.get(1).subtract(ray.getPoint1());
        Vector v3 = vertices.get(2).subtract(ray.getPoint1());

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();



        if (alignZero(n1.dotProduct(ray.getDr())) > 0 && alignZero(n2.dotProduct(ray.getDr())) > 0 && alignZero(n3.dotProduct(ray.getDr())) > 0)
        {
            return rayPoints;
        }
        else if (alignZero(n1.dotProduct(ray.getDr())) < 0 && alignZero(n2.dotProduct(ray.getDr())) < 0 && alignZero(n3.dotProduct(ray.getDr())) < 0)
        {
            return rayPoints;
        }
        if (isZero(n1.dotProduct(ray.getDr())) || isZero(n2.dotProduct(ray.getDr())) || isZero(n3.dotProduct(ray.getDr())))
            return null; //there is no instruction point
        return null;

    }

}