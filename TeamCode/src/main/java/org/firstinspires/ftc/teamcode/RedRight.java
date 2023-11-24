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


@Autonomous(name = "Red Right")
@Config
public class RedRight extends AbstractAutonomusDrive {

    public static Pose2d STARTING_POSITION = new Pose2d(0, 0, 0);

    public static double angle = 95;

    public static double c1StrafeRight = 19;
    public static double c2Forward = 26.5;
    public static double c3Back = 7;
    public static double c4StrafeLeft = 12;
    public static double c5Forward = 30.5;
    public static double c6Back = 5;
    public static double c7StrafeRight = 63;
    public static double c8Forward = 12;

    public static double l1Forward = 25;
    public static double l2Forward = 5.5;
    public static double l3Back = 8;
    public static double l4StrafeLeft = 19;
    public static double l5Forward = 32;
    public static double l6Back = 5;
    public static double l7StrafeRight = 60;
    public static double l8Forward = 10;

    public static double r1StrafeRight = 26;
    public static double r2Forward = 20;
    public static double r3Back = 7;
    public static double r4StrafeLeft = 15;
    public static double r5Forward = 30;
    public static double r6Back = 5;
    public static double r7StrafeRight = 29;
    public static double r8Forward = 10;


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

       /* while (opModeInInit()) {
            telemetry.addLine("gggg");
            //telemetry.addLine("Parking position is " + detector.getElementPosition());
            telemetry.update();
        }*/

        telemetry.addLine("before initTfod");
        initTfod();
        telemetry.addLine("after initTfod");
        String teamPropPosition = "NOTFOUND";

        // while (opModeInInit() && teamPropPosition == "NOTFOUND"  ) {
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

       /* while (shippingElementPosition.equals("NOTFOUND") && opModeIsActive()) {

            telemetry.addLine("while opModeIsActive");
            telemetry.addLine("startDetection- teampropposition: " + teamPropDetector.startDetection());
            shippingElementPosition = teamPropDetector.startDetection();

        }*/

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
                    .turn(Math.toRadians(-95)) //clockwise
                    .strafeLeft(c4StrafeLeft)
                    .forward(c5Forward)
                    .addTemporalMarker(() -> slider.goUp()) // slider up
                    .waitSeconds(2)
                    .addTemporalMarker(() -> Grabber.setPosition(0.01)) // drop yellow pixel
                    .back(c6Back)
                    .addTemporalMarker(() -> slider.goToHome()) // slider up
                    .strafeRight(c7StrafeRight) //75
                    .forward(c8Forward)
                    .build();

        } else if(teamPropPosition == "LEFT") {

            telemetry.addLine("INSIDE ELSE IF LEFT");

            trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .forward(l1Forward)
                    .turn(Math.toRadians(angle)) //clockwise
                    .forward(l2Forward)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    .waitSeconds(1)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(1)) // up servo
                    .back(l3Back)
                    .turn(Math.toRadians(-angle)) //clockwise
                    .waitSeconds(0.5)
                    .turn(Math.toRadians(-angle)) //clockwise
                    .strafeLeft(l4StrafeLeft)
                    .forward(l5Forward)
                    .addTemporalMarker(() -> slider.goUp()) // slider up
                    .waitSeconds(2)
                    .addTemporalMarker(() -> Grabber.setPosition(0.01)) // drop yellow pixel
                    .back(l6Back)
                    .addTemporalMarker(() -> slider.goToHome()) // slider up
                    .strafeRight(l7StrafeRight)
                    .forward(l8Forward)
                    .build();


        } else {  //RIGHT
            telemetry.addLine("INSIDE ELSE RIGHT");

            trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .strafeRight(r1StrafeRight)
                    .forward(r2Forward)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    .waitSeconds(1)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(1)) // up servo
                    .back(r3Back)
                    .turn(Math.toRadians(-95)) //clockwise
                    .strafeLeft(r4StrafeLeft)
                    .forward(r5Forward)
                    .addTemporalMarker(() -> slider.goUp()) // slider up
                    .waitSeconds(2)
                    .addTemporalMarker(() -> Grabber.setPosition(0.01)) // drop yellow pixel
                    .back(r6Back)
                    .addTemporalMarker(() -> slider.goToHome()) // slider up
                    .strafeRight(r7StrafeRight)
                    .forward(r8Forward)
                    .build();
        }

        drivetrain.followTrajectorySequence(trajSeq);
    }


}
