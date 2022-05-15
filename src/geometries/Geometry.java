package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public abstract class Geometry extends Intersectable{
    private Material material=new Material();
    protected  Color emission = Color.BLACK;

    public abstract Vector getNormal(Point p);

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
    /**
     * getter function for the color filed in geometry class
     * @return emission Color value
     * */
    public Color getEmission() {
        return emission;
    }
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * getter function for the Material filed in geometry class
     *
     * @return the material
     */
    public Material getMaterial()
    {
        return material;
    }
}