package renderer;

import geometries.Intersectable;
import primitives.*;
import  renderer.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * camera producing ray through a view plane
 */
public class Camera {

    private int numOfRays;
    private Vector _vTo;            // vector pointing towards the scene vp המרחק מהמישור תצוגה
    private Vector _vUp;            // vector pointing upwards direction up
    private Vector _vRight;//כיוון המצלמה בצידה הימני
    private Point _p0;             // camera eye location of the camera in the middel of the עדשה

    private double _distance;       // camera distance from view plane
    private double _width;          // view plane width
    private double _height;         // view plane height
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    private int threadsCount = 0;
    private static final int SPARE_THREADS = 3; // Spare threads if trying to use all the cores
    private boolean print = false; // printing progress percentage


    public Vector getvTo() {
        return _vTo;
    }

    public Vector getvUp() {
        return _vUp;
    }

    public Vector getvRight() {
        return _vRight;
    }

    public Point getP0() {
        return _p0;
    }

    public double getDistance() {
        return _distance;
    }

    public double getWidth() {
        return _width;
    }

    public double getHeight() {
        return _height;
    }


    /**
     * @param p0  origin point in 3D space
     * @param vUp
     * @param vTo
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("vTo and vUp should be orthogonal");
        _p0 = p0;

        _vTo = vTo.normalize();
        _vUp = vUp.normalize();

        _vRight = _vTo.crossProduct(vUp).normalize();
    }

    /**
     * set distance between the camera and its view plane
     *
     * @param distance the distnace for the view plane alligator
     * @return instance of camera for chaining
     */
    public Camera setVPDistance(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Illegal value of distance");
        }
        _distance = distance;
        return this;
    }

    /**
     * set view plane size
     * @param width  physical width
     * @param height physical height
     * @return
     */
    public Camera setVPSize(double width, double height) {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException("Width or height cannot be negative!");
        this._width = width;
        this._height = height;
        return this;
    }

    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBasic rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Camera setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            this.threadsCount = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            this.threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }




    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     */
    public Camera renderImageThreaded() throws MissingResourceException{
        if (imageWriter == null)
            throw new MissingResourceException("All the render's fields mustn't be null, including the camera", "Camera", null);
        if (rayTracer == null)
            throw new MissingResourceException("All the render's fields mustn't be null, including the imageWriter", "ImageWriter", null);
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();

        Pixel.initialize(nY, nX, Pixel.printInterval);
        while (threadsCount-- > 0) {
            new Thread(() -> {
                for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                    castRay(nX, nY, pixel.col, pixel.row, numOfRays);
            }).start();
        }
        Pixel.waitToFinish();
        return this;
    }


    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     * פונקציה זו מציגה את מפת צבע הפיקסלים של התמונה מהסצנה הכלולה בה
     * האובייקט Render
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
            if (threadsCount == 0)
             for (int i = 0; i < nY; i++)
                 for (int j = 0; j < nX; j++)
                    imageWriter.writePixel(j, i, castRay(nX, nY, j, i,numOfRays));

            else  renderImageThreaded();//if process is with multi-threading
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
        return this;
    }

    /**
     * Cast ray from camera in order to color a pixel
     * @param nX resolution on X axis (number of pixels in row)
     * @param nY resolution on Y axis (number of pixels in column)
     * @param j pixel's column number (pixel index in row)
     * @param i pixel's row number (pixel index in column)
     */
    private Color castRay(int nX, int nY, int j, int i, int numOfRays) {
        Color color;
        if(numOfRays == 1 || numOfRays == 0)
        {
            Ray ray = constructRayThroughPixel(nX, nY, j, i);
             color = rayTracer.traceRay(ray);
            imageWriter.writePixel(j, i, color);
        }
        else
        {
            List<Ray> rays = constructBeamThroughPixel(nX, nY, j, i,numOfRays);
             color = rayTracer.traceRay(rays);
            imageWriter.writePixel(j, i, color);
        }
        return color;
    }

    /**
     * Create a grid [over the picture] in the pixel color map. given the grid's
     * step and color.
     *
     * @param interval  grid's step
     * @param color grid's color
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }


    /**
     * the final action of producing the wanted image
     */
    public void writeToImage() {
        if (imageWriter == null)//if the imageWriter field, that creates the image, is missing in Render-
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        imageWriter.writeToImage();//delegate the writing to image to imageWriter, that deals with it.

    }


    public List<Intersectable.GeoPoint> findRay(int nX, int nY, Intersectable intersect) {
        Ray ray;
        List<Intersectable.GeoPoint> result = new LinkedList<>();
        List<Intersectable.GeoPoint> intersectionPoints;

        for (int i = 0; i < _width; i++) {
            for (int j = 0; j < _height; j++) {
                ray = constructRay(nX, nY, i, j);
                intersectionPoints = intersect.findGeoIntersections(ray) ;
                if (intersectionPoints != null) {
                    result.addAll(intersectionPoints);
                }
            }
        }
        return result.isEmpty() ? null : result;
    }
    public Ray constructRay(int nX, int nY, int j, int i) {
        Vector dir;
        Point pCenter, pCenterPixel;
        double ratioY, ratioX, yI, xJ;

        pCenter = _p0.add(_vTo.scale(_distance)); //give us the center of the pixel
        ratioY = alignZero(_height / nY);
        ratioX = alignZero(_width / nX);

        pCenterPixel = pCenter;
        yI = alignZero(-1 * (i - (nY - 1) / 2d) * ratioY);
        xJ = alignZero((j - (nX - 1) / 2d) * ratioX);
        if (!isZero(xJ)) {
            pCenterPixel = pCenterPixel.add(_vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            pCenterPixel = pCenterPixel.add(_vUp.scale(yI));
        }
        dir = pCenterPixel.subtract(_p0);

        return new Ray(dir,_p0);
    }
    /**
     * The function is responsible for creating the rays from the camera
     * @param nX int value - resolution of pixel in X
     * @param nY int value - resolution of pixel in Y
     * @param j int value - index of column
     * @param i int value - index of row
     * @return Ray that created
     * @throws Exception
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        // Image center:
        Point pC = _p0.add(_vTo.scale(this._distance));

        // Ratio:
        double Ry = _height / nY;
        double Rx = _width / nX;

        // Pixel[i,j] center
        double yi = alignZero(-(i - (nY - 1) / 2.0) * Ry);
        double xj = alignZero((j - (nX - 1) / 2.0) * Rx);

        Point pIJ = pC;

        // To avoid a zero vector exception
        if (xj != 0)
            pIJ = pIJ.add(_vRight.scale(xj));
        if (yi != 0)
            pIJ = pIJ.add(_vUp.scale(yi));

        Vector vIJ = pIJ.subtract(this._p0);

        return new Ray( vIJ, _p0);
    }
    public List<Ray> constructBeamThroughPixel (int nX, int nY, int j, int i, int raysAmount)
    {
//		//æøé÷ú ëîåú ùì ÷øðééí ìúåê öåøú âøéã
//		List<Ray> Rays = new ArrayList<Ray>();
        int numOfRays = (int)Math.floor(Math.sqrt(raysAmount)); //num of rays in each row or column

        if (numOfRays==1)
            return List.of(constructRayThroughPixel(nX, nY, j, i));
        Point Pc;
        if (isZero(_distance))
            Pc=_p0;
        else
            Pc=_p0.add(_vTo.scale(_distance));

        double Ry= _height/nY;
        double Rx=_width/nX;
        double Yi=(i-(nY-1)/2d)*Ry;
        double Xj=(j-(nX-1)/2d)*Rx;
//
        double PRy = Ry / numOfRays; //height distance between each ray
        double PRx = Rx / numOfRays; //width distance between each ray
//
        //The distance between the screen and the camera cannot be 0
        if (isZero(_distance))
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }

        List<Ray> sampleRays = new ArrayList<>();


        for (int row = 0; row < numOfRays; ++row)
        {//foreach place in the pixel grid
            for (int column = 0; column < numOfRays; ++column)
            {
                sampleRays.add(constructRaysThroughPixel(PRy,PRx,Yi, Xj, row, column));//add the ray
            }
        }
        sampleRays.add(constructRayThroughPixel(nX, nY, j, i));//add the center screen ray
        return sampleRays;
    }
    /**
     * In this function we treat each pixel like a little screen of its own and divide it to smaller "pixels".
     * Through each one we construct a ray. This function is similar to ConstructRayThroughPixel.
     * בפונקציה זו אנו מתייחסים לכל פיקסל כאל מסך קטן משלו ומחלקים אותו ל"פיקסלים" קטנים יותר.
     *      * דרך כל אחד בונים קרן. פונקציה זו דומה ל-ConstructRayThroughPixel.
     * @param Ry height of each grid block we divided the pixel into
     * @param Rx width of each grid block we divided the pixel into
     * @param yi distance of original pixel from (0,0) on Y axis
     * @param xj distance of original pixel from (0,0) on X axis
     * @param j j coordinate of small "pixel"
     * @param i i coordinate of small "pixel"
     * @return beam of rays through pixel
     */
    private Ray constructRaysThroughPixel(double Ry,double Rx, double yi, double xj, int j, int i)
    {
        Point Pc = _p0.add(_vTo.scale(_distance)); //the center of the screen point

        double ySampleI =  (i *Ry + Ry/2d); //The pixel starting point on the y axis
        double xSampleJ=   (j *Rx + Rx/2d); //The pixel starting point on the x axis

        Point Pij = Pc; //The point at the pixel through which a beam is fired
        //Moving the point through which a beam is fired on the x axis
        if (!isZero(xSampleJ + xj))
        {
            Pij = Pij.add(_vRight.scale(xSampleJ + xj));
        }
        //Moving the point through which a beam is fired on the y axis
        if (!isZero(ySampleI + yi))
        {
            Pij = Pij.add(_vUp.scale(-ySampleI -yi ));
        }
        Vector Vij = Pij.subtract(_p0);

        return new Ray(_p0,Vij);//create the ray throw the point we calculate here
    }
    public Camera setNumOfRays(int numOfRays)
    {
        if(numOfRays == 0)
            this.numOfRays=1;
        else
            this.numOfRays = numOfRays;
        return this;
    }
    public Camera setDebugPrint(double d) {
        print = true;
        return this;
    }
}