package geometries;
import primitives.Util;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends Geometry {

    Ray axisRay;
    double radius;
    public Tube(Ray axisRay1,double radius1) {
        axisRay=axisRay1;
        radius=radius1;
    }

    public Vector getNormal(Point p){
        double t=axisRay.getDr().dotProduct(p.subtract(axisRay.getPoint1()));
        Point point =axisRay.getPoint1().add(axisRay.getDr().scale(t));
        Vector myVec=p.subtract(point);
        return myVec.normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Vector vAxis = axisRay.getDr();
        Vector v = ray.getDr();
        Point p0 = ray.getPoint1();

        // At^2+Bt+C=0
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = primitives.Util.alignZero(v.dotProduct(vAxis));
        Vector vVaVa;
        Vector vMinusVVaVa;
        if (vVa == 0) // the ray is orthogonal to the axis
            vMinusVVaVa = v;
        else {
            vVaVa = vAxis.scale(vVa);
            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) { // the rays is parallel to axis
                return null;
            }
        }
        // A = (v-(v*va)*va)^2
        a = vMinusVVaVa.lengthSquared();

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(axisRay.getPoint1());
        } catch (IllegalArgumentException e1) { // the ray begins at axis P0
            if (vVa == 0) // the ray is orthogonal to Axis
                return List.of(new GeoPoint(this,ray.getPoint(radius)));

            double t = primitives.Util.alignZero(Math.sqrt(radius * radius / vMinusVVaVa.lengthSquared()));
            return t == 0 ? null : List.of(new GeoPoint(this,ray.getPoint(t)));
        }

        double dPVAxis = primitives.Util.alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0)
            dPMinusdPVaVa = deltaP;
        else {
            dPVaVa = vAxis.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                double t = primitives.Util.alignZero(Math.sqrt(radius * radius / a));
                return t == 0 ? null : List.of(new GeoPoint(this,ray.getPoint(t)));
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * primitives.Util.alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - radius * radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = primitives.Util.alignZero(b * b - 4 * a * c);
        if (discr <= 0) return null; // the ray is outside or tangent to the tube

        double doubleA = 2 * a;
        double tm = primitives.Util.alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (primitives.Util.isZero(th)) return null; // the ray is tangent to the tube

        double t1 = primitives.Util.alignZero(tm + th);
        if (t1 <= 0) // t1 is behind the head
            return null; // since th must be positive (sqrt), t2 must be non-positive as t1

        double t2 = primitives.Util.alignZero(tm - th);

        // if both t1 and t2 are positive
        if (t2 > 0)
            return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this, ray.getPoint(t2)));
        else // t2 is behind the head
            return List.of(new GeoPoint(this,ray.getPoint(t1)));
    }

}