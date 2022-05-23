package scene;

import geometries.Geometries;
import lighting.LightSource;
import primitives.Color;
import lighting.AmbientLight;

import java.util.LinkedList;
import java.util.List;

public class Scene {

    public String name;					//scene's name
    public Color background=Color.BLACK;//default color of the background (unless it was changed)
    public AmbientLight ambientLight=new AmbientLight();	//ambient light of the scene's objects
    public Geometries geometries = new Geometries();//the geometries that are in the scene
    public List<LightSource> lights = new LinkedList<>();

    /*************** constructor *****************/
    /**
     * restart the name of scene and restarts an empty geometries list.
     * @param _name
     */
    public Scene(String _name) {
        name=_name;
        geometries= new Geometries();//empty list of geometries
    }

    /*************** setters *****************/
    /**
     * @param geometries
     * @return the scene itself to allow design pattern of builder- to concatenate calls to setters.
     */
    public Scene setGeometries(Geometries geometries)
    {
        this.geometries = geometries;
        return this;
    }

    /**
     * @param ambientLight
     * @return the scene itself to allow design pattern of builder- to concatenate calls to setters.
     */
    public Scene setAmbientLight(AmbientLight ambientLight)
    {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * @param backGround
     * @return the scene itself to allow design pattern of builder- to concatenate calls to setters.
     */
    public Scene setBackGround(Color backGround)
    {
        this.background = backGround;
        return this;
    }

    /**
     * setter function to lights  and return this for builder pattern
     * @param lights the lights to set
     */
    public Scene setLights(List<LightSource> lights)
    {
        this.lights = lights;
        return this;
    }



}
