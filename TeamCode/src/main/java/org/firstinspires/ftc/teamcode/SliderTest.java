package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class SliderTest extends OpMode {

    Servo Grabber = null;
    double GrabberPos = 0;

    @Override
    public void init() {

        Grabber = hardwareMap.get(Servo.class, "yellowdrop");
        Grabber.scaleRange(0, 1);
        Grabber.setPosition(GrabberPos);
    }

    @Override
    public void loop() {

        telemetry.addLine("before grabber");
        if (gamepad2.right_bumper){
            GrabberPos = 0.01;
            telemetry.addLine("if rb grabber GrabberPos:" + GrabberPos);
            Grabber.setPosition(GrabberPos);

        } else if (gamepad2.left_bumper){
            GrabberPos = 1;
            telemetry.addLine(" else lb grabber GrabberPos:" + GrabberPos);
            Grabber.setPosition(GrabberPos);
        }



        telemetry.update();


        // run until the end of the match (driver presses STOP)
       /* double tgtPower = 0;
        while (opModeIsActive()) {
            tgtPower = -this.gamepad1.left_stick_y;
            motorTest.setPower(tgtPower);
            // check to see if we need to move the servo.
            if (gamepad1.y) {
                // move to 0 degrees.
                servoTest.setPosition(0);
            } else if (gamepad1.x || gamepad1.b) {
                // move to 90 degrees.
                servoTest.setPosition(0.5);
            } else if (gamepad1.a) {
                // move to 180 degrees.
                servoTest.setPosition(1);
            }
            telemetry.addData("Servo Position", servoTest.getPosition());
            telemetry.addData("Target Power", tgtPower);
            telemetry.addData("Motor Power", motorTest.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();

        }

        */

    }
}
