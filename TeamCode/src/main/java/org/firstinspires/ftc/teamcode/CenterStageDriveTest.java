package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
//test git

@TeleOp
public class CenterStageDriveTest extends OpMode {
    // Declare our motors
    // Make sure your ID's match your configuration
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotor Elevator = null;
    DcMotor Slider = null;

// test github push

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

    /*Servo Arm = null;
    Servo Grabber = null;
    Servo DRS = null;*/



    /* DistanceSensor SliderDistance = null;
     DistanceSensor ConeToArm = null;
     DigitalChannel ConeSensor = null;


     double SliderSpeed = 0;
     double SliderSpeedFront = 1;
     double SliderSpeedRear = -1;
     double ArmPos = 0;
     double GrabberPos = 0;*/
    double Drivepower = 2;
   /* double Sliderdistance = 0;
    double SliderLimitRear = 100;

    double DRS_pos = 0;*/

    //Brake brake;


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
        //Slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //Slider.setDirection(DcMotorSimple.Direction.REVERSE);

        //TestMotor = hardwareMap.dcMotor.get("testmotor");
       // frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        Elevator = hardwareMap.dcMotor.get("elevator");
        Elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        ElevatorLock = hardwareMap.get(Servo.class, "elevatorlock");
        ElevatorLock.scaleRange(0, 1);
        ElevatorLock.setPosition(ElevatorLockPower);


        /*

        Slider = hardwareMap.dcMotor.get("slider");
        Slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Arm = hardwareMap.get(Servo.class, "arm");


        Arm.scaleRange(0, 1);

        Grabber = hardwareMap.get(Servo.class, "grabber");

        ConeSensor = hardwareMap.get(DigitalChannel.class, "cone sensor");
        SliderDistance = hardwareMap.get(DistanceSensor.class, "Slider Distance");
        ConeToArm = hardwareMap.get(DistanceSensor.class, "cone to arm sensor");
        // set the digital channel to input.
        ConeSensor.setMode(DigitalChannel.Mode.INPUT);*/

        //brake = new Brake(hardwareMap);

        // DRS = hardwareMap.get(Servo.class, "DRS");



    }

    @Override
    public void loop() {

        // telemetry.addLine("distance from arm to cone in cm is "+ ConeToArm.getDistance(DistanceUnit.CM));


        // Sliderdistance = SliderDistance.getDistance(DistanceUnit.CM);

        Drivepower = 4;
        /*if (gamepad1.right_trigger > 0.5){
            Drivepower = 2.5;
            //brake.goHome();
        }
        if (gamepad1.left_stick_button){
            Drivepower = 1.5;
        }*/



        // telemetry.addLine("Slider is" + SliderDistance.getDistance(DistanceUnit.CM));
        // double y = gamepad1.left_stick_y; // Remember, this is reversed!
        // double x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        //double rx = -gamepad1.right_stick_x;

        double y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;
        double sliderspeed =  gamepad2.right_stick_y;

        telemetry.addLine("y: "+ y +" , x: "+ x + " , rx: +"+ rx);
        telemetry.addLine("sliderspeed: "+ sliderspeed);

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), Drivepower);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        telemetry.addLine("denominator: "+ denominator +" , frontLeftPower: "+ frontLeftPower + " , backLeftPower: +"+ backLeftPower +", frontRightPower: "+frontRightPower +" , backRightPower: "+backRightPower);

        double averageSpeed = (Math.abs(frontLeftPower)+Math.abs(backLeftPower)+Math.abs(frontRightPower)+Math.abs(backRightPower))/4;
        if (averageSpeed < 0.05){
            if (gamepad1.b){
                //  brake.goHome();
            } else {
                // brake.brake();
            }

        } else{
            // brake.goHome();
        }
        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);

        telemetry.addLine("sliderspeed before settong slider power: "+ sliderspeed);
        Slider.setPower(sliderspeed);

        /*if (DronePos < 0){
            DronePos = 0;
        }
        if (DronePos > 1){
            DronePos = 1;
        }*/
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

        } else  if (gamepad2.right_bumper){
            ElevatorLockPower = 0.5;
            ElevatorLock.setPosition(ElevatorLockPower);
        }

        ElevatorPower = gamepad2.left_stick_y;
        ElevatorPower = ElevatorPower/4;

       /* if (gamepad2.right_trigger>.5){
            ElevatorPower = ElevatorPower/2;
        }
        if (gamepad2.right_stick_y > .75){
            ElevatorPower = 1;
        }*/

       /* if (gamepad2.right_stick_y < -.75){
            ElevatorPower = -1;
        }*/

        Elevator.setPower(-ElevatorPower);

        if(gamepad2.dpad_left){
            Elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        /*if (gamepad1.right_stick_y){
            if (ConeSensor.getState() == false) {
                Slider.setPower(.1);
                gamepad1.rumble(100);
            }
            Slider.setPower(SliderSpeedFront);
        } else if (gamepad1.left_bumper) {
            Slider.setPower(SliderSpeedRear);
        } else{
            Slider.setPower(0);
        }*/



      /*  double ArmMultiplier = 1;
        if (gamepad2.right_trigger > 0.1){
            ArmMultiplier = 4;
        }




        ElevatorPower = gamepad2.right_stick_y;
        if (gamepad2.right_trigger>.5){
            ElevatorPower = ElevatorPower/2;
        }
        if (gamepad2.right_stick_y > .75){
            ElevatorPower = 1;
        }

        if (gamepad2.right_stick_y < -.75){
            ElevatorPower = -1;
        }

        ELEVATOR.setPower(-ElevatorPower);
        if (gamepad2.dpad_down){
            ArmPos = 0.027;
            Arm.setPosition(ArmPos);
        }
        if (gamepad2.dpad_up){
            ArmPos = .673564;
            //681965
            Arm.setPosition(ArmPos);
        }
        if (gamepad2.dpad_left){
            ArmPos = .321123;
            Arm.setPosition(ArmPos);
        }
        if (gamepad2.dpad_right){
            ArmPos = 1;
            Arm.setPosition(ArmPos);
        }
        if (gamepad2.left_stick_x>.1){
            ArmPos = ArmPos + gamepad2.left_stick_x*ArmMultiplier/100;
            Arm.setPosition(ArmPos);
        }
        if (gamepad2.left_stick_x<-.1){
            ArmPos = ArmPos + gamepad2.left_stick_x*ArmMultiplier/100;
            Arm.setPosition(ArmPos);
        }


        if (gamepad1.right_bumper){
            if (ConeSensor.getState() == false) {
                Slider.setPower(.1);
                gamepad1.rumble(100);
            }
            Slider.setPower(SliderSpeedFront);
        } else if (gamepad1.left_bumper) {
            Slider.setPower(SliderSpeedRear);
        } else{
            Slider.setPower(0);
        }

        if (gamepad1.dpad_right){
            SliderSpeedFront = SliderSpeedFront + 0.1;
            SliderSpeedRear = SliderSpeedRear - 0.1;
        }

        if (gamepad1.dpad_left){
            SliderSpeedFront = SliderSpeedFront - 0.1;
            SliderSpeedRear = SliderSpeedRear + 0.1;
        }
        telemetry.addLine("slider speed front is "+ SliderSpeedFront);
        telemetry.addLine("slider speed rear is "+ SliderSpeedRear);
        telemetry.addLine("slider distance" + SliderDistance.getDistance(DistanceUnit.CM));
        if (SliderSpeedFront >= 1){
            SliderSpeedFront = 1;
        }
        if (SliderSpeedFront <= 0){
            SliderSpeedFront = 0;
        }

        if (SliderSpeedRear >= 0){
            SliderSpeedRear = 0;
        }
        if (SliderSpeedRear <= -1){
            SliderSpeedRear = -1;
        }



        telemetry.addLine("Arm position ="+ ArmPos);
        telemetry.addLine("Slider Limit is"+ SliderLimitRear);




        if (ArmPos < 0){
            ArmPos = 0;
        }
        if (ArmPos > 1){
            ArmPos = 1;
        }

        if(gamepad2.left_bumper){
            GrabberPos = 0.01;
        }
        if (gamepad2.right_bumper){
            GrabberPos = .4;
        }
        Grabber.setPosition(GrabberPos);

        if (gamepad1.dpad_up){
            DRS_pos = .5;
        }

        if (gamepad1.dpad_down){
            DRS_pos = 0;
        }

        DRS.setPosition(DRS_pos);

        telemetry.addLine("DRS position is "+DRS.getPosition());*/




        telemetry.update();

    }

}
