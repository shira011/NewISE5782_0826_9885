package unittests;

import geometries.Sphere;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

public class MiniProject1 {
    private Scene scene = new Scene("Test scene");

    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));

        scene.geometries.add( //
//				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
//						.setMaterial(new Material().setKD(0.5).setKS(0.5).setnShininess(60)), //
//				new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
//						.setMaterial(new Material().setKD(0.5).setKS(0.5).setnShininess(60)), //
                new Sphere(new Point(0, 0, 0), 80) //
                        .setEmission(new Color(java.awt.Color.yellow)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKQ(2E-7));

        ImageWriter imageWriter = new ImageWriter("miniproject1-from", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }
    @Test
    public void trianglesTransparentSphere2()
    {
        int numOfRays=144;
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));

        scene.geometries.add( //
                new Sphere(new Point(0, 0, 0), 80) //
                        .setEmission(new Color(java.awt.Color.yellow)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKQ(2E-7));

        ImageWriter imageWriter = new ImageWriter("miniproject1-after"
                + "", 600, 600);

        camera.setNumOfRays(numOfRays).setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }
}