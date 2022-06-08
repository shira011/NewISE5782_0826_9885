package renderer;
import java.util.List;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;//a constant for hazaza of the beginning of light rays from object- for shadows
    private static final int MAX_CALC_COLOR_LEVEL = 10;//stop conditions for the recursia of reflection and transparency:
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * @param sc
     * Ctor using super class constructor
     */
    public RayTracerBasic(Scene sc) {
        super(sc);
    }

    /**
     * Implementation for the abstract method traceRay
     * Function that calculates the color for the nearest intersection point,
     * 	if no intersection points are returned the color of the background
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
     *@return the color of the pixel that the rays pass through it- the average color calculated by the intersection points of the rays.
     */
    protected Color traceRay(List<Ray> rays)
    {
        if(rays == null)
            return scene.background;
        Color color = Color.BLACK;
        Color bkg = scene.background;
        for (Ray ray : rays)
        {
            color = color.add(traceRay(ray));
        }
        color = color.add(scene.ambientLight.getIntensity());
        int size = rays.size();
        return color.reduce(size);////reduce by the count of rays- to find the average color

    }
    /**
     * * calculates the color of the point that the ray intersect it
     * 	 * (we already get here the closest intersection point)
     * 	 * @param point
     * 	 * @param the ray
     * 	 * @param level of recursion- goes down each time till it gets to 1
     * 	 * @param k- mekadem of reflection and refraction so far
     * 	 * @return the color צבע זוהר
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        //in the recursive calls to calcColor we might get that intersections=null.
        //if so- return black. no adding color and light.
        Color color =intersection.geometry.getEmission();
        color=color.add(calcLocalEffects(intersection, ray, k));//diffuse, specular, and shadow.
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k, intersection.geometry.getNormal(intersection.point)));//reflection and refraction- may cause recursion
    }
    /**
     * outer calcColor that adds ambient light, and call the inner calcColor
     * sending level of recursion, and initial mekadem k=1.
     * @param geoPoint
     * @param ray
     * @return
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray)
    {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, new Double3(DELTA)).add(scene.ambientLight.getIntensity());//here we add the ambient light- once, without the recursion.
    }
    /**
     * Function for calculating a point color
     *
     * @return Color
     * */

    /**
     * Adding diffusion/specular calculation, and check shadow
     * 	 * this function calculates the local light effects in phonge model
     * 	 * @param intersection
     * 	 * @param ray
     * 	 * 	 * @param level of recursion- goes down each time till it gets to 1
     * 	 * @param k- mekadem of reflection and refraction so far
     * 	 * @return the color that was calculated
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray,Double3 k) {
        Vector v = ray.getVector();//ממקום האור לנקודה המוארת
        Vector n = intersection.geometry.getNormal(intersection.point);// כיוון האור
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;
        int nShininess = intersection.geometry.getMaterial().nShininess;

        Double3 kd = intersection.geometry.getMaterial().KD;//מקדם של הדיפיוז כמה אחוז של התפזרות האור
        Double3 ks = intersection.geometry.getMaterial().KS;//מקדם של כמה אחוז מהאנרגיה הולך להשתקפות ישירה
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {//
            Vector l = lightSource.getL(intersection.point);//עוצמת מקור התאורה
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {//it means if there is transparency and its not "atum"-
                //take it in account for calculating how much shadow.
                Double3 ktr = transparency(lightSource, l, n, intersection);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(lightIntensity.scale(((calcDiffusive(kd, nl)).add(calcSpecular(ks, l, n, v, nShininess)))));
                }
            }
        }
        return color;
    }

    /**
     * calc the global effects- relection and refraction.
     * this func call "calcColor" in recursion to add more and more global effects.
     * @param gp
     * @param ray
     * @param level of recursion- goes down each time till it gets to 1
     * @param k- mekadem of reflection and refraction so far
     * @return
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k, Vector n) {
        if (level == 1 || k.lowerThan(MIN_CALC_COLOR_K))////stop recursion when it gets to the min limit
        {
            return Color.BLACK;
        }
        Vector v = ray.getVector();
        Color color = Color.BLACK;
        Double3 kr = gp.geometry.getMaterial().KR;
        Double3 kkr = k.product(kr);

        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {//stop recursion when it gets to the min limit
            Ray reflectedRay = constructReflectedRay( gp.point, ray, n);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            else
                color = color.add(scene.background.scale(kr));
        }
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
     * Calculate Diffusive component of light reflection.
     * 	 *
     * 	 * @param kd diffusive component coef
     * 	 * @param l          direction from light to point
     * 	 * @param n          normal to surface at the point
     * 	 * @param ip light intensity at the point
     * 	 * @return diffusive component of light reflection
     * 	 * The diffuse component is that dot product n•L that we discussed in class. It approximates light, originally
     * 	 * from light source L, reflecting from a surface which is diffuse, or non-glossy. One example of a non-glossy
     * 	 * surface is paper. In general, you'll also want this to have a non-gray color value, so this term would in general
     * 	 * be a color defined as: [rd,gd,bd](n•L)
     * @param kd
     * @return The color of diffusive effects
     */
    private Double3 calcDiffusive(Double3 kd, double nl) {
        if(nl < 0)
            nl = (-1)*nl;
        return kd.scale(nl);
    }

    /**
     * * @param ks         specular component coef
     * 	 * @param l          direction from light to point
     * 	 * @param n          normal to surface at the point
     * 	 * @param v          direction from point of view to point
     * 	 * @param nShininess shininess level
     * 	 * @param ip         light intensity at the point
     * 	 * @return specular component light effect at the point
     * 	 * Finally, the Phong model has a provision for a highlight, or specular, component, which reflects light in a
     * 	 * shiny way. This is defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection direction vector we discussed
     * 	 * in class (and also used for ray tracing), and where p is a specular power. The higher the value of p, the shinier
     * 	 * the surface.
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @return The color of specular reflection
     */
    private Double3 calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess) {
        double nl = Util.alignZero(n.dotProduct(l));
        Vector r = l.subtract(n.scale(2*nl)).normalize();
        double vr = Util.alignZero(v.dotProduct(r));
        if(vr>0)
            return new Double3(0,0,0);

        double vrPowed = Math.pow((-1)*vr,nShininess);
        return ks.scale(vrPowed);
    }
    /**
     * בודקים אם הקרן נתקעת בעצם אז היא בצל
     * search for shadow shape
     * @param l from light source to point
     * @param n the normal
     * @param geopoint the point
     * @return if the point is unshaded. it means- if there is no shade on it- it should have light.
     */
    private boolean unshaded(Vector l, Vector n, GeoPoint geopoint, LightSource ls) {

        double maxDistance= ls.getDistance(geopoint.point);////the distance of the point of geoPoint from the light source

        Vector lightDirection = l.scale(-1); // from point to light source

        Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);

        Point point = geopoint.point.add(epsVector);//we add it to the point by the normal direction,
        //in order to avoid case that the point is inside the object and we think the object itself makes a shadow on the point.
        //so we move the point up a little bit.
        Ray lightRay = new Ray(point, lightDirection);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null) {//if there are no intersections with the light ray
            return true;//the point is unshaded- no one makes shadow on the point
        }
        double lightDistance = ls.getDistance(geopoint.point);
        for (GeoPoint gp : intersections) {
            if(Util.alignZero(gp.point.distance(geopoint.point)-lightDistance)<=0 && gp.geometry.getMaterial().KT.equals(0))
                //if there is an intersection closer to beginning of ray than our intersection
                //of "geopoint" that we got- return false. it means, there is something shadowing our intersection of "geopoint".
                //only if the material of the geo is "atum"- it makes shadow
                return false;
        }
        return true;
    }
    /**
     * this function is like unshaded but returns how much shading
     * מחשבת שקיפות
     * @param light
     * @param l
     * @param n
     * @param geoPoint
     * @return the transparency
     */
    private Double3 transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {

        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(geoPoint.point, lightDirection, n); // refactored ray head move

        double lightDistance = light.getDistance(geoPoint.point);

        var intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null)
            return Double3.ONE;

        Double3 ktr =Double3.ONE ;
        for (GeoPoint gp : intersections) {//move on all the geometries in the way
            //if there are geometr between the point to the light- calculate the transparency
            //in order to know how much shadow there is on the point
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                ktr = gp.geometry.getMaterial().KT.product(ktr);//add this transparency to ktr
                if (ktr.lowerThan(MIN_CALC_COLOR_K))//stop the loop- shadow "atum", black. very small transparency
                    return Double3.ZERO;}
        }return ktr;
    }
    /**
     * Constructs a refracted-ray to a former-ray which ends on the given point and it's direction is the given direction
     * בונה קרן נשברת לקרן קודמת שמסתיימת בנקודה הנתונה והכיוון שלה הוא הכיוון הנתון
     * @param point the former-ray's end point - and the refracted-ray's starting point
     * @param v the former-ray's direction - and the refracted-ray's direction
     * @param n the normal to the geometry on which the point lays ( - the end of the former-ray)
     * @return the constructed refracted-ray לשבור
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
        if (Util.isZero(vn))
            return null;
        Vector dir = v.subtract(n.scale(2*vn));
        return new Ray(point, dir,n);
    }
}