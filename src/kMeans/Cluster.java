package kMeans;

import primitives.Point;

import java.util.ArrayList;
import java.util.List;

public class Cluster
{

    public List<Point2> points;
    public Point2 centroid;
    public int id;

    //Creates a new Cluster
    public Cluster(int id)
    {
        this.id = id;
        this.points = new ArrayList<Point2>();
        this.centroid = null;
    }

    public List<Point2> getPoints()
    {
        return points;
    }

    public void addPoint(List<Point2> points)
    {
        for(Point2 p :points)
        {
            points.add(p);
        }
    }
    public void addPoint(Point2 p)
    {
        points.add(p);
    }

    public void setPoints(List<Point2> points)
    {
        this.points = points;
    }

    public Point2 getCentroid()
    {
        return centroid;
    }

    public void setCentroid(Point2 centroid)
    {
        this.centroid = centroid;
    }

    public int getId()
    {
        return id;

    }

    public void clear()
    {
        points.clear();
    }

}
