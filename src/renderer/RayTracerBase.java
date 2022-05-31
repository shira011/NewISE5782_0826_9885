package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public abstract class RayTracerBase {
    protected Scene scene;//the scene we will trace

    /**
     * constructor that gets the scene
     * @param _scene
     */
    public RayTracerBase(Scene _scene)
    {
        scene=_scene;
    }

    /**
     * abstract method
     * @param ray
     * @return the color of the pixel that the ray pass through it
     */
    public abstract Color traceRay(Ray ray);
    protected abstract Color traceRay(List<Ray> rays);

}
