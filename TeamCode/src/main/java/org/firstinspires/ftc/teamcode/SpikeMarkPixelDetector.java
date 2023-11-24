package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.io.File;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class SpikeMarkPixelDetector {

    private HardwareMap hardwareMap = null;
    private Telemetry telemetry = null;

    private TfodProcessor tfod = null;
    //private VisionPortal visionPortal = null;
    VisionPortal.Builder builder = null;
    private VisionPortal visionPortal;

  //  private static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/CenterStage.tflite";
  private static final String TFOD_MODEL_ASSET = "CenterStage.tflite";
  //private static final String TFOD_MODEL_ASSET = "/sdcard/FIRST/src/main/assets/CenterStage.tflite";
  //private static final String TFOD_MODEL_ASSET = "/Internal shared storage/FIRST/assets/CenterStage.tflite";


    // Define the labels recognized in the model for TFOD (must be in training order!)
    private static final String[] LABELS = {
            "Pixel"   };



    public SpikeMarkPixelDetector(HardwareMap hardwareMap, Telemetry telemetry, boolean USE_WEBCAM) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        telemetry.addLine("Enter Constructor");

        //initTfod(USE_WEBCAM);

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()
                .setModelAssetName(TFOD_MODEL_ASSET)
                .setModelLabels(LABELS)
                .build();


        // Indicate that only the zoomed center area of each
        // image will be passed to the TensorFlow object
        // detector. For no zooming, set magnification to 1.0.
        //tfod.setZoom(2.0);


        // Create the vision portal by using a builder.
        builder = new VisionPortal.Builder();

        telemetry.addLine("Before setting Camera");

        // Create the vision portal the easy way.
       // if (USE_WEBCAM) {
       // if (true) {
           // visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "SpikeMarkWebcam"), tfod);
           builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
            telemetry.addLine("builder. setting Camera");

        /*} else {
            //visionPortal = VisionPortal.easyCreateWithDefaults(BuiltinCameraDirection.BACK, tfod);
            builder.setCamera(BuiltinCameraDirection.BACK);

        }*/
        telemetry.addLine("before add processor");
        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();
        telemetry.addLine("builder.build");



    }

    /**
     * Initialize the TensorFlow Object Detection processor.
     */
   /* private void initTfod(boolean USE_WEBCAM) {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                // Use setModelAssetName() if the TF Model is built in as an asset.
                // Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                .setModelAssetName(TFOD_MODEL_ASSET)
                //.setModelFileName(TFOD_MODEL_FILE)

                .setModelLabels(LABELS)
                //.setIsModelTensorFlow2(true)
                //.setIsModelQuantized(true)
                //.setModelInputSize(300)
                //.setModelAspectRatio(16.0 / 9.0)

                .build();

        // Create the vision portal by using a builder.
        builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "SpikeMarkPixelWebcam"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableCameraMonitoring(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        //tfod.setMinResultConfidence(0.75f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()

*/


    public void stopDetection() {
        visionPortal.stopStreaming();
    }

    public int startDetection() {

        int spikeMarkPosition = 1;
        int count=0;
        int spikeMarkPixelLeft = 1;
        int spikeMarkPixelCenter = 2;
        int spikeMarkPixelRight = 3;
        double PIXEL_X = 0;
        double PIXEL_Y = 0;
        boolean isPropRecognised = false;

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addLine("# Objects Detected :" + currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            count++;
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;
            double y = (recognition.getTop()  + recognition.getBottom()) / 2 ;

            PIXEL_X = x;
            PIXEL_Y = y;

            telemetry.addData(""," ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());

            if(recognition.getLabel().equals("Pixel")){
                isPropRecognised = true;
            }

            if (isPropRecognised) {
                break;
            }

            if(count == 3) break;

        }   // end for() loop

        //visionPortal.stopStreaming();
        //
        // visionPortal.close();


        //determine a Spike Pixel Position
        if(PIXEL_Y > 2) {
            spikeMarkPosition =  spikeMarkPixelCenter;
        } else if(PIXEL_X < 2 ) {
            spikeMarkPosition =  spikeMarkPixelLeft;
        } else {
            spikeMarkPosition =  spikeMarkPixelRight;
        }

        // Push telemetry to the Driver Station.
        //telemetry.update();

        return spikeMarkPosition;
    }

}

