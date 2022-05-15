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
public class SpotLight extends PointLight  {
    private Vector direction;

    public SpotLight(Color intensity, Point position, Vector direction)
    {
        super(intensity, position);
        this.direction=direction.normalize();
    }



}