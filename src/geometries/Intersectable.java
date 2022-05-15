package geometries;

import java.util.List;
import java.util.stream.Collectors;


import primitives.*;

public abstract class Intersectable {

    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }



    public List<GeoPoint> findGeoIntersections (Ray ray){
        return findGeoIntersectionsHelper(ray);
    }
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);


    public static class GeoPoint
    {
        public Geometry geometry;
        public Point point;

        //vvbmphnmt4h4

        public GeoPoint(Geometry geometry,Point point)
        {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj) return true;
            if (obj == null) return false;
            if (!(obj instanceof GeoPoint)) return false;
            GeoPoint other = (GeoPoint)obj;
            return this.geometry.equals(other.geometry) && this.point.equals(other.point);
        }
        @Override
        public String toString() {
            return "GP{" + "G=" + geometry + ", P=" + point + '}';
        }

    }
}