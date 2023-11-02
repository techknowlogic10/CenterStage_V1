package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp
public class DriverOperator extends OpMode {

    // Declare Motors
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    //double drivePower = 4.0;
    double drivePower = 2.0;

    @Override
    public void init() {

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRight = hardwareMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        backLeft = hardwareMap.dcMotor.get("backLeft");

        backRight = hardwareMap.dcMotor.get("backRight");
    }

    @Override
    public void loop() {

        /*double y = gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x * -1.1; //-1.1;
        double rx = -gamepad1.right_stick_x;*/
        double y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;

        telemetry.addLine("x:" + x+ "y:"+y + "rx:"+rx);

        double denominator = Math.max(Math.abs(y) + Math.abs(x)+ Math.abs(rx), drivePower);
        telemetry.addLine("denominator:"+denominator);

        double frontLeftPower = (y + x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;
        telemetry.addLine("frontLeftPower:" + frontLeftPower+ "frontRightPower:"+frontRightPower + "backLeftPower:"+backLeftPower+ "frontRightPower:"+frontRightPower);

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        telemetry.update();

    }
}