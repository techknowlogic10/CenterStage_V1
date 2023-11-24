package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

public abstract class AbstractAutonomusDrive extends LinearOpMode {

    protected TfodProcessor tfod;
    //private static final String TFOD_MODEL_FILE = "TechKNOWLogic_Centerstage.tflite";
    private static final String TFOD_MODEL_FILE = "Centerstage_15004.tflite";
    private static final String[] LABELS = { "blueprop","redprop"};

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    protected VisionPortal visionPortal;

    protected void initTfod() {

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
        .setModelAspectRatio(16.0 / 8.0)

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
        tfod.setMinResultConfidence(0.65f);

        // Disable or re-enable the TFOD processor at any time.
        visionPortal.setProcessorEnabled(tfod, true);

        telemetry.addLine("end initTfod");

    }   // end method initTfod()

    protected String startDetection() {
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

                if(propHeight > 70 && propHeight < 140 && propWidth > 70 && propWidth < 140) {

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
