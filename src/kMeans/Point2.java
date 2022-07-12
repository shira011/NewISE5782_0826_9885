package kMeans;
import java.util.Random;

import geometries.*;
import primitives.*;
public class Point2 {
    private Geometry geometry;
    private Point PositionPoint;
    private int clusterNumber = 0;

    public Point2(Geometry g)
    {
        this.geometry=g;
        this.PositionPoint=g.getPositionPoint();
    }
    /**
     * @return the PositionPoint
     */
    public Point getPositionPoint() {
        return PositionPoint;
    }
    /**
     * @return the geometry
     */
    public Geometry getGeometry() {
        return geometry;
    }
    /**
     * @param geometry
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
    /**
     *
     */
    public void setPositionPoint(Point positionPoint) {
        PositionPoint = positionPoint;
    }
    /**
     * @return the clusterNumber
     */
    public int getClusterNumber() {
        return clusterNumber;
    }
    /**
     *
     */
    public void setClusterNumber(int cluster_number) {
        this.clusterNumber = cluster_number;
    }
    //Distance between two position_points
    protected static double distance(Point2 p, Point2 centroid) {
        return p.getPositionPoint().distanceSquared(centroid.getPositionPoint());
    }
    //Creates random point
    protected static Point2 createRandomPoint() {
        return Point2.createRandomPoint(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
    }
    protected static Point2 createRandomPoint(double min, double max) {
        Random r = new Random();
        double x = min + (max - min) * r.nextDouble();
        double y = min + (max - min) * r.nextDouble();
        double z = min + (max - min) * r.nextDouble();
        return new Point2(new Sphere(new Point(x,y,z),1));
    }
}
