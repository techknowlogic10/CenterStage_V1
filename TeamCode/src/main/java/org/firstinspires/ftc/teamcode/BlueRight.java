package org.firstinspires.ftc.teamcode;

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

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous(name = "Blue Right")
@Config
public class BlueRight extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;
    private int spikeMarkPixelPosition = 1;

    public static Pose2d STARTING_POSITION = new Pose2d(37,-60, Math.toRadians(90));

    //Measurements for the CENTER TeamProp
    public static int STEP1_CENTER_FORWARD = 18;
    public static int STEP2_CENTER_BACKWARD = 16;
    public static int STEP3_CENTER_RIGHT_FORWARD = 60;
    public static int STEP4_CENTER_RIGHT_STRAF = 30;
    public static int STEP4_CENTER_RIGHT_STRAF_2 = 30;
    public static int STEP4_CENTER_PARK = 10;
    public static int STEP4_CENTER_LEFT_STRAF = 20;
    public static int STEP5_CENTER_FORWARD_BACKDROP = 10;
    public static int STEP6_CENTER_BACKDROP_RIGHT_STRAF = 20;
    public static int STEP7_CENTER_PARK_FORWARD = 10;

    //Measurements for the LEFT TeamProp
    public static int STEP1_LEFT_STRAF = 20;
    public static int STEP2_LEFT_FORWARD = 12;
    public static int STEP3_LEFT_BACKWARD = 10;
    public static int STEP3_LEFT_RIGHT_FORWARD = 70;
    public static int STEP5_LEFT_LEFT_STRAF = 20;
    public static int STEP5_LEFT_FORWARD_BACKDROP = 10;
    public static int STEP6_LEFT_BACKDROP_RIGHT_STRAF = 20;
    public static int STEP7_LEFT_PARK_FORWARD = 10;


    //Measurements for the RIGHT TeamProp
    public static int STEP1_RIGHT_FORWARD = 18;
    public static int STEP2_RIGHT_PIXELMARK_FORWARD = 5;
    public static int STEP3_RIGHT_RIGHT_STRAF = 16;
    public static int STEP3_RIGHT_RIGHT_FORWARD = 60;
    public static int STEP5_RIGHT_LEFT_STRAF = 20;
    public static int STEP5_RIGHT_FORWARD_BACKDROP = 10;
    public static int STEP6_RIGHT_BACKDROP_RIGHT_STRAF = 20;
    public static int STEP7_RIGHT_PARK_FORWARD = 10;

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

    public static RobotPosition ROBOT_POSITION = RobotPosition.BLUE_WAREHOUSE;

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

        /*DcMotor Slider = hardwareMap.dcMotor.get("slider");
        //Slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Slider.setDirection(DcMotorSimple.Direction.REVERSE);*/

        Trajectory step1_straf = null;
        Trajectory step1_forward = null;
        Trajectory step2_backward = null;
        Trajectory step2_forward = null;
        Trajectory step2_right_forward = null;
        Trajectory step3_forward = null;
        Trajectory step3_backward = null;
        Trajectory step3_straf = null;
        Trajectory step4_left_straf = null;
        Trajectory step4_forward = null;
        Trajectory step5_forward = null;
        Trajectory step5_left_straf = null;
        Trajectory step6_forward = null;
        Trajectory step7_forward = null;
        Trajectory step7_right_straf = null;
        Trajectory step8_forward = null;

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start Autonomous");
        telemetry.update();
        waitForStart();

        //Step-1 : Scan for duck or Team Shipping Element
        String shippingElementPosition = detector.getElementPosition();
        telemetry.log().add("team shipping element position " + shippingElementPosition);
        //shippingElementPosition = "LEFT";


        if(shippingElementPosition == "CENTER") {
            step1_forward = drivetrain.trajectoryBuilder(STARTING_POSITION).forward(STEP1_CENTER_FORWARD).build();
           /* step2_backward = drivetrain.trajectoryBuilder(step1_forward.end()).back(STEP2_CENTER_BACKWARD).build();
            step3_forward = drivetrain.trajectoryBuilder(step2_backward.end()).forward(STEP3_CENTER_RIGHT_FORWARD).build();
            step4_left_straf = drivetrain.trajectoryBuilder(step3_forward.end()).strafeLeft(STEP4_CENTER_LEFT_STRAF).build();
            step5_forward = drivetrain.trajectoryBuilder(step4_left_straf.end()).strafeLeft(STEP5_CENTER_FORWARD_BACKDROP).build();
            step6_forward = drivetrain.trajectoryBuilder(step5_forward.end()).strafeLeft(STEP6_CENTER_BACKDROP_RIGHT_STRAF).build();
            step7_forward = drivetrain.trajectoryBuilder(step6_forward.end()).strafeLeft(STEP7_CENTER_PARK_FORWARD).build();

            */



        }  else if(shippingElementPosition == "LEFT"){

            step1_straf = drivetrain.trajectoryBuilder(STARTING_POSITION).strafeLeft(STEP1_LEFT_STRAF).build();
            step2_forward = drivetrain.trajectoryBuilder(step1_straf.end()).forward(STEP2_LEFT_FORWARD).build();
            /*step3_backward = drivetrain.trajectoryBuilder(step2_forward.end()).back(STEP3_LEFT_BACKWARD).build();
            step4_forward = drivetrain.trajectoryBuilder(step3_backward.end()).forward(STEP3_LEFT_RIGHT_FORWARD).build();
            step5_left_straf = drivetrain.trajectoryBuilder(step4_forward.end()).strafeLeft(STEP5_LEFT_LEFT_STRAF).build();
            step6_forward = drivetrain.trajectoryBuilder(step5_left_straf.end()).forward(STEP5_LEFT_FORWARD_BACKDROP).build();
            step7_right_straf = drivetrain.trajectoryBuilder(step6_forward.end()).strafeRight(STEP6_LEFT_BACKDROP_RIGHT_STRAF).build();
            step8_forward = drivetrain.trajectoryBuilder(step7_right_straf.end()).forward(STEP7_LEFT_PARK_FORWARD).build();

             */



        } else{ //TeamProp on RIGHT PixelMark

            step1_forward = drivetrain.trajectoryBuilder(STARTING_POSITION).strafeLeft(STEP1_RIGHT_FORWARD).build();
            step2_forward = drivetrain.trajectoryBuilder(step1_forward.end()).forward(STEP2_RIGHT_PIXELMARK_FORWARD).build();
            /*step3_straf = drivetrain.trajectoryBuilder(step2_forward.end()).back(STEP3_RIGHT_RIGHT_STRAF).build();
            step4_forward = drivetrain.trajectoryBuilder(step3_backward.end()).forward(STEP3_RIGHT_RIGHT_FORWARD).build();
            step5_left_straf = drivetrain.trajectoryBuilder(step4_forward.end()).strafeLeft(STEP5_RIGHT_LEFT_STRAF).build();
            step6_forward = drivetrain.trajectoryBuilder(step5_left_straf.end()).forward(STEP5_RIGHT_FORWARD_BACKDROP).build();
            step7_right_straf = drivetrain.trajectoryBuilder(step6_forward.end()).strafeRight(STEP6_RIGHT_BACKDROP_RIGHT_STRAF).build();
            step8_forward = drivetrain.trajectoryBuilder(step6_forward.end()).strafeLeft(STEP7_RIGHT_PARK_FORWARD).build();

             */


        }

        //long start = System.currentTimeMillis();
        //As detection continue to happen since init, we can stop detection (stop streaming)
        detector.stopDetection();

        //Step 2 - Turn
        //drivetrain.followTrajectory(step2_forward);
        if(shippingElementPosition == "CENTER") {

            //Step 1 - Strafe Right
            drivetrain.followTrajectory(step1_forward);

            //Purple pixel drop code here
            Grabber.setPosition(0.2);
            /*
            //Step 2 - Backward
            drivetrain.followTrajectory(step2_backward);
            drivetrain.turn(Math.toRadians(-135));
            //Step 3 - Forward
            drivetrain.followTrajectory(step3_forward);

            //Step 4 - Straf
            drivetrain.followTrajectory(step4_left_straf);

            //Step 5 - foward towards backfrop
            drivetrain.followTrajectory(step5_forward);

            //Step 6 - right straf towards parkinglot
            drivetrain.followTrajectory(step6_forward);

            //Step 7 - foward towards parkinglot
            drivetrain.followTrajectory(step7_forward);

             */


        } else if(shippingElementPosition == "LEFT") {
            //Step 1 - Strafe left
            drivetrain.followTrajectory(step1_straf);

            //Step 2 - Forward
            drivetrain.followTrajectory(step2_forward);

            //Purple pixel drop code here
            Grabber.setPosition(0.2);

            /*
            //Step 3 - Backward
            drivetrain.followTrajectory(step3_backward);
            drivetrain.turn(Math.toRadians(-135));


            //Step 4 - Straf
            drivetrain.followTrajectory(step4_forward);

            //Step 5 - left straf
            drivetrain.followTrajectory(step5_left_straf);

            //Step 6 - toward backdrop
            drivetrain.followTrajectory(step6_forward);

            //Step 7 - right straf towards parkinglot foward towards parkinglot
            drivetrain.followTrajectory(step7_right_straf);

            //Step 8 - toward parkinglot
            drivetrain.followTrajectory(step8_forward);

             */

        } else {  //RIGHT

            //Step 1 - forward
            drivetrain.followTrajectory(step1_forward);
            drivetrain.turn(Math.toRadians(135));

            //Step 2 - Forward
            drivetrain.followTrajectory(step2_forward);

            //Purple pixel drop code here
            Grabber.setPosition(0.2);

            /*
            //Step 3 - right strafe
            drivetrain.followTrajectory(step3_straf);

            //Step 4 - Straf
            drivetrain.followTrajectory(step4_forward);

            //Step 5 - left straf
            drivetrain.followTrajectory(step5_left_straf);

            //Step 6 - toward backdrop
            drivetrain.followTrajectory(step6_forward);

            //Step 7 - right straf towards parkinglot foward towards parkinglot
            drivetrain.followTrajectory(step7_right_straf);

            //Step 8 - toward parkinglot
            drivetrain.followTrajectory(step8_forward);

             */

        }


        // parkRobot(backdropPosition, drivetrain);


    }

  /*  private void parkRobot(int parkingPosition, SampleMecanumDrive drivetrain) {

        Trajectory park_strafe = null;

        if (parkingPosition == 1) {
            park_strafe = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate()).strafeLeft(PARKING_ONE_STRAFE_LEFT).build();
        } else if (parkingPosition == 2) {
            park_strafe = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate()).strafeLeft(PARKING_TWO_STRAFE_LEFT).build();
        } else {
            park_strafe = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate()).strafeRight(PARKING_THREE_STRAFE_RIGHT).build();
        }

        drivetrain.followTrajectory(park_strafe);

        Trajectory parkingBack = drivetrain.trajectoryBuilder(drivetrain.getPoseEstimate()).back(PARKING_BACK).build();
        drivetrain.followTrajectory(parkingBack);
    }*/


}
