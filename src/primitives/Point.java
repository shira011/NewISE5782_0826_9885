package primitives;

public class Point {

    final Double3 xyz;


    protected Point(Double3 xyz) {

        super();
        this.xyz = xyz;
    }
    public Point(double x, double y, double z) {

        this.xyz=new Double3(x,y,z);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point)) return false;
        Point other = (Point)obj;
        return getXyz().equals(other.getXyz());
    }
    public Vector subtract(Point p)  {
        return new Vector(this.getXyz().subtract(p.getXyz()));
    }

    public Point add(Vector v) {
        return new Point(this.getXyz().add(v.getXyz()));
    }
    /**
     *
     * */
    public double distanceSquared(Point p) {
        Double3 d = this.getXyz().subtract(p.getXyz());
        return (d.d1)*(d.d1)+(d.d2)*(d.d2)+(d.d3)*(d.d3);
    }
    public double distance(Point p) {
        return Math.sqrt(this.distanceSquared(p));
    }

    @Override
    public String toString() {
        return getXyz().toString(); }

    public double getX()
    {
        return getXyz().d1;
    }
    public double getY()
    {
        return getXyz().d2;
    }
    public double getZ()
    {
        return getXyz().d3;
    }
    public Double3 getXyz() {
        return xyz;
    }


}


