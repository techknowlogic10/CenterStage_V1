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

    //public static Pose2d STARTING_POSITION = new Pose2d(37,-60, Math.toRadians(90));
    public static Pose2d STARTING_POSITION = new Pose2d(0, 0, 0);
    public static RobotPosition ROBOT_POSITION = RobotPosition.RED_CAROUSAL;

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera


    //private TfodProcessor tfod;
    //private VisionPortal visionPortal;
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

            while (opModeInInit() && shippingElementPosition == "NOTFOUND"  ) {

                telemetry.addLine("while opModeIsActive");

                //telemetryTfod();
                shippingElementPosition = startDetection();


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
                    .forward(26)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    //.waitSeconds(1)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(1)) // up servo
                    .back(7)
                    .turn(Math.toRadians(-150)) //clockwise
                    .strafeLeft(14)
                    .forward(32)
                    .waitSeconds(1)
                    .addTemporalMarker(() -> slider.goUp()) // slider up
                    .waitSeconds(2)
                    .addTemporalMarker(() -> Grabber.setPosition(0.01)) // drop yellow pixel
                    //.addTemporalMarker(() -> YellowDrop.setPosition(1)) // drop yellow pixel
                    .waitSeconds(1)
                   // .addTemporalMarker(() -> YellowDrop.setPosition(0.01)) // up servo
                    .back(5)
                    //.waitSeconds(1)
                    .strafeRight(42)
                    .forward(13)
                    .build();

        } else if(shippingElementPosition == "LEFT") {

            telemetry.addLine("INSIDE ELSE IF LEFT");

            trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .forward(24)
                    .turn(Math.toRadians(150)) //clockwise
                    .forward(5)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                   // .waitSeconds(1)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(1)) // up servo
                    .back(8)
                    .turn(Math.toRadians(-150)) //clockwise
                    .waitSeconds(1)
                    .turn(Math.toRadians(-150)) //clockwise
                    .strafeLeft(25)
                    .forward(32)
                    .addTemporalMarker(() -> slider.goUp()) // slider up
                    .waitSeconds(2)
                    .addTemporalMarker(() -> Grabber.setPosition(0.01)) // drop yellow pixel
                    //.addTemporalMarker(() -> YellowDrop.setPosition(1)) // drop yellow pixel
                    .waitSeconds(1)
                    // .addTemporalMarker(() -> YellowDrop.setPosition(0.01)) // up servo
                    .back(5)
                    .waitSeconds(1)
                    .strafeRight(50)
                    .forward(13)
                    .build();


        } else {  //RIGHT
            telemetry.addLine("INSIDE ELSE RIGHT");

            trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .strafeRight(18)
                    .forward(16)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    //.waitSeconds(1)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(1)) // up servo
                    .back(7)
                    .turn(Math.toRadians(-150)) //clockwise
                    .strafeLeft(10)
                    .forward(25)
                    .addTemporalMarker(() -> slider.goUp()) // slider up
                    .waitSeconds(2)
                    .addTemporalMarker(() -> Grabber.setPosition(0.01)) // drop yellow pixel
                    //.addTemporalMarker(() -> YellowDrop.setPosition(1)) // drop yellow pixel
                    .waitSeconds(1)
                    // .addTemporalMarker(() -> YellowDrop.setPosition(0.01)) // up servo
                    .back(5)
                    //.waitSeconds(1)
                    .strafeRight(29)
                    .forward(12)
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
