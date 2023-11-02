package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.RobotPosition;
import org.firstinspires.ftc.teamcode.util.TeamShippingElementDetector;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous(name = "Red Left")
@Config
public class RedLeft extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;
    private int spikeMarkPixelPosition = 1;

    public static Pose2d STARTING_POSITION = new Pose2d(37,-60, Math.toRadians(90));

    //Measurements for the CENTER TeamProp
    public static int STEP1_CENTER_FORWARD = 18;
    public static int STEP2_CENTER_FORWARD = 20;
    public static int STEP3_CENTER_RIGHT_STRAF = 40;
    public static int STEP4_CENTER_FORWARD = 10;

    //Measurements for the LEFT TeamProp
    public static int STEP1_LEFT_FORWARD = 18;
    public static int STEP2_LEFT_FORWARD = 20;
    public static int STEP3_LEFT_RIGHT_STRAF = 40;
    public static int STEP4_LEFT_FORWARD = 10;

    //Measurements for the LEFT TeamProp
    public static int STEP1_RIGHT_RIGHT_STRAF = 20;
    public static int STEP2_RIGHT_FORWARD = 12;
    public static int STEP2_RIGHT_RIGHT_FORWARD =15;
    public static int STEP3_RIGHT_RIGHT_STRAF = 15;
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

        TeamShippingElementDetector detector = new TeamShippingElementDetector(hardwareMap, telemetry, ROBOT_POSITION, true);
        //Detection continue to happen throughout init
        detector.startDetection();

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();

        long start = System.currentTimeMillis();
        //As detection continue to happen since init, we can stop detection (stop streaming)
        detector.stopDetection();

        //Step-1 : Scan for duck or Team Shipping Element
        String shippingElementPosition = detector.getElementPosition();
        telemetry.log().add("team shipping element position " + shippingElementPosition);

        int spikeMarkPixelLeft = 1;
        int spikeMarkPixelCenter = 2;
        int spikeMarkPixelRight = 3;

        SampleMecanumDrive drivetrain = new SampleMecanumDrive(hardwareMap);
        drivetrain.setPoseEstimate(STARTING_POSITION);

        /*DcMotor Slider = hardwareMap.dcMotor.get("slider");
        //Slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Slider.setDirection(DcMotorSimple.Direction.REVERSE);*/

       /* Grabber grabber = new Grabber(hardwareMap);
        grabber.pickup();*/

        Trajectory step1_forward = null;
        Trajectory step2_forward = null;
        Trajectory step2_right_forward = null;
        Trajectory step3_forward = null;
        Trajectory step4_forward = null;


        if(shippingElementPosition.equals("CENTER")) {
            step1_forward = drivetrain.trajectoryBuilder(STARTING_POSITION).forward(STEP1_CENTER_FORWARD).build();
            step2_forward = drivetrain.trajectoryBuilder(step1_forward.end()).forward(STEP2_CENTER_FORWARD).build();
            step3_forward = drivetrain.trajectoryBuilder(step2_forward.end()).strafeRight(STEP3_CENTER_RIGHT_STRAF).build();
            step4_forward = drivetrain.trajectoryBuilder(step3_forward.end()).forward(STEP4_CENTER_FORWARD).build();

        }  else if(shippingElementPosition.equals("LEFT")){
            step1_forward = drivetrain.trajectoryBuilder(STARTING_POSITION).forward(STEP1_LEFT_FORWARD).build();
            step2_forward = drivetrain.trajectoryBuilder(step1_forward.end()).forward(STEP2_LEFT_FORWARD).build();
            step3_forward = drivetrain.trajectoryBuilder(step2_forward.end()).strafeRight(STEP3_LEFT_RIGHT_STRAF).build();
            step4_forward = drivetrain.trajectoryBuilder(step3_forward.end()).forward(STEP4_LEFT_FORWARD).build();

        } else{ //TeamProp on RIGHT PixelMark
            step1_forward = drivetrain.trajectoryBuilder(STARTING_POSITION).strafeRight(STEP1_RIGHT_RIGHT_STRAF).build();
            step2_right_forward = drivetrain.trajectoryBuilder(step1_forward.end()).forward(STEP2_RIGHT_FORWARD).build();
            step2_forward = drivetrain.trajectoryBuilder(step2_right_forward.end()).forward(STEP2_RIGHT_RIGHT_FORWARD).build();
            step3_forward = drivetrain.trajectoryBuilder(step2_forward.end()).strafeRight(STEP3_RIGHT_RIGHT_STRAF).build();
            step4_forward = drivetrain.trajectoryBuilder(step3_forward.end()).forward(STEP4_RIGHT_FORWARD).build();
        }

        //Step 1 - Strafe Right
        drivetrain.followTrajectory(step1_forward);

        //Step 2 - Turn
        //drivetrain.followTrajectory(step2_forward);
        if(shippingElementPosition.equals("CENTER")) {
            //Purple pixel drop code here
            drivetrain.turn(Math.toRadians(-135));

        } else if(shippingElementPosition.equals("LEFT")) {
            drivetrain.turn(Math.toRadians(135));
            //Purple pixel drop code here

            drivetrain.turn(Math.toRadians(-270));

        } else {  //RIGHT

            drivetrain.followTrajectory(step2_right_forward);
            //Purple pixel drop code here

            drivetrain.turn(Math.toRadians(-135));

        }

        //Step 2 - Forward
        drivetrain.followTrajectory(step2_forward);

        //Step 3 - Forward
        drivetrain.followTrajectory(step3_forward);

        //Step 4 - Forward
        drivetrain.followTrajectory(step4_forward);


        // parkRobot(backdropPosition, drivetrain);


    }

    private void parkRobot(int parkingPosition, SampleMecanumDrive drivetrain) {

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
    }


}
