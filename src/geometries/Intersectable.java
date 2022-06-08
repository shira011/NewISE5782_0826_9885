package geometries;


import primitives.Ray;

import java.util.List;

import primitives.Point;

public abstract class Intersectable{

    /**
     * A function that return all the intersection.הצטלבות point with geometry
     *
     * @param ray
     * @return List<Point>
     * @throws Exception
     * */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }


    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        @Override
        public String toString() {
            return "GeoPoint [geometry=" + geometry + ", point=" + point + "]";
        }
        /**
         * constructor for geo point
         *
         * @param geometry Geometry
         * @param point Point3D
         * */
        public GeoPoint(Geometry geometry,Point point)
        {
            this.geometry = geometry;
            this.point = point;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (!(obj instanceof GeoPoint)) return false;
            GeoPoint other = (GeoPoint)obj;
            return this.geometry== other.geometry && this.point.equals(other.point);
        }

    }
    public List<GeoPoint> findGeoIntersections (Ray ray){
        return findGeoIntersectionsHelper(ray);
    }
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);//{
    //	return null;
    //}

}

