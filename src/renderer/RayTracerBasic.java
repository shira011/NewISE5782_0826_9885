package renderer;
import java.util.List;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {
    /**
     * @param sc
     * Ctor using super class constructor
     */
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    public RayTracerBasic(Scene sc) {
        super(sc);
    }

    /**
     * Implementation for the abstract method traceRay
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersectionsPoints = scene.geometries.findGeoIntersections(ray);
        if (intersectionsPoints == null)
            return scene.background;
        else
            return calcColor(ray.findClosestGeoPoint(intersectionsPoints), ray);
    }
    /**
     * @param rays List of surrounding rays
     * @return average color
     */
    protected Color traceRay(List<Ray> rays)
    {
        if(rays == null)
            return scene.background;
        Color color = Color.BLACK;
        Color bkg = scene.background;
        for (Ray ray : rays)
        {
//        	GeoPoint gp = findClosestIntersection(ray);
//        	color = color.add(gp == null ? bkg : calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, 1d));
            color = color.add(traceRay(ray));
        }
        color = color.add(scene.ambientLight.getIntensity());
        int size = rays.size();
        return color.reduce(size);

    }
    /**
     * Calculate the color of a certain point
     *
     * @param
     * @return The color of the point (calculated with local effects)
     * public Color calcColor(GeoPoint point, Ray ray) {
     *         return scene.ambientLight.getIntensity().add(point.geometry.getEmission()).add(calcLocalEffects(point, ray));
     *     }
     */

    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        //Color color = scene.ambientLight.getIntensity();
        Color color =intersection.geometry.getEmission();
        color=color.add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k, intersection.geometry.getNormal(intersection.point)));
    }
    private Color calcColor(GeoPoint geoPoint, Ray ray)
    {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, new Double3(DELTA)).add(scene.ambientLight.getIntensity());
    }
    /**
     * Function for calculating a point color
     *
     * @return Color
     * */

    /**
     * Calculate the effects of lights
     *
     * @param intersection
     * @param ray
     * @return The color resulted by local effecrs calculation
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray,Double3 k) {
        Vector v = ray.getVector();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;
        int nShininess = intersection.geometry.getMaterial().nShininess;

        Double3 kd = intersection.geometry.getMaterial().KD;
        Double3 ks = intersection.geometry.getMaterial().KS;
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // checks if nl == nv
                Double3 ktr = transparency(lightSource, l, n, intersection);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(lightIntensity.scale(((calcDiffusive(kd, nl)).add(calcSpecular(ks, l, n, v, nShininess)))));
                }
            }
        }
        return color;
    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k, Vector n) {
        if (level == 1 || k.lowerThan(MIN_CALC_COLOR_K))
        {
            return Color.BLACK;
        }
        Vector v = ray.getVector();
        Color color = Color.BLACK;
        Double3 kr = gp.geometry.getMaterial().KR;
        Double3 kkr = k.product(kr);

        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            Ray reflectedRay = constructReflectedRay( gp.point, ray, n);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            else
                color = color.add(scene.background.scale(kr));
        }
     ///glih

        Double3 kt = gp.geometry.getMaterial().KT;
        Double3 kkt = k.product(kt);

        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            Ray refractedRay = constructRefractedRay(gp.point, v, n);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);

            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            else
                color = color.add(scene.background.scale(kr));
        }
        return color;
    }
    /**
     * Calculates diffusive light
     * @param kd
     * @return The color of diffusive effects
     */
    private Double3 calcDiffusive(Double3 kd, double nl) {
        //double nl = alignZero(n.dotProduct(l));
        if(nl < 0)
            nl = (-1)*nl;
        return kd.scale(nl);
    }

    /**
     * Calculate specular light
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @return The color of specular reflection
     */
    private Double3 calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess) {
        //double twiceNL = alignZero(n.dotProduct(l)*2);
        //Vector r = l.subtract(n.scale(twiceNL)).normalized();
        double nl = Util.alignZero(n.dotProduct(l));
        Vector r = l.subtract(n.scale(2*nl)).normalize();
        double vr = Util.alignZero(v.dotProduct(r));
        if(vr>0)
            return new Double3(0,0,0);

        double vrPowed = Math.pow((-1)*vr,nShininess);
        return ks.scale(vrPowed);
    }
    private boolean unshaded(Vector l, Vector n, GeoPoint geopoint, LightSource ls) {

        double maxDistance= ls.getDistance(geopoint.point);

        Vector lightDirection = l.scale(-1); // from point to light source

        Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);

        Point point = geopoint.point.add(epsVector);

        Ray lightRay = new Ray(point, lightDirection);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null) {
            return true;
        }
        double lightDistance = ls.getDistance(geopoint.point);
        for (GeoPoint gp : intersections) {
            if(Util.alignZero(gp.point.distance(geopoint.point)-lightDistance)<=0 && gp.geometry.getMaterial().KT.equals(0))
                return false;
        }
        return true;
    }
    private Double3 transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {

        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(geoPoint.point, lightDirection, n); // refactored ray head move

        double lightDistance = light.getDistance(geoPoint.point);

        var intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null)
            return Double3.ONE;

        Double3 ktr =Double3.ONE ;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                ktr = gp.geometry.getMaterial().KT.product(ktr);
                if (ktr.lowerThan(MIN_CALC_COLOR_K))
                    return Double3.ZERO;}
        }return ktr;
    }
    /**

     * Constructs a refracted-ray to a former-ray which ends on the given point and it's direction is the given direction
     * @param point the former-ray's end point - and the refracted-ray's starting point
     * @param v the former-ray's direction - and the refracted-ray's direction
     * @param n the normal to the geometry on which the point lays ( - the end of the former-ray)
     * @return the constructed refracted-ray
     */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    /**
     * Finds the closest intersection-geoPoint to the starting-point of the given ray.
     * @param ray the given constructed-ray
     * @return The closest intersection-geoPoint to the ray's starting-point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(ray);
        return intersections == null ? null : ray.findClosestGeoPoint(intersections);
    }
    /**
     * Constructs a reflected ray to a former-ray which ends on the given point and it's direction is the given direction
     * @param point the former-ray's end point - and the reflected-ray's starting point
     * @param  //the former-ray's direction
     * @param n the normal to the geometry on which the point lays ( - the end of the former-ray)
     * @return the constructed reflected-ray
     */
    private Ray constructReflectedRay(Point point, Ray  ray, Vector n) {
        Vector v = ray.getVector();
        double vn = Util.alignZero(v.dotProduct(n));
        // if v.dotProduct(n)==0 then the ray we construct is tangent to the geometry:
        if (Util.isZero(vn))
            return null;
        Vector dir = v.subtract(n.scale(2*vn));
        return new Ray(point, dir,n);
    }
}