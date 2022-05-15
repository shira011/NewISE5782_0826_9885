package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * @author äîçùá ùìé
 *
 */
public class AmbientLight extends Light {

    private Double3  Ka; //


    /**
     * constructor that save the intensity=Ia*Ka
     *
     * @param Ia Color value
     * @param Ka double value
     */
    public AmbientLight(Color Ia,Double3 Ka ) {
        super(Ia.scale(Ka));

    }

    /**
     * A default constructor
     * this c-tor put the defalt color - black to the ambition light
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

}