package geometries;

import primitives.Util;
import primitives.Vector;
import primitives.Point;
import primitives.Ray;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Tube extends Geometry {
    protected Ray axisRay;
    protected double radius;
/************** ctor *****************/
    /**
     * ctor that gets 2 parameters
     * @param axisRay
     * @param radius
     */
    public Tube(Ray axisRay, double radius) {
        super();
        if(Util.isZero(radius) || radius < 0)
            throw new IllegalArgumentException("Zero or negative radius");
        this.axisRay = axisRay;
        this.radius = radius;
    }
    /*************** gets *****************/
    /**
     * @return the axisray
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }
    /**
     * @param p
     * @return the normal
     */

    /*************** admin *****************/
    @Override
    public Vector getNormal(Point p) {
        Vector v = p.subtract(axisRay.getPoint());
        double t = alignZero(axisRay.getVector().dotProduct(v));
        Point o = axisRay.getPoint().add(axisRay.getVector().scale(t));
        Vector N = p.subtract(o);
        return N.normalize();
    }

    @Override
    public String toString() {
        return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
    }
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return  null;
        /*Vector vAxis = axisRay.getVector();
        Vector v = ray.getVector();
        Point p0 = ray.getPoint();

        // At^2+Bt+C=0
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = alignZero(v.dotProduct(vAxis));
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
            deltaP = p0.subtract(axisRay.getPoint());
        } catch (IllegalArgumentException e1) { // the ray begins at axis P0
            if (vVa == 0) // the ray is orthogonal to Axis
                return List.of(new GeoPoint(this,ray.getPoint(radius)));

            double t = alignZero(Math.sqrt(radius * radius / vMinusVVaVa.lengthSquared()));
            return t == 0 ? null : List.of(new GeoPoint(this,ray.getPoint(t)));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0)
            dPMinusdPVaVa = deltaP;
        else {
            dPVaVa = vAxis.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                double t = alignZero(Math.sqrt(radius * radius / a));
                return t == 0 ? null : List.of(new GeoPoint(this,ray.getPoint(t)));
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - radius * radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);
        if (discr <= 0) return null; // the ray is outside or tangent to the tube

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (isZero(th)) return null; // the ray is tangent to the tube

        double t1 = alignZero(tm + th);
        if (t1 <= 0) // t1 is behind the head
            return null; // since th must be positive (sqrt), t2 must be non-positive as t1

        double t2 = alignZero(tm - th);

        // if both t1 and t2 are positive
        if (t2 > 0)
            return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this, ray.getPoint(t2)));
        else // t2 is behind the head
            return List.of(new GeoPoint(this,ray.getPoint(t1)));*/
    }
}
