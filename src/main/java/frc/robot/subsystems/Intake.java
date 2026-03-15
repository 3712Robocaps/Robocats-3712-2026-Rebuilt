package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SubsystemConstants;

public class Intake extends SubsystemBase{
    private final SparkMax IntakeLeadMotor;
   
    public static final double MAXSPEED = 1;
    public static final double MINSPEED = -1;

    @SuppressWarnings("removal")
    public Intake(){
        IntakeLeadMotor = new SparkMax(SubsystemConstants.IntakeLeadID, MotorType.kBrushless);
       
        SparkMaxConfig config = new SparkMaxConfig();

        config
                .inverted(true)
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(40);

        config.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .pidf(0.1,0,0, 0.1)
                .outputRange(MINSPEED, MAXSPEED);

            IntakeLeadMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
          

           
            
 
            
    }
public void setSpeed(double targetSpeed){
    IntakeLeadMotor.set(targetSpeed);
}
    
}
