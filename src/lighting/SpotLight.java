package lighting;

import lighting.PointLight;
import primitives.*;

import static primitives.Util.alignZero;


/**
 * spot light- extends point light, but has direction to the light.
 *
 * @author efrat & esti
 */
public class SpotLight extends PointLight {
    private Vector direction;        //the direction of the spot light

    /* ********* Constructors ***********/

    /**
     * a new spotlight
     *
     * @param color     the color of the light
     * @param position  the position of the light source
     * @param direction the direction of the light
     */
    public SpotLight(Color color, Point position, Vector direction) {
        super(color, position);
        this.direction = direction.normalize();
    }

    /* ************* Getters & setters *******/

    /**
     * get light intensity
     *
     * @param p the point
     * @return light
     */
    @Override
    public Color getIntensity(Point p) {
        Vector l = super.getL(p);

        if (alignZero(direction.dotProduct(l)) <= 0) //In case the dir * l return zero or negative number
            return Color.BLACK;

        return super.getIntensity(p).scale(direction.dotProduct(l));
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
