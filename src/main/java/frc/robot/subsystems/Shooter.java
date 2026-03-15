package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SubsystemConstants;

public class Shooter extends SubsystemBase{
    private final SparkMax ShooterLead;
    private final SparkMax ShooterFollow;
    public static final double MAXSPEED = 1;
    public static final double MINSPEED = -1;

    @SuppressWarnings("removal")
    public Shooter(){
        ShooterLead = new SparkMax(SubsystemConstants.ShooterLeadID, MotorType.kBrushless);
        ShooterFollow = new SparkMax(SubsystemConstants.ShooterFollowID, MotorType.kBrushless);
        SparkMaxConfig config = new SparkMaxConfig();

        config
                .inverted(false)
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(40);

        config.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .pidf(0.1,0,0, 0.1)
                .outputRange(MINSPEED, MAXSPEED);

            ShooterLead.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
            ShooterFollow.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

            SparkMaxConfig followerConfig = new SparkMaxConfig();
            followerConfig.follow(ShooterLead, false);
            ShooterFollow.configure(followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
public void setSpeed(double targetSpeed){
    ShooterLead.set(targetSpeed);
}

    
}
