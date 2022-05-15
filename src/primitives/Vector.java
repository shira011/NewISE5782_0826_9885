package primitives;

public class Vector extends Point {


    public Vector(double x, double y, double z) {
        super(x, y, z);
        if(this.getXyz().equals(Double3.ZERO))
            throw new IllegalArgumentException();
//l
    }
    public Vector(Double3 d) {
        super(d);
        if (this.getXyz().equals(Double3.ZERO) )
            throw new IllegalArgumentException();

    }


    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point)) return false;
        Vector other = (Vector)obj;
        return getXyz().equals(other.getXyz());
    }
    public Vector add(Vector v){

        return new Vector(this.getXyz().add(v.getXyz()));
    }
    public Vector scale(double x) {
        return new Vector(this.getXyz().d1*x,this.getXyz().d2*x,this.getXyz().d3*x);
    }
    public  double dotProduct(Vector u) {
        //  Vector.checkLengths(u1, u2);

        double sum = 0;
        sum += this.getXyz().d1*u.getXyz().d1+this.getXyz().d2*u.getXyz().d2+this.getXyz().d3*u.getXyz().d3;
        return sum;
    }
    public  Vector crossProduct(Vector a) {

        return new Vector(this.getXyz().d2 * a.getXyz().d3 - this.getXyz().d3 * a.getXyz().d2,
                this.getXyz().d3 * a.getXyz().d1 - this.getXyz().d1 * a.getXyz().d3,
                this.getXyz().d1 * a.getXyz().d2 -this.getXyz().d2 * a.getXyz().d1);
    }
    public double lengthSquared(){
        double sum = 0;
        sum += (this.getXyz().d1)*(this.getXyz().d1)+(this.getXyz().d2)*(this.getXyz().d2)+(this.getXyz().d3)*(this.getXyz().d3);
        return sum;
    }
    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    public  Vector normalize() {
        if (this.getXyz().equals((Double3.ZERO))) {
            throw new IllegalArgumentException();
        } else {
            return this.scale(1.0/this.length());
        }
    }
    @Override
    public String toString() {
        String str = "[";
        String sep = ",\n ";


        str += this.getXyz().d1+sep+this.getXyz().d2+sep+this.getXyz().d3;

        return str + "]";
    }
}