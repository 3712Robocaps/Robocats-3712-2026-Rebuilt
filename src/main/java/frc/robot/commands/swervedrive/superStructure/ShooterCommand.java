package frc.robot.commands.swervedrive.superStructure;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.Command;

public class ShooterCommand extends Command{
    private final Shooter ShooterSubsystem;
    private final double targetSpeed;

public ShooterCommand(Shooter ShooterSubsystem, double targetSpeed) {
    this.targetSpeed = targetSpeed;
    this.ShooterSubsystem = ShooterSubsystem;
    addRequirements(ShooterSubsystem);

}

@Override
 public void execute() {
        double speed = (double) applyDeadband(targetSpeed, OperatorConstants.DEADBAND);
    ShooterSubsystem.setSpeed(speed);
    }

        @Override
    public void end(boolean interrupted) {
        ShooterSubsystem.setSpeed(0);
    }
    @Override
    public boolean isFinished() {
        return false;

    }
    private double applyDeadband(double value, double deadband) {
        return (Math.abs(value) > deadband) ? value : 0.0;
    }
}
