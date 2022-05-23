package primitives;

import java.util.Objects;

import static primitives.Util.isZero;
//point class
//fuhh
public class Point {


    public static final Point ZERO =  new Point(0,0,0);
    public final Double3 dPoint;
    public Point(double d1,double d2,double d3)
    {
        dPoint = new Double3(d1,d2,d3);
    }
    public Point(Double3 d)
    {
        dPoint = d;
    }

    /**
     * @return d1
     */
    public double getD1()
    {
        return dPoint.d1;
    }

    /**
     * @return d2
     */
    public double getD2()
    {
        return dPoint.d2;
    }

    /**
     * @return d3
     */
    public double getD3()
    {
        return dPoint.d3;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if(!(obj instanceof Point))
            return false;
        Point p = (Point) obj;
        //if (this.dpoint.equals(dpoint.ZERO))
        //    return false;
        return this.dPoint.equals((p.dPoint));
    }
    @Override
    public int hashCode() {
        return Objects.hash(dPoint);
    }
    @Override
    public String toString(){
        return String.format("Point: "+ dPoint.toString());
    }
    //substrucing
    public Vector subtract(Point p2){
        Vector newVector = new Vector(this.dPoint.subtract(p2.dPoint));
        return newVector;
    }
    //
    //add function
    public 	Point add (Point p2){
        Point newPoint = new Point(this.dPoint.add(p2.dPoint));
        return newPoint;

    }
    //add function
    public 	Point add (Vector v2){
        double x = v2.dPoint.d1+this.dPoint.d1;
        double y = v2.dPoint.d2+this.dPoint.d2;
        double z = v2.dPoint.d3+this.dPoint.d3;
        Point newpoint = new Point(x,y,z);
        return newpoint;

    }
    //distance function
    public double distanceSquared(Point p2){
        return (this.dPoint.d1 - p2.dPoint.d1) * (this.dPoint.d1 - p2.dPoint.d1) +
                (this.dPoint.d2 - p2.dPoint.d2) * (this.dPoint.d2 - p2.dPoint.d2) +
                (this.dPoint.d3 - p2.dPoint.d3) * (this.dPoint.d3 - p2.dPoint.d3);
    }
    public double distance(Point p)	{
        double  dis = distanceSquared(p);
        dis = Math.sqrt(dis);
        return dis;
    }




}