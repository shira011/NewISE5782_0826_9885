package scene;

import java.util.LinkedList;
import java.util.List;

import lighting.AmbientLight;
import lighting.LightSource;
import geometries.Geometries;
import primitives.Color;

/**
 * class Scene for PDS
 *
 * @author
 */
public class Scene
{
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight();
    public Geometries geometries;
    public List<LightSource>lights=new LinkedList<LightSource>() ;


    /**
     * constructor
     *
     * @author
     * @param name
     * */
    public Scene(String name)
    {
        geometries = new Geometries();
    }

    public Color getbackground()
    {
        return background;
    }
    /**
     * setter function to background, and return this for builder pattern
     *
     * @author
     * @param background the background to set
     */
    public Scene setBackground(Color background)
    {
        this.background = background;
        return this;
    }


    /**
     * setter function to ambientLight, and return this for builder pattern
     *
     * @author
     * @param ambientLight the ambientLight to set
     */
    public Scene setAmbientLight(AmbientLight ambientLight)
    {
        this.ambientLight = ambientLight;
        return this;

    }


    /**
     * setter function to geometries, and return this for builder pattern
     *
     * @author
     * @param geometries the geometries to set
     */
    public Scene setGeometries(Geometries geometries)
    {
        this.geometries = geometries;
        return this;
    }


    public Scene setLights(List<LightSource> lights)
    {
        this.lights = lights;
        return this;
    }
}