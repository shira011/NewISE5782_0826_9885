/**
 *
 */
package lighting;

import primitives.Color;

/**
 * @author äîçùá ùìé
 *
 */
public class Light {

    protected Color intensity;//private?


    /**
     * constructor for light
     * @return the intensity
     */
    protected Light(Color intensity)
    {
        this.intensity=intensity;
    }

    /**
     * getter to intensity
     * @return intensity Color
     * */
    public Color getIntensity()
    {
        return intensity;
    }
}