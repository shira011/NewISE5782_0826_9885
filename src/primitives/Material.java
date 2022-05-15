/**
 *
 */
package primitives;

/**
 * @author äîçùá ùìé
 *
 */
public class Material {

    public int nShininess=0;
    public Double3 Kd=new  Double3(0);
    public Double3 Ks=new  Double3(0);
    public Double3 kT =new Double3(0);

    /**
     * Material's reflection-factor
     */
    public Double3 kR =new Double3(0);



    public Double3 getKd()
    {
        return Kd;
    }
    public Double3 getKs()
    {
        return Ks;
    }
    /**
     * @param nShininess the nShininess to set
     */
    public Material setnShininess(int nShininess)
    {
        this.nShininess = nShininess;
        return this;

    }

    /**
     * @param kD the kD to set
     */
    public Material setKd(double kD)
    {
        Kd= new  Double3(kD);
        return this;
    }
    public Material setKd(Double3 kD)
    {
        Kd= kD;
        return this;
    }
    /**
     * @param kS the kS to set
     */
    public Material setKs(double kS)
    {
        Ks = new  Double3(kS);
        return this;
    }
    public Material setKs(Double3 kS)
    {
        Ks =kS;
        return this;
    }
    /**
     * Setter to the material's Kt transparency-factor
     * @param kT the material's Kt transparency-factor
     * @return The updated material
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Setter to the material's Kr reflection-factor
     * @param kR the material's Kr reflection-factor
     * @return The updated material
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }
}