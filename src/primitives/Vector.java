package primitives;

import static primitives.Util.isZero;

public class Vector extends Point
{
    /*************** ctors *****************/
    /**
     * ctor that gets 1 parameter
     * @param
     */
    public Vector(double d1,double d2,double d3){
        super(d1,d2,d3);
        if(Double3.ZERO.equals(new Double3(d1,d2,d3)))
            throw new IllegalArgumentException("Can't create the zero vector");
    }	/*************** get *****************/
public Vector(Double3 d) {
    super(d);
    if(Double3.ZERO.equals(d))
        throw new IllegalArgumentException("Can't create the zero vector");
}

    /**
     * subtract the wanted vector from it's head
     * @param v
     * @return the vector
     */
    public Vector subtract (Vector v){
        return (Vector) super.subtract(v);
    }
    /*************** calculating functions *****************/
    /**
     * add the wanted vector to it's head
     * @param v
     * @return the new vector
     */
    public Vector add (Vector v){
        return new Vector(super.add(v).dPoint);
    }
    /**
     * @param
     * @return the wanted vector by multipile each of it's coordinates by s
     */
    public Vector scale(double scl){
        Vector v = new Vector(this.dPoint.d1*scl,this.dPoint.d2*scl,this.dPoint.d3*scl);
        return v;
    }
    /**
     * @param v
     * @return the scallar
     */
    public double dotProduct(Vector v){
        return this.dPoint.d1* v.dPoint.d1+this.dPoint.d2*v.dPoint.d2+this.dPoint.d3*v.dPoint.d3;
    }

    /**
     * @param v
     * @return a new vector of the cross product
     */
    public Vector crossProduct(Vector v) {
        double ax = dPoint.d1;
        double ay = dPoint.d2;
        double az = dPoint.d3;

        double bx = v.dPoint.d1;
        double by = v.dPoint.d2;
        double bz = v.dPoint.d3;

        double cx = ay*bz - az*by;
        double cy = az*bx - ax*bz;
        double cz = ax*by - ay*bx;

        return new Vector(cx, cy, cz);

    }
    public double lengthSquared() {
        double dis = this.dPoint.d1*this.dPoint.d1+this.dPoint.d2*this.dPoint.d2+this.dPoint.d3*this.dPoint.d3;
        return dis;
    }
    /**
     * @return the length
     */
    public double length() {
        double dis = lengthSquared();
        return Math.sqrt(dis);
    }
    /*************** normalize *****************/
    /**
     * @return a new normalize vector
     */
    public Vector normalize() {
        //double dis = length();
        // Vector newv = new Vector(this.dPoint.d1/dis,this.dPoint.d2/dis,this.dPoint.d3/dis);
        // return newv;
        double length = length();
        return new Vector(dPoint.reduce(length));
    }
    /*************** admin *****************/
    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }

    @Override
    public String toString() {return "Vector="+super.toString();}

}