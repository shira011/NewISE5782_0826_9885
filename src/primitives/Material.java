package primitives;

public class Material {
    public int nShininess=0;
    public Double3 KD= new Double3(0);////difusive mekadem
    public Double3 KS = new Double3(0);//specular mekadem
    public Double3 KT= new Double3(0);//transparency=refraction=shkifut mekadem
    //public Double3 KR=new Double3(0);

    public Double3 KR =new Double3(0);////reflection=hishtakfut mekadem

    /**
     * @param nShininess the nShininess to set
     */
    public Material setShininess(int nShininess)
    {
        this.nShininess = nShininess;
        return this;

    }

    /**
     * @param kD the kD to set
     */
    public Material setKd(double kD)
    {
        KD = new Double3(kD);
        return this;
    }
    /* set KD
     * @param kD
     * @return
     */
    public Material setKD(Double3 kD){
        this.KD = kD;
        return this;
    }

    /**
     * @param _kS the kS to set
     */
    public Material setKs(double _kS)
    {
        KS = new Double3(_kS);
        return this;
    }
    /* set KS
     * @param kS
     * @return
     */
    public Material setKS(Double3 kS){
        this.KS = kS;
        return this;
    }
    public Material setKt(double kT) {
        this.KT = new Double3(kT);
        return this;
    }
    public Material setKr(double kR) {
        this.KR = new Double3(kR);
        return this;
    }
    /*public Material setKT(double kT)
    {
        KT = new Double3(kT);
        return this;
    }*/

    /*//**
     * @param kR the kR to set
     */
    /*public Material setKR(double kR)
    {
        KR = new Double3(kR);
        return this;
    }*/
}
