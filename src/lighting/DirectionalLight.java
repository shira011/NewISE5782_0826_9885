package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{

    private Vector direction;
    /**
     * constructor for light
     *
     * @param intensity
     * @return the intensity
     * @author shira suissa & talya moshe
     */
    public DirectionalLight(Color intensity, Vector v) {
        super(intensity);
        direction =v.normalize();
    }

    @Override
    public Color getIntensity(Point p) {return getIntensity();}

    @Override
    public Vector getL(Point p) {
        return direction;
    }
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
