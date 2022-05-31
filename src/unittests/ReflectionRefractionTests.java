/**
 *
 */
package unittests;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import renderer.ImageWriter;
import lighting.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000);

        scene.geometries.add( //
                new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKQ(0.0000006));

        camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2500, 2500).setVPDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

        scene.geometries.add( //
                new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 0, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
                new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(0.5)));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKQ(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

        scene.geometries.add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKQ(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
    @Test
    public void ourPicture() {
        Camera camera = new Camera(new Point(0, 0,0), new Vector(0, 0, -1), new Vector(0, -1, 0)) //
                .setVPSize(300, 300).setVPDistance(400);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));

        scene.geometries.add( //
                new Sphere(new Point(0,50,-800),120) //the turquise sphere
                        .setEmission(new Color(0,100,100)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(1000).setKt(0).setKr(1)),
                new Sphere(new Point(-210,110,-900),300) //the large purple sphere
                        .setEmission(new Color(30,0,50)) //
                        .setMaterial(new Material().setKd(0).setKs(0.2).setShininess(1000).setKt(0.6).setKr(0.2)),
                new Sphere(new Point(300,50,-200),80) //the blue sphere (not seen boldly)
                        .setEmission(new Color(0,0,100)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.9).setShininess(1000).setKt(0).setKr(0.2)),
                new Sphere(new Point(10, 30, -100),10) //the red sphere
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0).setKr(0.8)),
                new Sphere(new Point(-80, 80, -400),20) //the magenta sphere
                        .setEmission(new Color(java.awt.Color.MAGENTA)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.7).setKr(0)),
                new Sphere(new Point(-100, 60, -300),30) //the yellow sphere
                        .setEmission(new Color(java.awt.Color.YELLOW)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.7).setKr(0)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),//the triangle
                        new Point(-1500, -1500, -2000)).setEmission(new Color(java.awt.Color.BLACK))
                        .setMaterial(new Material().setKd(0.25).setKs(0.9).setShininess(20).setKt(0).setKr(0.8)));


        scene.lights.add(new SpotLight(new Color(200,200,200), new Point(1000,-550,1000), new Vector(0,-1,0)) //
                .setKl(4E-5).setKQ(2E-7));
        scene.lights.add(new PointLight(new Color(300, 500, 500), new Point(-50, -50, 50))//
                .setKl(0.00001).setKQ(0.000001));

        Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150) //
                .setVPDistance(1000);
        ImageWriter imageWriter = new ImageWriter("ourPictureRefrectionRefraction", 600, 600);
        camera1.setNumOfRays(144).setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //


    }
}
