package frc.mechs;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.AnalogInput;
import frc.config.Config;

public class StorageMech {
    // feel free to rename and reorganize these variables
    /* IR sensors */
    private AnalogInput irTop, irMiddle, irBottom, irRamp;
    /* Talon SRX motor controller. */
    private TalonSRX motor;

    /* if the analog input value is greater than this number, then there is a lemon in front of the sensor */
    private static final int LEMON_THRESHOLD = 1200;

    private static int ballCount = 0;

    public StorageMech() {
        this.motor = new WPI_TalonSRX(Config.getInt("motor_id"));
        this.irTop = new AnalogInput(Config.getInt("ir0_id"));
        this.irMiddle = new AnalogInput(Config.getInt("ir1_id"));
        this.irBottom = new AnalogInput(Config.getInt("ir2_id"));
        this.irRamp = new AnalogInput(Config.getInt("ir3_id"));
    }

    /**
     * this function is called periodically during teleop
     */
    public void update() {
        // take input from sensors and make motors move here

        //Never want it to go past top
        if (ballInFront(irTop)){
            motor.set(ControlMode.PercentOutput, 0);
        }
        //
        else if (ballInFront(irBottom) && ballInFront(irRamp)){
            ballCount = 2; //logically the sensors are awar of 2 balls at this point
            motor.set(ControlMode.PercentOutput, .5);
        } 
        //a)so it doesnt start when there's only one ball in storage (at irBottom)
        //b)Could happen that 2 balls have made it past irBottom. At this point no balls would be touching irBottom. 
        //If a third ball gets to the tubing, it would be the only ball touching a sensor (irBottom) but we would still 
        //want the motor to turn on
        else if(ballCount >= 2 && ballInFront(irBottom)){
            motor.set(ControlMode.PercentOutput, .5);
        }
        else{
            motor.set(ControlMode.PercentOutput, 0);

        }
    }
        
   public boolean ballInFront (AnalogInput ir){
        return ir.getValue() > LEMON_THRESHOLD;
    }

}
