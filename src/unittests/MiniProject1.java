package unittests;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.YELLOW;

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
    @Test
    public void ourPicture() {
        ImageWriter imageWriter2 = new ImageWriter("ourPicture4x4ASS", 900, 600);
        Camera camera = new Camera(new Point(689, 777, -100), new Vector(-597, -754, -46), new Vector(0, -46, 754)) //
                .setVPSize(337, 225).setVPDistance(1200);

        scene.setAmbientLight(new AmbientLight(new Color(209, 240, 251), new Double3(0.15)));

        scene.geometries.add( //
                new Plane(new Point(1, -200, -200), new Point(150, -200, -135), new Point(75, -200, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)).setEmission(new Color(1, 1, 1)), //

                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.5)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.5)), //
                new Sphere(new Point(70, -20, -100), 20) //
                        .setEmission(new Color(82, 228, 228)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(30).setKt(0.1)),

                new Triangle(new Point(-150, -150, -215), new Point(150, -150, -235), new Point(75, 75, -250)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.4)), //
                new Triangle(new Point(-150, -150, -215), new Point(-70, 70, -240), new Point(75, 75, -250)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.5)), //
                new Sphere(new Point(30, -40, -200), 20) //
                        .setEmission(new Color(java.awt.Color.ORANGE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(30).setKr(0.4)),
                new Sphere(new Point(90, 90, -200), 11) //
                        .setEmission(new Color(0, 0, 200)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(30).setKr(0.4)),

                new Sphere(new Point(-50, -90, -190), 12) //
                        .setEmission(new Color(210, 47, 20)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(100).setKr(0.3)),

                new Triangle(new Point(-40, -20, -100), new Point(-40, -30, -100), new Point(-40, -20, -90)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(100).setKr(0)),
                new Triangle(new Point(-40, -20, -100), new Point(-40, -30, -100), new Point(-40, -20, -110)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(100).setKr(0)),
                new Triangle(new Point(-40, -20, -100), new Point(-40, -10, -100), new Point(-40, -20, -90)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(100).setKr(0)),
                new Triangle(new Point(-40, -20, -100), new Point(-40, -10, -100), new Point(-40, -20, -110)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(100).setKr(0)),

                new Triangle(new Point(-40, -20, -100), new Point(-30, -40, -100), new Point(-40, -20, -90)) //
                        .setEmission(new Color(java.awt.Color.black)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(100).setKr(1)),
                new Triangle(new Point(-40, -20, -100), new Point(-30, -40, -100), new Point(-40, -20, -110)) //
                        .setEmission(new Color(java.awt.Color.black)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(100).setKr(1)),
                new Triangle(new Point(-40, -20, -100), new Point(-50, 0, -100), new Point(-40, -20, -90)) //
                        .setEmission(new Color(java.awt.Color.black)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(100).setKr(1)),
                new Triangle(new Point(-40, -20, -100), new Point(-50, 0, -100), new Point(-40, -20, -110)) //
                        .setEmission(new Color(java.awt.Color.black)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(100).setKr(1)),

                new Triangle(new Point(125, 50, -100), new Point(125, 43, -100), new Point(125, 50, -93)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(100).setKt(0.5)),
                new Triangle(new Point(125, 50, -100), new Point(125, 43, -100), new Point(125, 50, -107)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(100).setKt(0.5)),
                new Triangle(new Point(125, 50, -100), new Point(125, 57, -100), new Point(125, 50, -93)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(100).setKt(0.5)),
                new Triangle(new Point(125, 50, -100), new Point(125, 57, -100), new Point(125, 50, -107)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(100).setKt(0.5)),

                new Sphere(new Point(-50, -90, -190), 6) //
                        .setEmission(new Color(58, 138, 160)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setShininess(100)),

                new Sphere(new Point(60, 50, -50), 40) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.8)),

                new Sphere(new Point(70, -20, -100), 30) //
                        .setEmission(new Color(40 ,40, 100)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.8)),

                new Sphere(new Point(-45, 0, -200), 14) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.4)),
                new Sphere(new Point(-45, 0, -200), 7) //
                        .setEmission(new Color(java.awt.Color.GREEN)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),

                new Sphere(new Point(-55, -20, -175), 10) //
                        .setEmission(new Color(198, 91, 219)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.4).setShininess(50).setKr(0.4)),
                new Sphere(new Point(-90, 10, -200), 7) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKd(0.3).setKs(0.1).setShininess(20).setKr(0.4)),

                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(150, -150, -235)) //
                        .setMaterial(new Material().setKd(0).setKs(1).setShininess(60).setKr(1)), //
                new Triangle(new Point(150, -150, -135), new Point(150, -150, -235), new Point(-150, -150, -215)) //
                        .setMaterial(new Material().setKd(0).setKs(1).setShininess(60).setKr(1)), //

                new Triangle(new Point(-98, 63, -86), new Point(-70, 70, -140), new Point(-150, -150, -115)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.5)), //
                new Triangle(new Point(-98, 63, -86), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.5)), //
                new Triangle(new Point(-98, 63, -86), new Point(75, 75, -150), new Point(-150, -150, -115)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.5)), //

                new Triangle(new Point(-98, 63, -200), new Point(-70, 70, -240), new Point(-150, -150, -215)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.4)), //
                new Triangle(new Point(-98, 63, -200), new Point(-70, 70, -240), new Point(75, 75, -250)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.4)), //
                new Triangle(new Point(-98, 63, -200), new Point(75, 75, -250), new Point(-150, -150, -215)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.4))); //


        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKQ(2E-7));
        scene.lights.add(new SpotLight(new Color(200, 180, 180), new Point(914, 826, -130), new Vector(-959.0, -826.0, -65.0)) //
                .setKl(4E-5).setKQ(2E-7));
        scene.lights.add(new DirectionalLight(new Color(30, 30, 150), new Vector(200, -500, 40)));
        scene.lights.add(new PointLight(new Color(60,60,60), new Point(215, 368, 0))//
                .setKl(0.00001).setKQ(0.000001));


       // scene.lights.add(new PointLight(new Color(500, 500, 500), new Point(100, 0, -100)).setKQ(0.000001));
        camera.setNumOfRays(110).setImageWriter(imageWriter2).setMultithreading(3).setDebugPrint(0.1) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }//end
}