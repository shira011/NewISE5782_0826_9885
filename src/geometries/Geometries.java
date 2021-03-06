package geometries;


import primitives.Point;
import primitives.Ray;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries extends Borderable {

    List<Borderable> geometriesInScene;

    public Geometries() {
        geometriesInScene = new LinkedList<>();
    }

    public Geometries(Borderable... intersectables) {
        geometriesInScene = List.of(intersectables);
    }

    public void add(Borderable... intersectables) {
        geometriesInScene.addAll(List.of(intersectables));
    }

    /**
     * find all  geo point with the ray
     */
    public List<GeoPoint> findGeoIntersectionsParticular(Ray ray) {
        if (geometriesInScene.isEmpty()) // In case the collection is empty
            return null;

        List<GeoPoint> points = null, result;
        for (Intersectable body: geometriesInScene) {
            result = body.findGeoIntersectionsHelper(ray);
            if(result != null){
                if(points == null)
                    points = new LinkedList<GeoPoint>(result);
                else
                    points.addAll(result);
            }
        }
        return points;
    }

    @Override
    protected void findMinMaxParticular() {
        minX = Double.POSITIVE_INFINITY;
        maxX = Double.NEGATIVE_INFINITY;
        minY = Double.POSITIVE_INFINITY;
        maxY = Double.NEGATIVE_INFINITY;
        minZ = Double.POSITIVE_INFINITY;
        maxZ = Double.NEGATIVE_INFINITY;

        /**
         * find the minimum and the maximum of the geometry border
         */

        for (Borderable g : geometriesInScene) {
            g.findMinMax();

            //calc min
            if (g.minX < minX)
                minX = g.minX;
            if (g.minY < minY)
                minY = g.minY;
            if (g.minZ < minZ)
                minZ = g.minZ;

            //calc max
            if (g.maxX > maxX)
                maxX = g.maxX;
            if (g.maxY > maxY)
                maxY = g.maxY;
            if (g.maxZ > maxZ)
                maxZ = g.maxZ;
        }
    }
}





























/*package geometries;

import java.util.List;

import primitives.*;
import java.util.ArrayList;

/**
 * interface of composite- contains a list of geometries that are intersectable.
 * (and not implements all "Geometry interface" because we cannot define a normal to the composite object- it contains a few objects.
 * but it does implement "Intersectable interface" because we want to check the intersections with all the geometries we have.
 * @author efrat & esti
 */
/*public class Geometries extends Intersectable
{
    private List<Intersectable> geometries; //list of geometries- all implement intersactable interface

    /*************** ctors *****************/
/**
 * default ctor- restart new empty arrayList of geometries
 */
 /*   public Geometries()
    {
        super();
        geometries=new ArrayList<Intersectable>();//we chose arrayList because its faster to get into the items-
        //it takes O(1) and we need to pass on the list of geometries as fast as we can when we get the intersections with them all, for all the rays!
        //its more important than adding geometries to the list or deleting, which take O(n) here in arrayList.
    }

    /**
     * copy-ctor. copies the given array of geometries.
     */
 /*   public Geometries(Intersectable...mygeometries)
    {
        geometries=new ArrayList<Intersectable>();
        for(int i=0;i<mygeometries.length;i++)
            geometries.add(mygeometries[i]);
    }

    /*************** add *****************/
/**
 * function that adds new geometries to the list
 */
 /*   public void add(Intersectable...mygeometries)
    {
        for(int i=0;i<mygeometries.length;i++)
            geometries.add(mygeometries[i]);
    }

    /**
     * @param ray
     * @return a list of intersections of the ray with all the geometries in the list. all the composite component.
     * we are using the design pattern of composite- here in one function we collect all the intersections of our geometry shapes by using their own "findInresection" function.
     */
/**
 * @param ray
 * @return a list of intersections of the ray with all the geometries in the list. all the composite component.
 * we are using the design pattern of composite- here in one function we collect all the intersections of our geometry shapes by using their own "findInresection" function.
 */
  /*  @Override
    public List<Point> findIntersections(Ray ray)
    {
        List<Point> intersections=new ArrayList<Point>();//new empty list of points and geometries
        for(int i=0; i<geometries.size(); i++) 				 //move on all the geometries
        {
            if(geometries.get(i).findIntersections(ray)!=null) //if there are intersections to the ray with the specific shape
            {
                for(int j=0; j<geometries.get(i).findIntersections(ray).size(); j++) //move on all the intersections point with this shape
                {
                    intersections.add(geometries.get(i).findIntersections(ray).get(j));//add them to general list of intersections
                }
            }
        }
        if(intersections.size()==0)
            return null;//No intersection at all
        return intersections;//return all the intersections
    }
}*/