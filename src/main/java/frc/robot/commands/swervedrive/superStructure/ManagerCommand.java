package frc.robot.commands.swervedrive.superStructure;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Manager;

import edu.wpi.first.wpilibj2.command.Command;

public class ManagerCommand extends Command{
    private final Manager ManagerSubsystem;
    private final double targetSpeed;

public ManagerCommand(Manager ManagerSubsystem, double targetSpeed) {
    this.targetSpeed = targetSpeed;
    this.ManagerSubsystem = ManagerSubsystem;
    addRequirements(ManagerSubsystem);

}

@Override
 public void execute() {
        double speed = (double) applyDeadband(targetSpeed, OperatorConstants.DEADBAND);
    ManagerSubsystem.setSpeed(speed);
    }

        @Override
    public void end(boolean interrupted) {
        ManagerSubsystem.setSpeed(0);
    }
    @Override
    public boolean isFinished() {
        return false;

    }
    private double applyDeadband(double value, double deadband) {
        return (Math.abs(value) > deadband) ? value : 0.0;
    }
}
