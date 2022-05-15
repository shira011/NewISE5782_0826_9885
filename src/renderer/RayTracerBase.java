/**
 *
 */
package renderer;
import primitives.Ray;

import java.util.List;

import primitives.Color;
import scene.Scene;

/**
 * @author äîçùá ùìé
 *
 */
public abstract class RayTracerBase {

    protected Scene myscene;

      /**
     * constructor
     * @param myscene Scene value
     */
    public  RayTracerBase(Scene myscene)
    {
        this.myscene=myscene;
    }


    /**
     * Statement of an abstract function that calculates the color for the nearest intersection point,
     * if no intersection points are returned the color of the background
     * @param ray Ray value
     * @throws Exception
     * @return Color
     *  */
    public abstract Color traceRay(Ray ray) throws IllegalArgumentException ;

    //protected abstract Color traceRay(List<Ray> rays);
}