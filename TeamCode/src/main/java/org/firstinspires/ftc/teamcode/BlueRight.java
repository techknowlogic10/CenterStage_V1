package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.RobotPosition;

import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous(name = "Blue Right")
@Config
public class BlueRight extends AbstractAutonomusDrive {

    public static Pose2d STARTING_POSITION = new Pose2d(0, 0, 0);
    public static double angle = 95;

    public static double c1StrafeRight = 35;
    public static double c2Forward = 30;
    public static double c3Back = 7;
    public static double c4StrafeRight = 25;
    public static double c5Forward = 15;
    public static double c6Forward = 75;
    public static double c7StrafeLeft = 40;
    public static double c8Back = 5;
    public static double c9StrafeLeft = 42;
    public static double c10Forward = 10;

    public static double l1Forward = 24;
    public static double l2Forward = 5;
    public static double l3Back = 7;
    public static double l4StrafeRight = 25;
    public static double l5Forward = 75;
    public static double l6StrafeLeft = 40;
    public static double l7Back = 5;
    public static double l8StrafeRight = 42;
    public static double l9Forward = 10;

    public static double r1StrafeRight = 24;
    public static double r2Forward = 20;
    public static double r3Back = 7;
    public static double r4StrafeLeft = 25;
    public static double r5Forward = 20;
    public static double r6Forward = 75;
    public static double r7StrafeLeft = 40;
    public static double r8Back = 5;
    public static double r9StrafeRight = 50;
    public static double r10Forward = 10;

    @Override
    public void runOpMode() throws InterruptedException {

        Servo PurpleDrop = hardwareMap.get(Servo.class, "purpledrop");

        Servo Elbow = hardwareMap.get(Servo.class, "elbow");
        Elbow.setPosition(0.55);

        Servo Grabber = hardwareMap.get(Servo.class, "grabber");
        Grabber.setPosition(0.4);

        /*DcMotor Slider = hardwareMap.dcMotor.get("slider");
        Slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Slider.setDirection(DcMotorSimple.Direction.REVERSE);*/
        Slider slider = new Slider(hardwareMap);

       /* TeamShippingElementDetector detector = new TeamShippingElementDetector(hardwareMap, telemetry, ROBOT_POSITION, true);
        //Detection continue to happen throughout init
        detector.startDetection();
        */

        telemetry.addLine("before initTfod");
        initTfod();
        telemetry.addLine("after initTfod");
        String teamPropPosition = "NOTFOUND";

        //while (opModeInInit() && teamPropPosition == "NOTFOUND"  ) {
        while (opModeInInit()) {

            teamPropPosition = startDetection();
            telemetry.addLine("Position:"+teamPropPosition);

            // Push telemetry to the Driver Station.
            telemetry.update();

            // Share the CPU.
            sleep(20);
        }
        telemetry.addLine("BEFORE teamPropPosition:" +teamPropPosition);

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start Autonomous");
        telemetry.update();
        waitForStart();

        //visionPortal.stopStreaming();
        visionPortal.close();

        SampleMecanumDrive drivetrain = new SampleMecanumDrive(hardwareMap);
        TrajectorySequence trajSeq = null;

        if(teamPropPosition == "CENTER") {

            telemetry.addLine("INSIDE IF CENTER");

            trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .strafeRight(c1StrafeRight)
                    .forward(c2Forward)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    .waitSeconds(1)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(1)) // up servo
                    .back(c3Back)
                    .strafeRight(c4StrafeRight)
                    .forward(c5Forward)
                    .turn(Math.toRadians(angle)) //anticlockwise
                    .forward(c6Forward)
                    .strafeLeft(c7StrafeLeft)
                    .addTemporalMarker(() -> slider.goUp()) // slider up
                    .waitSeconds(2)
                    .addTemporalMarker(() -> Grabber.setPosition(0.01)) // drop yellow pixel
                    .back(c8Back)
                    .strafeRight(c9StrafeLeft)
                    .forward(c10Forward)
                    .build();

        } else if(teamPropPosition == "LEFT") {

            telemetry.addLine("INSIDE ELSE LEFT");

            trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .forward(l1Forward)
                    .turn(Math.toRadians(angle)) //clockwise
                    .forward(l2Forward)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    .waitSeconds(1)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(1)) // up servo
                    .back(l3Back)
                    .strafeRight(l4StrafeRight)
                    .forward(l5Forward)
                    .strafeLeft(l6StrafeLeft)
                    .addTemporalMarker(() -> slider.goUp()) // slider up
                    .waitSeconds(2)
                    .addTemporalMarker(() -> Grabber.setPosition(0.01)) // drop yellow pixel
                    .back(l7Back)
                    .strafeRight(l8StrafeRight)
                    .forward(l9Forward)
                    .build();

        } else {  //RIGHT

            telemetry.addLine("INSIDE ELSE IF RIGHT");

            trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .strafeRight(r1StrafeRight)
                    .forward(r2Forward)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    .waitSeconds(1)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(1)) // up servo
                    .back(r3Back)
                    .strafeLeft(r4StrafeLeft)
                    .forward(r5Forward)
                    .turn(Math.toRadians(angle)) //anticlockwise
                    .forward(r6Forward)
                    .strafeLeft(r7StrafeLeft)
                    .addTemporalMarker(() -> slider.goUp()) // slider up
                    .waitSeconds(2)
                    .addTemporalMarker(() -> Grabber.setPosition(0.01)) // drop yellow pixel
                    .back(r8Back)
                    .strafeRight(r9StrafeRight)
                    .forward(r10Forward)
                    .build();
        }

        drivetrain.followTrajectorySequence(trajSeq);
    }

}
