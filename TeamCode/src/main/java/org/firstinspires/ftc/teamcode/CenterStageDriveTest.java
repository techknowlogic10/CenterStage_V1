package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;


@TeleOp
@Disabled
public class CenterStageDriveTest extends OpMode {

    // Declare our motors
    // Make sure your ID's match your configuration
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotor Elevator = null;
    DcMotor Slider = null;

    Servo Drone = null;
    Servo BackDrop = null;
    Servo Grabber = null;
    Servo ElevatorLock = null;


    double DronePos = 0;
    double BackDropPos = 0.8;
    double GrabberPos = 0;

    double SliderSpeed = 0;
    double ElevatorPower = 0;
    double ElevatorLockPower = 0;
    double Drivepower = 2;


    @Override
    public void init() {
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

        Drone = hardwareMap.get(Servo.class, "drone");
        Drone.scaleRange(0, 1);

        BackDrop = hardwareMap.get(Servo.class, "backdrop");
        BackDrop.scaleRange(0, 1);
        BackDrop.setPosition(BackDropPos);

        Grabber = hardwareMap.get(Servo.class, "grabber");
        Grabber.scaleRange(0, 1);

        Slider = hardwareMap.dcMotor.get("slider");
        Slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Slider.setDirection(DcMotorSimple.Direction.REVERSE);

        Elevator = hardwareMap.dcMotor.get("elevator");
        Elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Elevator.setDirection(DcMotorSimple.Direction.REVERSE);

        ElevatorLock = hardwareMap.get(Servo.class, "elevatorlock");
        ElevatorLock.scaleRange(0, 1);
        ElevatorLock.setPosition(ElevatorLockPower);


    }

    @Override
    public void loop() {



        Drivepower = 4;
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
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), Drivepower);
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

        telemetry.addLine("sliderspeed before settong slider power: "+ sliderspeed);
        Slider.setPower(sliderspeed);

        DronePos = 0;
        GrabberPos =0.5;
        BackDropPos = 0.5;
        telemetry.addLine("DronePos: "+ DronePos);

        if (gamepad1.right_bumper){
            GrabberPos = 1;
            Grabber.setPosition(GrabberPos);

        } else  if (gamepad1.left_bumper){
            GrabberPos = 0.5;
            Grabber.setPosition(GrabberPos);
        }

        if (gamepad1.dpad_left){
            DronePos = 1;
            Drone.setPosition(DronePos);

        } else  if (gamepad1.dpad_right){
            DronePos = 0;
            Drone.setPosition(DronePos);
        }

        if (gamepad2.dpad_up){
            BackDropPos = 1;
            BackDrop.setPosition(BackDropPos);

        } else  if (gamepad2.dpad_down){
            BackDropPos = 0.6;
            BackDrop.setPosition(BackDropPos);
        }

        //locking elevator
        if (gamepad2.right_bumper){
            ElevatorLockPower = 1;
            ElevatorLock.setPosition(ElevatorLockPower);

        } else  if (gamepad2.left_bumper){
            ElevatorLockPower = 0.5;
            ElevatorLock.setPosition(ElevatorLockPower);
        }
        ElevatorPower = gamepad2.left_stick_y;
        ElevatorPower = ElevatorPower/4;
        Elevator.setPower(-ElevatorPower);

        if(gamepad2.dpad_left){
            Elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        telemetry.update();

    }

}
