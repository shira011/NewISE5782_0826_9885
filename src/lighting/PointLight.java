/**
 *
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * @author äîçùá ùìé
 *
 */
public class PointLight extends Light implements LightSource {

    private Point position;
    private double KC = 1;
    private double KL = 0;
    private double KQ = 0;

    public PointLight(Color intensity, Point position)
    {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) throws IllegalArgumentException
    {
        return getIntensity().reduce((KC + KL * p.distance(position)+ KQ * p.distanceSquared(position)));
    }

    @Override
    public Vector getL(Point p) throws IllegalArgumentException
    {
        if (p.equals(position))
            return null; //In order not to reach a state of exception due to the zero vector
        return p.subtract(position).normalize();
    }

    /**
     * setter to filed kl
     * @param kL the kL to set
     * @return the object - builder
     */
    public PointLight setKL(double kL)
    {
        KL = kL;
        return this;
    }


    /**
     * setter to filed kq
     * @param kQ the kQ to set
     * @return the object - builder
     */
    public PointLight setKQ(double kQ)
    {
        KQ = kQ;
        return this;
    }
    public double getDistance(Point point)
    {
        return position.distance(point);
    }
}