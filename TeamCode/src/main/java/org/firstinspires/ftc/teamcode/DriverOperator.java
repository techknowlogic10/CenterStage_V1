package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp
public class DriverOperator extends OpMode {

    // Declare DC motors
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotor elevator = null;
    DcMotor slider = null;

    // Declare Servo motors
    Servo drone = null;
    Servo elbow = null;
    Servo grabber = null;
    Servo elevatorLock = null;
    Servo purpleDrop = null;
    Servo yellowDrop = null;

    double dronePos = 0;
    double elbowPos = 0.8 ;
    double grabberPos = 0.5;
    double elevatorLockPos = 0.5;
    double purpleDropPos = 0.9;
    double yellowDropPos = 0;

    double sliderSpeed = 0;
    double elevatorPower = 0;
    //double ElevatorLockPower = 0.5;
    double drivePower = 3;

    @Override
    public void init() {

        // Make sure your ID's match your configuration

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        backLeft = hardwareMap.dcMotor.get("backLeft");
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRight = hardwareMap.dcMotor.get("frontRight");
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        backRight = hardwareMap.dcMotor.get("backRight");
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        drone = hardwareMap.get(Servo.class, "drone");
        drone.scaleRange(0, 1);

        elbow = hardwareMap.get(Servo.class, "elbow");
        elbow.scaleRange(0, 1);
        elbow.setPosition(elbowPos);


       /* YellowDrop = hardwareMap.get(Servo.class, "yellowdrop");
        YellowDrop.scaleRange(0, 1);
        YellowDrop.setPosition(YellowDropPos);
        telemetry.addLine("intial YellowDrop  position: " +YellowDrop.getPosition());*/

        grabber = hardwareMap.get(Servo.class, "grabber");
        grabber.scaleRange(0, 1);
        telemetry.addLine("grabber current position: " +grabber.getPosition());
        grabber.setPosition(grabberPos);

        slider = hardwareMap.dcMotor.get("slider");
        slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slider.setDirection(DcMotorSimple.Direction.REVERSE);

        elevator = hardwareMap.dcMotor.get("elevator");
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevator.setDirection(DcMotorSimple.Direction.REVERSE);

        elevatorLock = hardwareMap.get(Servo.class, "elevatorlock");
        elevatorLock.scaleRange(0, 1);
        elevatorLock.setPosition(elevatorLockPos);
    }

    @Override
    public void loop() {

        drivePower = 2;
        /*if (gamepad1.right_trigger > 0.5){
            Drivepower = 2.5;
            //brake.goHome();
        }
        if (gamepad1.left_stick_button){
            Drivepower = 1.5;
        }*/

        double y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;
        double sliderspeed =  gamepad2.right_stick_y;

        telemetry.addLine("y: "+ y +" , x: "+ x + " , rx: +"+ rx);
        telemetry.addLine("sliderspeed: "+ sliderspeed);

        // Denominator is the largest motor power (absolute value) or 1
        // T+his ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), drivePower);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        telemetry.addLine("denominator: "+ denominator +" , frontLeftPower: "+ frontLeftPower + " , backLeftPower: +"+ backLeftPower +", frontRightPower: "+frontRightPower +" , backRightPower: "+backRightPower);

        double averageSpeed = (Math.abs(frontLeftPower)+Math.abs(backLeftPower)+Math.abs(frontRightPower)+Math.abs(backRightPower))/4;

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);

        //Slider up and down
        telemetry.addLine("sliderspeed before settong slider power: "+ sliderspeed);
        slider.setPower(sliderspeed);


        //Elbow up and down
        telemetry.addLine("before Elbow");

        if (gamepad2.dpad_up){
            elbowPos = 0.55;
            elbow.setPosition(elbowPos);
            telemetry.addLine("Elbow dpad_up position: " +elbow.getPosition());

        } else  if (gamepad2.dpad_down){
            elbowPos = 0.85;
            elbow.setPosition(elbowPos);
            telemetry.addLine(" Elbow dpad_down position: " +elbow.getPosition());
        }
        //grabber in take and open
        telemetry.addLine("before grabber");
        if (gamepad2.right_bumper){
            grabberPos = 0.9;
            grabber.setPosition(grabberPos);
            telemetry.addLine(" Grabber right_bumper position: " +grabber.getPosition());

        } else if (gamepad2.left_bumper){

            grabberPos = 0.15;
            grabber.setPosition(grabberPos);
            telemetry.addLine(" Grabber left_bumper position: " +grabber.getPosition());
        }


        //yellow drop to be commented
       /* telemetry.addLine("before yellow drop");
        if (gamepad2.dpad_left){
            yellowDropPos = 1;
            yellowDrop.setPosition(yellowDropPos);
            telemetry.addLine(" Yellowdrop gamepad2.dpad_left position: " +yellowDrop.getPosition());
        } else if (gamepad2.dpad_right){
            yellowDropPos = 0;
            yellowDrop.setPosition(YellowDropPos);
            telemetry.addLine("Yellowdrop gamepad2.dpad_right position: " +yellowDrop.getPosition());
        }*/

        //purple intake and drop
       /* telemetry.addLine("before purple drop");
        if (gamepad2.right_bumper){
            grabberPos = 0.8;
            telemetry.addLine("if rb grabber GrabberPos:" + grabberPos);
            grabber.setPosition(grabberPos);

        } else if (gamepad2.left_bumper){
            grabberPos = 0.2;
            telemetry.addLine(" else lb grabber GrabberPos:" + grabberPos);
            grabber.setPosition(grabberPos);
        }*/


        //launching drone
        if (gamepad1.left_bumper && gamepad1.right_bumper){
            dronePos = 0;
            drone.setPosition(dronePos);

        }/* else  if (gamepad1.dpad_right){
            dronePos = 0;
            drone.setPosition(dronePos);
        }*/

        //Elevator up and down
        elevatorPower = gamepad2.left_stick_y;
        elevator.setPower(-elevatorPower);

        //locking elevator
        telemetry.addLine("locking elevator");
        if (gamepad2.left_trigger > 0 && gamepad2.right_trigger > 0){

            elevatorLockPos = 1;
            telemetry.addLine("if..gamepad2.dpad_left:"+ elevatorLockPos);
            elevatorLock.setPosition(elevatorLockPos);

            boolean in_loop = true;

            while(in_loop){
                elevator.setPower(-0.5);
                in_loop = true;
            }


        } else if (gamepad2.dpad_right){
            elevatorLockPos = 0.7;
            telemetry.addLine("else..gamepad2.dpad_right:"+ elevatorLockPos);
            elevatorLock.setPosition(elevatorLockPos);
        }

        telemetry.update();

    }

}
