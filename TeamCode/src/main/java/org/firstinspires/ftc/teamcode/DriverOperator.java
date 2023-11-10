package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp
public class DriverOperator extends OpMode {

    // Declare our motors
    // Make sure your ID's match your configuration
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotor Elevator = null;
    DcMotor Slider = null;

    Servo Drone = null;

    Servo Elbow = null;
    Servo Grabber = null;
    Servo ElevatorLock = null;
    Servo PurpleDrop = null;
    Servo Wrist = null;

    double DronePos = 0;
    double ElbowPos = 0.9 ;
    double GrabberPos = 0.8;
    double ElevatorLockPos = 0.5;
    double PurpleDropPos = 0.9;
    double WristPos = 0;

    double SliderSpeed = 0;
    double ElevatorPower = 0;
    //double ElevatorLockPower = 0.5;
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

        Elbow = hardwareMap.get(Servo.class, "elbow");
        //Elbow.scaleRange(0, 1);
        Elbow.setPosition(ElbowPos);

        Wrist = hardwareMap.get(Servo.class, "wrist");
        Wrist.scaleRange(0.5, 1);
        Wrist.setPosition(WristPos);

        Grabber = hardwareMap.get(Servo.class, "grabber");
        Grabber.scaleRange(0.5, 1);
        Grabber.setPosition(GrabberPos);

        /*PurpleDrop = hardwareMap.get(Servo.class, "purpledrop");
        //Grabber.scaleRange(0.2, 0.8);
        Grabber.setPosition(PurpleDropPos);*/

        Slider = hardwareMap.dcMotor.get("slider");
        Slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Slider.setDirection(DcMotorSimple.Direction.REVERSE);

        Elevator = hardwareMap.dcMotor.get("elevator");
        Elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Elevator.setDirection(DcMotorSimple.Direction.REVERSE);

        ElevatorLock = hardwareMap.get(Servo.class, "elevatorlock");
        ElevatorLock.scaleRange(0, 1);
        ElevatorLock.setPosition(ElevatorLockPos);
    }

    @Override
    public void loop() {

        Drivepower = 2;
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

        //Slider up and down
        telemetry.addLine("sliderspeed before settong slider power: "+ sliderspeed);
        Slider.setPower(sliderspeed);

        DronePos = 0;
        GrabberPos =0.01;
        ElbowPos = 1;



        //Elbow up and down
        telemetry.addLine("before Elbow");
        //Elbow.setPower(0);
        if (gamepad2.dpad_up){
            ElbowPos = 0.9;
            Elbow.setPosition(ElbowPos);

        } else  if (gamepad2.dpad_down){
            ElbowPos = 1;
            Elbow.setPosition(ElbowPos);
        }

        //Wrist up and down   --Driver
        telemetry.addLine("before Wrist");
        if (gamepad1.dpad_up){
            WristPos = 0.9;
            Wrist.setPosition(WristPos);

        } else  if (gamepad1.dpad_down){
            WristPos = 1;
            Wrist.setPosition(WristPos);
        }

        //grabber in take and open
        telemetry.addLine("before grabber");
        if (gamepad2.right_bumper){
            GrabberPos = 0.01;
            telemetry.addLine("if rb grabber GrabberPos:" + GrabberPos);
            Grabber.setPosition(GrabberPos);

        } else if (gamepad2.left_bumper){

            GrabberPos = 0.2
            ;
            telemetry.addLine(" else lb grabber GrabberPos:" + GrabberPos);
            Grabber.setPosition(GrabberPos);
        }


        //purple intake and drop
       /* telemetry.addLine("before purple drop");
        if (gamepad2.right_bumper){
            GrabberPos = 0.8;
            telemetry.addLine("if rb grabber GrabberPos:" + GrabberPos);
            Grabber.setPosition(GrabberPos);

        } else if (gamepad2.left_bumper){
            GrabberPos = 0.2;
            telemetry.addLine(" else lb grabber GrabberPos:" + GrabberPos);
            Grabber.setPosition(GrabberPos);
        }*/


        //launching drone
        if (gamepad1.left_bumper && gamepad1.right_bumper){
            DronePos = 0;
            Drone.setPosition(DronePos);

        }/* else  if (gamepad1.dpad_right){
            DronePos = 0;
            Drone.setPosition(DronePos);
        }*/

        //Elevator up and down
        ElevatorPower = gamepad2.left_stick_y;
       // ElevatorPower = ElevatorPower;
        Elevator.setPower(-ElevatorPower);

        //locking elevator
        telemetry.addLine("locking elevator");
        if (gamepad2.left_trigger > 0 && gamepad2.right_trigger > 0){

            ElevatorLockPos = 1;
            telemetry.addLine("if..gamepad2.dpad_left:"+ ElevatorLockPos);
            ElevatorLock.setPosition(ElevatorLockPos);

            boolean in_loop = true;

            while(in_loop){
                Elevator.setPower(-0.5);
                in_loop = true;
            }


        } else if (gamepad2.dpad_right){
            ElevatorLockPos = 0.7;
            telemetry.addLine("else..gamepad2.dpad_right:"+ ElevatorLockPos);
            ElevatorLock.setPosition(ElevatorLockPos);
        }

        telemetry.update();

    }

}
