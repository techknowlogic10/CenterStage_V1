import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class Single_Wheel_test extends OpMode {
    // Make sure your ID's match your configuration
    DcMotor frontLeft;
    double Drivepower = 2;
    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
    }

    @Override
    public void loop() {
        double y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;

        telemetry.addLine("y: "+ y +" , x: "+ x + " , rx: +"+ rx);

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), Drivepower);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

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


    }

}
