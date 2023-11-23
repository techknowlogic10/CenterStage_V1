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


@Autonomous(name = "Blue Left")
@Config
public class BlueLeft extends AbstractAutonomusDrive {

    //public static Pose2d STARTING_POSITION = new Pose2d(37,-60, Math.toRadians(90));
    public static Pose2d STARTING_POSITION = new Pose2d(0, 0, 0);
    public static RobotPosition ROBOT_POSITION = RobotPosition.BLUE_CAROUSAL;

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera


    //private TfodProcessor tfod;
    //private VisionPortal visionPortal;
    //private static final String TFOD_MODEL_FILE = "testmodel.tflite";
    //private static final String TFOD_MODEL_FILE = "CenterStage.tflite";
    private static final String TFOD_MODEL_FILE = "TechKNOWLogic_Centerstage.tflite";
    //private static final String[] LABELS = { "testmodel", };
    private static final String[] LABELS = { "blueprop","redprop"};
    //private static final String[] LABELS = { "Pixel",};

    public static double angle = 95;

    public static double c1strafeleft = 35;
    public static double c2forward = 30;
    public static double c3back = 7;
    public static double c4strafeleft = 14;
    public static double c5forward = 34.5;
    public static double c6back = 5;
    public static double c7strafeleft = 42;
    public static double c8forward = 10;

    public static double l1strafeleft = 35;
    public static double l2forward = 17;
    public static double l3back = 7;
    public static double l4forward = 20;
    public static double l5back = 7;
    public static double l6strafeleft = 29;
    public static double l7forward = 10;

    public static double r1forward = 24;
    public static double r2forward = 5;
    public static double r3back = 8;
    public static double r4strafeleft = 25;
    public static double r5forward = 28;
    public static double r6back = 5;
    public static double r7strafeleft = 50;
    public static double r8forward = 10;



    @Override
    public void runOpMode() throws InterruptedException {

        //Servo Grabber = hardwareMap.get(Servo.class, "grabber");
        Servo PurpleDrop = hardwareMap.get(Servo.class, "purpledrop");
        //PurpleDrop.scaleRange(0.01, 0.4);
        //PurpleDrop.setPosition(0.4);

        //Servo YellowDrop = hardwareMap.get(Servo.class, "yellowdrop");


        Servo Elbow = hardwareMap.get(Servo.class, "elbow");
        // Elbow.setPosition(0.8);
        Elbow.setPosition(0.55);

        Servo Grabber = hardwareMap.get(Servo.class, "grabber");
        Grabber.setPosition(0.4);

        /*DcMotor Slider = hardwareMap.dcMotor.get("slider");
        Slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Slider.setDirection(DcMotorSimple.Direction.REVERSE);*/
        Slider slider = new Slider(hardwareMap);

      /*Grabber.scaleRange(0, 1);
        Grabber.setPosition(0.8); */

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
        String shippingElementPosition = "NOTFOUND";

        // TeamPropDetector teamPropDetector = new TeamPropDetector(hardwareMap, telemetry);
        //  String shippingElementPosition = "NOTFOUND";

        //while (opModeInInit() && shippingElementPosition == "NOTFOUND"  ) {
        while (opModeInInit()) {


            //telemetry.addLine("while opModeIsActive");

            //telemetryTfod();
            shippingElementPosition = startDetection();
            telemetry.addLine("Position:"+shippingElementPosition);


            // Push telemetry to the Driver Station.
            telemetry.update();

               /* // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }*/

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



       /* while (shippingElementPosition.equals("NOTFOUND") && opModeIsActive()) {

            telemetry.addLine("while opModeIsActive");
            telemetry.addLine("startDetection- teampropposition: " + teamPropDetector.startDetection());
            shippingElementPosition = teamPropDetector.startDetection();

        }*/
        //teamPropDetector.startDetection();
       /* while (opModeInInit()) {
            telemetry.addLine("startDetection- teampropposition: " + teamPropDetector.startDetection());
            telemetry.update();
        }*/


        //String shippingElementPosition = detector.getElementPosition();
        //telemetry.log().add("team shipping element position " + shippingElementPosition);
        // String shippingElementPosition = "CENTER";
        //teamPropDetector.startDetection();


        SampleMecanumDrive drivetrain = new SampleMecanumDrive(hardwareMap);

        TrajectorySequence trajSeq = null;

        if(shippingElementPosition == "CENTER") {

            telemetry.addLine("INSIDE IF CENTER");


            trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .strafeLeft(c1strafeleft)
                    .forward(c2forward)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    .waitSeconds(1)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(1)) // up servo
                    .back(c3back)
                    .turn(Math.toRadians(angle)) //clockwise
                    .strafeLeft(c4strafeleft)
                    .forward(c5forward)
                   // .waitSeconds(1)
                    .addTemporalMarker(() -> slider.goUp()) // slider up
                    .waitSeconds(2)
                    .addTemporalMarker(() -> Grabber.setPosition(0.01)) // drop yellow pixel
                    //.addTemporalMarker(() -> YellowDrop.setPosition(1)) // drop yellow pixel
                   // .waitSeconds(1)
                    // .addTemporalMarker(() -> YellowDrop.setPosition(0.01)) // up servo
                    .back(c6back)
                    .addTemporalMarker(() -> slider.goToHome()) // slider up
                   // .waitSeconds(1)
                    .strafeLeft(c7strafeleft)
                    .forward(c8forward)
                    .build();

        } else if(shippingElementPosition == "LEFT") {

            telemetry.addLine("INSIDE ELSE LEFT");

            trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .strafeLeft(l1strafeleft)
                    .forward(l2forward)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    .waitSeconds(1)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(1)) // up servo
                    .back(l3back)
                    .turn(Math.toRadians(angle)) //clockwise
                    //.strafeLeft(10)
                    .forward(l4forward)
                    .addTemporalMarker(() -> slider.goUp()) // slider up
                    .waitSeconds(2)
                    .addTemporalMarker(() -> Grabber.setPosition(0.01)) // drop yellow pixel
                    .back(l5back)
                    .addTemporalMarker(() -> slider.goToHome()) // slider up
                   // .waitSeconds(1)
                    .strafeLeft(l6strafeleft)
                    .forward(l7forward)
                    .build();


        } else {  //RIGHT

            telemetry.addLine("INSIDE ELSE IF RIGHT");


            trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .forward(r1forward)
                    .turn(Math.toRadians(-angle)) //clockwise
                    .forward(r2forward)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    .waitSeconds(1)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(1)) // up servo
                    .back(r3back)
                    .turn(Math.toRadians(angle)) //clockwise
                    .waitSeconds(0.5)
                    .turn(Math.toRadians(angle)) //clockwise
                    .strafeLeft(r4strafeleft)
                    .forward(r5forward)
                    .addTemporalMarker(() -> slider.goUp()) // slider up
                    .waitSeconds(2)
                    .addTemporalMarker(() -> Grabber.setPosition(0.01)) // drop yellow pixel
                    //.addTemporalMarker(() -> YellowDrop.setPosition(1)) // drop yellow pixel
                    //.waitSeconds(1)
                    // .addTemporalMarker(() -> YellowDrop.setPosition(0.01)) // up servo
                    .back(r6back)
                    .addTemporalMarker(() -> slider.goToHome()) // slider up
                   // .waitSeconds(1)
                    .strafeLeft(r7strafeleft)
                    .forward(r8forward)
                    .build();



        }

        drivetrain.followTrajectorySequence(trajSeq);



    }

    /*

    private static final boolean USE_WEBCAM = true;

    //private int spikeMarkPixelPosition = 1;

    public static Pose2d STARTING_POSITION = new Pose2d(37,-60, Math.toRadians(90));

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

     */

}
