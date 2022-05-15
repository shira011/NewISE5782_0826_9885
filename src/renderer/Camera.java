package renderer;
import primitives.*;
import static primitives.Util.*;

import java.util.List;
import java.util.MissingResourceException;
/**
 * class camera in the package renderer
 * The purpose of the class is to create rays from the camera towards the various geometries of the scene.
 *
 */

public class Camera {
    private Point p0; //location of camera
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double width;
    private double height;
    private double distance;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;


    /**
     * Constructor to create new camera
     *
     * @param vTo Vector value
     * @param vUp Vector value
     * @param p0 Point3D value
     * @return Camera
     * @throws Exception
     */
    public Camera(Point p0, Vector vTo, Vector vUp) throws IllegalArgumentException
    {
        if(!isZero(vTo.dotProduct(vUp))) // if vTo doesn't orthogonal to vUp
            throw new IllegalArgumentException("vUp doesnt ortogonal to vTo");

        //all the vectors need to be normalize:
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        vRight = (vTo.crossProduct(vUp)).normalize();

        this.p0 = p0;

    }

    /**
     * Set View Plane size
     *
     * @param width double value
     * @param height double value
     * @return Camera
     */
    public Camera setVPSize(double width, double height)
    {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Set View Plane distance
     *
     * @param distance double value
     * @return Camera
     */
    public Camera setVPDistance(double distance)
    {
        this.distance = distance;
        return this;
    }

    /**
     * Constructing a ray through a pixel
     *
     * @param Nx
     * @param Ny
     * @param j
     * @param i
     * @return ray from the camera to Pixel[i,j]
     */
    public Ray constructRay(int Nx, int Ny, int j, int i) {

        // Image center ---> Pc = p0 + distance * vTo
        Point Pc = p0.add(vTo.scale(distance));

        // Ratio (pixel height & width)
        double Ry = (double) height / Ny;
        double Rx = (double) width / Nx;

        Point Pij = Pc;
        double yI = -(i - (Ny - 1) / 2d) * Ry;
        double xJ = (j - (Nx - 1) / 2d) * Rx;

        // movement to middle of pixel ij
        if (!isZero(xJ)) {
            Pij = Pij.add(vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            Pij = Pij.add(vUp.scale(yI));
        }
        //return ray from camera to view plane ij coordinates
        return new Ray(p0, Pij.subtract(p0));
    }



    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter=imageWriter;
        return this;
    }
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer=rayTracer;
        return this;
    }
    /**
     * @return the p0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * @return the vUp
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * @return the vTo
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * @return the vRight
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }
    public Camera getImageWriter()
    {
        return this;
    }
    public RayTracerBase getRayTracerBase() {
        return this.rayTracer;
    }
    /**
     * The function transfers beams from camera to pixel, tracks the beam
     *  and receives the pixel color from the point of intersection
     *
     * @author
     * @throws Exception
     */

    public Camera renderImage() {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }

            //rendering the image
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    imageWriter.writePixel(j, i, castRay(nX, nY, j, i));
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
        return this;
    }
    private Color castRay(int nX, int nY, int j, int i) {
        Ray ray = constructRay(nX, nY, j, i);
        Color pixelColor = rayTracer.traceRay(ray);
        return pixelColor;
    }
    /**
     * A function that creates a grid of lines
     *
     * @param interval int value
     * @param color Color value
     * */
    public void printGrid(int interval, Color color)
    {
        if (imageWriter == null)
            throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter", "imageWriter");

        for (int i = 0; i < imageWriter.getNx(); i++){
            for (int j = 0; j < imageWriter.getNy(); j++)	{
                if(i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(i, j, color);
            }
        }
    }

    /**
     * A function that finally creates the image.
     * This function delegates the function of a class imageWriter
     *
     * @author
     * */
    public void writeToImage()	{
        if (imageWriter == null)
            throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter", "imageWriter");

        imageWriter.writeToImage();
    }

}