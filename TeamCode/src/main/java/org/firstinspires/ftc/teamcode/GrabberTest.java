package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


//@TeleOp

@Config
@Autonomous(group = "GrabberTest")

public class GrabberTest extends LinearOpMode {

    //    Servo BackDrop = null;
    Servo Grabber = null;

    /* public static double BackDropPos = 0.3;
     public static double BackDropPos_High = 0.6;*/
    public static double GrabberPos = 0.01;
    public static double GrabberPos_High = 0.6;

    @Override
    public void runOpMode() throws InterruptedException {

        /*BackDrop = hardwareMap.get(Servo.class, "backdrop");
        BackDrop.scaleRange(BackDropPos, BackDropPos_High);
        BackDrop.setPosition(BackDropPos);*/

        Grabber = hardwareMap.get(Servo.class, "grabber");
        Grabber.scaleRange(0, 1);
//        Grabber.setPosition(GrabberPos);

        //grabber claw open and close

        //if (gamepad2.right_bumper){

        telemetry.addLine("if rb grabber GrabberPos:" + GrabberPos);
        Grabber.setPosition(GrabberPos);

        //} else if (gamepad2.left_bumper){
        sleep(1000);
        telemetry.addLine(" else lb grabber GrabberPos_High:" + GrabberPos_High);
        Grabber.setPosition(GrabberPos_High);
        //}

        //backdrop up and down
        //if (gamepad2.dpad_up){
        //telemetry.addLine(" if lb back drop:" + BackDropPos);
        //  BackDrop.setPosition(BackDropPos);

        //} else  if (gamepad2.dpad_down){
        // telemetry.addLine(" else BackDropPos_Highe" + BackDropPos_High);
        //  BackDrop.setPosition(BackDropPos_High);
        // }




        telemetry.update();


    }

}

