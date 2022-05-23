package lighting;

import primitives.*;

public class AmbientLight extends Light{

    /*************** ctor *****************/
    /**
     * ctor that restarts Intensity of super class by Ia and ka
     * @param Ia
     * @param ka
     */
    public AmbientLight(Color Ia, Double3 ka) {

        super(Ia.scale(ka));//the father is "Light"- the basic light class
    }

    /*************** ctor *****************/
    /**
     * ctor that restarts Intensity of super class by Ia and ka
     */
    public AmbientLight() {
        super(Color.BLACK);//the father is "Light"- the basic light class
    }

    /*************** get *****************/
    /**
     * @return the Intensity
     */
}

