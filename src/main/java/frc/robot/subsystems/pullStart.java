// This subsystem controls the pull start machanism. 
// The mechanism consists of the shooter motors on the hexshaft, which a rope is wrapped around to perform a pull start motion.
// The subsystem detects when the shaft is rotated around a specific speed and number of rotations to enage
// Once engaed it will perform a cold start style movement to mimic that start of a chainsaw

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SubsystemConstants;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;

public class pullStart extends SubsystemBase {
    private final SparkMax pullStartMotor;

    boolean pullTriggered = false;
    double lastPositionRotations = 0.0;

    // These variable need to be fine tuned
    public static final double kMinPullRotations = 10.0; // Minimum rotations of wheel when pulling rope to engage
    public static final double kMinPullVelocity = 0.5; // Minimum velocity of wheel when pulling rope to engage
    public static final boolean kPullDirection = true; // Direction to pull rope to engage (true for clockwise, false for counter-clockwise)
    private final RelativeEncoder pullStartEncoder;

    public pullStart() {
        pullStartMotor = new SparkMax(SubsystemConstants.ShooterLeadID, SparkMax.MotorType.kBrushless);
        pullStartEncoder = pullStartMotor.getEncoder();

    }
    @Override public void periodic() {
        

        // This method will be called once per scheduler run
        double pullRotations = pullStartEncoder.getPosition();
        double pullVelocity = pullStartEncoder.getVelocity();
        double deltaRotations = (pullRotations - lastPositionRotations);

        boolean movingCorrectDirection = kPullDirection ? deltaRotations > 0 : deltaRotations < 0;
        boolean sufficientRotations = Math.abs(deltaRotations) >= kMinPullRotations;
        boolean sufficientVelocity = Math.abs(pullVelocity) >= kMinPullVelocity;

        if (!pullTriggered && movingCorrectDirection && sufficientRotations && sufficientVelocity) {
            pullTriggered = true;
        }

        lastPositionRotations = pullRotations;
    }
     
    public boolean consumePullTrigger() {
        if (pullTriggered) {
            pullTriggered = false;
            return true;
        }
        return false;
    }

    public void runVoltage(double voltage) {
        pullStartMotor.setVoltage(voltage);
    }

    public void stop() {
        pullStartMotor.setVoltage(0);
    }

    public double getPullRotations() {
        return pullStartEncoder.getPosition();
    }

    public double getPullVelocity() {
        return pullStartEncoder.getVelocity();
    }

    public void zeroPosition() {
        pullStartEncoder.setPosition(0.0);
        lastPositionRotations = 0.0;
    }
}
