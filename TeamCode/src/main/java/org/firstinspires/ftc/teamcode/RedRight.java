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

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.RobotPosition;
import org.firstinspires.ftc.teamcode.util.TeamShippingElementDetector;
import org.firstinspires.ftc.teamcode.util.TeamPropDetector;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.List;

@Autonomous(name = "Red Right")
@Config
public class RedRight extends LinearOpMode {

    //public static Pose2d STARTING_POSITION = new Pose2d(37,-60, Math.toRadians(90));
    public static Pose2d STARTING_POSITION = new Pose2d(0, 0, 0);
    public static RobotPosition ROBOT_POSITION = RobotPosition.RED_CAROUSAL;

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera


    private TfodProcessor tfod;
    private VisionPortal visionPortal;
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
                    .forward(28)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    .waitSeconds(3)
                    .back(5)
                    .turn(Math.toRadians(-150)) //clockwise
                    .forward(35)
                    // .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    //.waitSeconds(3)
                    .strafeRight(45)
                    .forward(12)
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
                    .forward(28)
                    .turn(Math.toRadians(150)) //clockwise
                    .forward(5)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    .waitSeconds(3)
                    .back(5)
                    .turn(Math.toRadians(-150)) //clockwise
                    .waitSeconds(2)
                    .turn(Math.toRadians(-150)) //clockwise
                    .forward(35)
                    // .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    //.waitSeconds(3)
                    .strafeRight(45)
                    .forward(12)
                    .build();



        } else {  //RIGHT
            telemetry.addLine("INSIDE ELSE RIGHT");

            trajSeq = drivetrain.trajectorySequenceBuilder(STARTING_POSITION)
                    .strafeRight(21)
                    .forward(22)
                    .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    .waitSeconds(3)
                    .back(5)
                    .turn(Math.toRadians(-150)) //clockwise
                    .forward(25)
                    // .addTemporalMarker(() -> PurpleDrop.setPosition(0.01)) // Lower servo
                    //.waitSeconds(3)
                    .strafeRight(40)
                    .forward(15)
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


    private void initTfod() {

        // Create the TensorFlow processor by using a builder.

        telemetry.addLine("starting initTfod");

        tfod = new TfodProcessor.Builder()

                // Use setModelAssetName() if the TF Model is built in as an asset.
                // Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                .setModelAssetName(TFOD_MODEL_FILE) //for default model
                // .setModelFileName(TFOD_MODEL_FILE)  //for custom mode
                .setModelLabels(LABELS)
                //.setIsModelTensorFlow2(true)
                //.setIsModelQuantized(true)
                //.setModelInputSize(300)
                .setModelAspectRatio(16.0 / 9.0)

                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        telemetry.addLine("after webcam");

        // Choose a camera resolution. Not all cameras support all resolutions.
        // builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        builder.enableCameraMonitoring(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        builder.setAutoStopLiveView(true);

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        telemetry.addLine(" builder.build()");

        // Set confidence threshold for TFOD recognitions, at any time.
        //tfod.setMinResultConfidence(0.75f);
        tfod.setMinResultConfidence(0.5f);


        // Disable or re-enable the TFOD processor at any time.
        visionPortal.setProcessorEnabled(tfod, true);

        telemetry.addLine("end initTfod");

    }   // end method initTfod()

    public String startDetection() {
        String teamPropPosition = "NOTFOUND";

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {

            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());


            if(recognition.getLabel() == "redprop" || recognition.getLabel() == "blueprop" ) {

                double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
                double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;
                double propHeight = (recognition.getBottom() - recognition.getTop());
                double propWidth =  (recognition.getRight() - recognition.getLeft());

                telemetry.addLine("propHeight:" +propHeight +", propWidth:" +propWidth);
                telemetry.addData("- Position", "%.0f / %.0f", x, y);

                if(propHeight > 75 && propHeight <125 && propWidth > 75 && propWidth <125) {

                    if (x < 200) {
                        teamPropPosition = "LEFT";

                    } else if (x >= 200 && x < 400) {
                        teamPropPosition = "CENTER";

                    } else {
                        teamPropPosition = "RIGHT";

                    }


                    break;
                }

            }

        }   // end for() loop

        telemetry.addLine("after for loop in getTeamPropPosition");
        return teamPropPosition;

    }

}
