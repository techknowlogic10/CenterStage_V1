package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.RobotPosition;
import org.firstinspires.ftc.teamcode.util.TeamShippingElementDetector;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;


@Autonomous
//@Disabled
@Config
public class ImageTest extends LinearOpMode {



    @Override
    public void runOpMode() throws InterruptedException {

        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


        waitForStart();


        Mat image = Imgcodecs.imread("path/to/your/image.jpg");
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        CascadeClassifier faceCascade = new CascadeClassifier("path/to/haarcascade_frontalface_alt.xml");
       /* MatOfRect faces = new MatOfRect();
        faceCascade.detectMultiScale(grayImage, faces);

        List<Rect> faceList = new ArrayList<>(faces.toList());
        for (Rect face : faceList) {
            Imgproc.rectangle(image, face, new Scalar(0, 255, 0), 3);
        }*/

        Imgcodecs.imwrite("path/to/save/detected_faces.jpg", image);


        telemetry.addLine("");
        telemetry.update();

        while(opModeIsActive()) {
            sleep(100);
        }

        telemetry.update();
    }
}