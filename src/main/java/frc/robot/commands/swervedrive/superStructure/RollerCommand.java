package frc.robot.commands.swervedrive.superStructure;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Roller;
import edu.wpi.first.wpilibj2.command.Command;

public class RollerCommand extends Command{
    private final Roller RollerSubsystem;
    private final double targetSpeed;

public RollerCommand(Roller RollerSubsystem, double targetSpeed) {
    this.targetSpeed = targetSpeed;
    this.RollerSubsystem = RollerSubsystem;
    addRequirements(RollerSubsystem);

}

@Override
 public void execute() {
        double speed = (double) applyDeadband(targetSpeed, OperatorConstants.DEADBAND);
    RollerSubsystem.setSpeed(speed);
    }
    @Override
    public void end(boolean interrupted) {
        RollerSubsystem.setSpeed(0);
    }
    @Override
    public boolean isFinished() {
        return false;

    }
    private double applyDeadband(double value, double deadband) {
        return (Math.abs(value) > deadband) ? value : 0.0;
    }
}
