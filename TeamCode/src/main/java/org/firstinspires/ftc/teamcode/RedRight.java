package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.trajectory.SimpleTrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.RobotPosition;
import org.firstinspires.ftc.teamcode.util.TeamShippingElementDetector;
import org.firstinspires.ftc.teamcode.util.TeamPropDetector;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous(name = "Red Right")
@Config
public class RedRight extends LinearOpMode {

    public static Pose2d STARTING_POSITION = new Pose2d(37,-60, Math.toRadians(90));
    public static RobotPosition ROBOT_POSITION = RobotPosition.RED_CAROUSAL;

    @Override
    public void runOpMode() throws InterruptedException {

        //Servo Grabber = hardwareMap.get(Servo.class, "grabber");
        //Servo Grabber = hardwareMap.get(Servo.class, "purpledrop");

      /*  Grabber.scaleRange(0, 1);
        Grabber.setPosition(0.8); */

       /* TeamShippingElementDetector detector = new TeamShippingElementDetector(hardwareMap, telemetry, ROBOT_POSITION, true);
        //Detection continue to happen throughout init
        detector.startDetection();
        */

        while (opModeInInit()) {
            telemetry.addLine("gggg");
            //telemetry.addLine("Parking position is " + detector.getElementPosition());
            telemetry.update();
        }

       /* TeamPropDetector teamPropDetector = new TeamPropDetector(hardwareMap, telemetry);
        teamPropDetector.startDetection();
        while (opModeInInit()) {
            telemetry.addLine("TeamPropPosition: " + teamPropDetector.getTeamPropPosition());
            telemetry.update();
        } */

        SampleMecanumDrive drivetrain = new SampleMecanumDrive(hardwareMap);
        //drivetrain.setPoseEstimate(STARTING_POSITION);



        //String shippingElementPosition = detector.getElementPosition();
        String shippingElementPosition = "";
        //String shippingElementPosition = teamPropDetector.getTeamPropPosition();
        //telemetry.log().add("team shipping element position " + shippingElementPosition);
        shippingElementPosition = "CENTER";

        //teamPropDetector.startDetection();

        telemetry.addLine("BEFORE shippingElementPosition");

        TrajectorySequence trajSeq = null;

        if(shippingElementPosition == "CENTER") {

            telemetry.addLine("INSIDE IF CENTER");

             trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .forward(20)
                   // .addTemporalMarker(() -> Grabber.setPosition(0.2)) // Lower servo
                    //.waitSeconds(3)
                    .turn(Math.toRadians(90) + 1e-6) //clockwise
                    .forward(21)
                    .strafeRight(34)
                    .forward(10)
                    .build();




           // drivetrain.turn(Math.toRadians(-150));
            // Turns counter clockwise
            //drive.turn(Math.toRadians(180) + 1e-6);
            // Turns clockwise
            //drive.tu
            // rn(Math.toRadians(180) - 1e-6);

        } else if(shippingElementPosition == "LEFT") {

            telemetry.addLine("INSIDE ELSE IF LEFT");

             trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .forward(20)
                   // .addTemporalMarker(() -> Grabber.setPosition(0.2)) // drop pixel
                    .waitSeconds(3)
                    .turn(Math.toRadians(90) + 1e-6)
                    .forward(21)
                    .strafeRight(34)
                    .forward(10)
                    .build();


        } else {  //RIGHT
            telemetry.addLine("INSIDE ELSE RIGHT");

             trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .forward(20)
                    //.addTemporalMarker(() -> Grabber.setPosition(0.2)) // drop pixel
                    .waitSeconds(3)
                    .turn(Math.toRadians(90) + 1e-6)
                    .forward(21)
                    .strafeRight(34)
                    .forward(10)
                    .build();

        }

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start Autonomous");
        telemetry.update();
        waitForStart();

        drivetrain.followTrajectorySequence(trajSeq);


    }

    /*

    private static final boolean USE_WEBCAM = true;

    //private int spikeMarkPixelPosition = 1;

    public static Pose2d STARTING_POSITION = new Pose2d(37,-60, Math.toRadians(90));

    //Measurements for the CENTER TeamProp
    public static int STEP1_CENTER_FORWARD = 20;
    public static int STEP2_CENTER_FORWARD = 21;
    public static int STEP3_CENTER_RIGHT_STRAF = 34;
    public static int STEP4_CENTER_FORWARD = 10;

    //Measurements for the LEFT TeamProp
    public static int STEP1_LEFT_FORWARD = 22;
    public static int STEP1_LEFT_PIXEL_DROP_FORWARD = 8;
    public static int STEP2_LEFT_FORWARD = 23;
    public static int STEP3_LEFT_RIGHT_STRAF = 32;
    public static int STEP4_LEFT_FORWARD = 10;

    //Measurements for the RIGHT TeamProp
    public static int STEP1_RIGHT_RIGHT_STRAF = 20;
    public static int STEP2_RIGHT_FORWARD = 14;
    public static int STEP2_RIGHT_RIGHT_FORWARD =18;
    public static int STEP3_RIGHT_RIGHT_STRAF = 27;
    public static int STEP4_RIGHT_FORWARD = 10;

    //public static int STEP2_FORWARD = 10;
    //public static int STEP2_RIGHT_FORWARD = 26;

    public static int STEP1_RIGHT_STRAFE_RIGHT = 10;
    //public static int STEP1_RIGHT_FORWARD = 10;

    //public static int STEP1_STRAFE_RIGHT = 33;
    //public static int STEP2_FORWARD = 42;
    public static int STEP3_STRAFE_LEFT = 5;
    public static int STEP4_STRAFE_RIGHT = 2;
    public static int STEP5_FORWARD = 8;

    public static int PARKING_ONE_STRAFE_LEFT = 52;
    public static int PARKING_TWO_STRAFE_LEFT = 25;
    public static int PARKING_THREE_STRAFE_RIGHT = 2;

    public static int PARKING_BACK = 12;

    public static RobotPosition ROBOT_POSITION = RobotPosition.RED_CAROUSAL;

    @Override
    public void runOpMode() throws InterruptedException {

        Servo Grabber = hardwareMap.get(Servo.class, "grabber");
        Grabber.scaleRange(0, 1);
        Grabber.setPosition(0.8);

        TeamShippingElementDetector detector = new TeamShippingElementDetector(hardwareMap, telemetry, ROBOT_POSITION, true);
        //Detection continue to happen throughout init
        detector.startDetection();

        while (opModeInInit()) {
            telemetry.addLine("Parking position is " + detector.getElementPosition());
            telemetry.update();
        }


        SampleMecanumDrive drivetrain = new SampleMecanumDrive(hardwareMap);
        drivetrain.setPoseEstimate(STARTING_POSITION);

        Trajectory step1_forward = null;
        Trajectory step2_forward = null;
        Trajectory step2_pixel_forward = null;
        Trajectory step2_pixel_backward = null;
        Trajectory step2_right_forward = null;
        Trajectory step3_forward = null;
        Trajectory step4_forward = null;

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start Autonomous");
        telemetry.update();
        waitForStart();

        //Step-1 : Scan for duck or Team Shipping Element
        String shippingElementPosition = detector.getElementPosition();
        telemetry.log().add("team shipping element position " + shippingElementPosition);
        //shippingElementPosition = "LEFT";

        if(shippingElementPosition == "CENTER" ) {

            step1_forward = drivetrain.trajectoryBuilder(STARTING_POSITION).forward(STEP1_CENTER_FORWARD).build();
            step2_forward = drivetrain.trajectoryBuilder(step1_forward.end()).forward(STEP2_CENTER_FORWARD).build();
            step3_forward = drivetrain.trajectoryBuilder(step2_forward.end()).strafeRight(STEP3_CENTER_RIGHT_STRAF).build();
            step4_forward = drivetrain.trajectoryBuilder(step3_forward.end()).forward(STEP4_CENTER_FORWARD).build();

        }  else if(shippingElementPosition == "LEFT" ){
            step1_forward = drivetrain.trajectoryBuilder(STARTING_POSITION).forward(STEP1_LEFT_FORWARD).build();

            step2_pixel_forward = drivetrain.trajectoryBuilder(step1_forward.end()).forward(STEP1_LEFT_PIXEL_DROP_FORWARD).build();
            step2_pixel_backward = drivetrain.trajectoryBuilder(step2_pixel_forward.end()).back(STEP1_LEFT_PIXEL_DROP_FORWARD).build();

            step2_forward = drivetrain.trajectoryBuilder(step2_pixel_backward.end()).forward(STEP2_LEFT_FORWARD).build();
            step3_forward = drivetrain.trajectoryBuilder(step2_forward.end()).strafeRight(STEP3_LEFT_RIGHT_STRAF).build();
            step4_forward = drivetrain.trajectoryBuilder(step3_forward.end()).forward(STEP4_LEFT_FORWARD).build();

        } else{ //TeamProp on RIGHT PixelMark
            step1_forward = drivetrain.trajectoryBuilder(STARTING_POSITION).strafeRight(STEP1_RIGHT_RIGHT_STRAF).build();
            step2_right_forward = drivetrain.trajectoryBuilder(step1_forward.end()).forward(STEP2_RIGHT_FORWARD).build();
            step2_forward = drivetrain.trajectoryBuilder(step2_right_forward.end()).forward(STEP2_RIGHT_RIGHT_FORWARD).build();
            step3_forward = drivetrain.trajectoryBuilder(step2_forward.end()).strafeRight(STEP3_RIGHT_RIGHT_STRAF).build();
            step4_forward = drivetrain.trajectoryBuilder(step3_forward.end()).forward(STEP4_RIGHT_FORWARD).build();
        }



        //long start = System.currentTimeMillis();
        //As detection continue to happen since init, we can stop detection (stop streaming)
        detector.stopDetection();

        //Step 1 - Strafe Right
        drivetrain.followTrajectory(step1_forward);

        //Step 2 - Turn
        //drivetrain.followTrajectory(step2_forward);
        if(shippingElementPosition == "CENTER") {
            //Purple pixel drop code here
            Grabber.setPosition(0.2);
            drivetrain.turn(Math.toRadians(-150));

        } else if(shippingElementPosition == "LEFT")
        {
            drivetrain.turn(Math.toRadians(150));
            drivetrain.followTrajectory(step2_pixel_forward);
            //Purple pixel drop code here
            Grabber.setPosition(0.2);
            drivetrain.followTrajectory(step2_pixel_backward);
            drivetrain.turn(Math.toRadians(-295));

        } else {  //RIGHT

            drivetrain.followTrajectory(step2_right_forward);
            //Purple pixel drop code here
            Grabber.setPosition(0.2);
            drivetrain.turn(Math.toRadians(-150));

        }

        //Step 2 - Forward
        drivetrain.followTrajectory(step2_forward);

        //Step 3 - Forward
        drivetrain.followTrajectory(step3_forward);

        //Step 4 - Forward
        drivetrain.followTrajectory(step4_forward);


       // parkRobot(backdropPosition, drivetrain);


    }

     */




}
