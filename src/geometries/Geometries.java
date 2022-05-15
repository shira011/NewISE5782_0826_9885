package geometries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

public class Geometries extends Intersectable {

    private List<Intersectable> geometries1;

    /**
     * Default constructor - Creates an empty list that will contains geometries
     * */
    public Geometries(){
        //we chosen in ArrayList because this class works better when the application demands storing the data and accessing it.
        geometries1 = new ArrayList<Intersectable>();
    }

	 /*public Geometries() {
	        geometries1 = new LinkedList<>();
	    }*/

    /**
     * Constructor that receives list of geometries and put them in new arrayList
     * @param geometries
     * */
    public Geometries(Intersectable... geometries){
        geometries1 =  new ArrayList<Intersectable>(Arrays.asList(geometries));
    }

    public void add(Intersectable... geometries){
        if (geometries != null){
            geometries1.addAll(Arrays.asList(geometries));
        }
    }


    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) throws IllegalArgumentException {
        List<GeoPoint> temp = new ArrayList<GeoPoint>();
        for (Intersectable intersectable : geometries1) {
            List<GeoPoint> intersection = intersectable.findGeoIntersections(ray);
            if (intersection != null)
                temp.addAll(intersection);
        }

        if (temp.isEmpty())
            return null;
        return temp;
    }

    public List<Intersectable> getIntsersectionPoints() {
        return geometries1;
    }

    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> result = null;
        for (Intersectable item : geometries1) {
            //get intersections points of a particular item from _intersectables
            List<GeoPoint> itemPoints = item.findGeoIntersections(ray);
            if (itemPoints != null) {
                //first time initialize result to new LinkedList
                if (result == null) {
                    result = new LinkedList<>();
                }
                //add all item points to the resulting list
                result.addAll(itemPoints);
            }
        }
        return result;
    }
}