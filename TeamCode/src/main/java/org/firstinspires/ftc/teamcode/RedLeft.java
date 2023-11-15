package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.RobotPosition;

import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;



@Autonomous(name = "Red Left")
@Config
public class RedLeft extends AbstractAutonomusDrive {

    //public static Pose2d STARTING_POSITION = new Pose2d(37,-60, Math.toRadians(90));
    public static Pose2d STARTING_POSITION = new Pose2d(0, 0, 0);
    public static RobotPosition ROBOT_POSITION = RobotPosition.RED_WAREHOUSE;

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera


    //private static final String TFOD_MODEL_FILE = "testmodel.tflite";
    //private static final String TFOD_MODEL_FILE = "CenterStage.tflite";
    private static final String TFOD_MODEL_FILE = "TechKNOWLogic_Centerstage.tflite";
    //private static final String[] LABELS = { "testmodel", };
    private static final String[] LABELS = { "blueprop","redprop"};
    //private static final String[] LABELS = { "Pixel",};



    @Override
    public void runOpMode() throws InterruptedException {

        //Servo Grabber = hardwareMap.get(Servo.class, "grabber");
        Servo PurpleDrop = hardwareMap.get(Servo.class, "purpledrop");
        //PurpleDrop.scaleRange(0.01, 0.4);
        //PurpleDrop.setPosition(0.4);

        Servo YellowDrop = hardwareMap.get(Servo.class, "yellowdrop");

      /*Grabber.scaleRange(0, 1);
        Grabber.setPosition(0.8); */

       /* TeamShippingElementDetector detector = new TeamShippingElementDetector(hardwareMap, telemetry, ROBOT_POSITION, true);
        //Detection continue to happen throughout init
        detector.startDetection();
        */



        telemetry.addLine("before initTfod");
        initTfod();
        telemetry.addLine("after initTfod");
        String shippingElementPosition = "NOTFOUND";




        // TeamPropDetector teamPropDetector = new TeamPropDetector(hardwareMap, telemetry);
        //  String shippingElementPosition = "NOTFOUND";


        while (opModeInInit() && shippingElementPosition == "NOTFOUND"  ) {

            telemetry.addLine("while opModeIsActive");

            //telemetryTfod();
            shippingElementPosition = startDetection();


            // Push telemetry to the Driver Station.
            telemetry.update();


            // Share the CPU.
            sleep(20);
        }


        telemetry.addLine("BEFORE shippingElementPosition:" +shippingElementPosition);
        // Save more CPU resources when camera is no longer needed.


        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start Autonomous");
        telemetry.update();
        waitForStart();

        //visionPortal.stopStreaming();
        visionPortal.close();


        SampleMecanumDrive drivetrain = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence trajSeq = null;

        if(shippingElementPosition == "CENTER") {

            telemetry.addLine("INSIDE IF CENTER");

            trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .strafeLeft(20)
                    .forward(25)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    .waitSeconds(1)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(1)) // up servo
                    .back(7)
                    .strafeLeft(25)
                    .forward(30)
                    .turn(Math.toRadians(-147)) //clockwise
                    .forward(75)
                    .strafeRight(40)
                    .addTemporalMarker(() -> YellowDrop.setPosition(0.01)) // Lower servo
                    .waitSeconds(1)
                    .addTemporalMarker(() -> YellowDrop.setPosition(1)) // up servo
                    .strafeLeft(38)
                    .forward(10)
                    .build();



        } else if(shippingElementPosition == "LEFT") {

            telemetry.addLine("INSIDE ELSE IF LEFT");

            trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .strafeLeft(10)
                    .forward(20)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    .waitSeconds(3)
                    .back(8)
                    .strafeRight(8)
                    .forward(30)
                    .turn(Math.toRadians(150)) //clockwise
                    .forward(80)
                    .strafeRight(20)
                    // .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    //.waitSeconds(3)
                    .strafeLeft(20)
                    .forward(10)
                    .build();

        } else {  //RIGHT
            telemetry.addLine("INSIDE ELSE RIGHT");

            trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .forward(28)
                    .turn(Math.toRadians(-150)) //clockwise
                    .forward(8)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    .waitSeconds(3)
                    .back(8)
                    .strafeLeft(20)
                    .forward(80)
                    .strafeRight(20)
                    // .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    //.waitSeconds(3)
                    .strafeLeft(20)
                    .forward(10)
                    .build();


        }
        drivetrain.followTrajectorySequence(trajSeq);

    }
}
