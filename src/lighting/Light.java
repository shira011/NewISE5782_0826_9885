package lighting;
import primitives.Color;


/**
 * class for light - abstract class
 *
 * @author shira suissa & talya moshe
 */
abstract class Light
{

    private Color intensity;


    /**
     * constructor for light
     *
     * @author shira suissa & talya moshe
     * @return the intensity
     */
    protected Light(Color intensity)
    {
        this.intensity=intensity;
    }

    /**
     * getter to intensity
     *
     * @author shira suissa & talya moshe
     * @return intensity Color
     * */
    public Color getIntensity()
    {
        return intensity;
    }


}
