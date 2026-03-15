package frc.robot.commands.swervedrive.superStructure;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;

public class IntakeCommand extends Command{
    private final Intake IntakeSubsystem;
    private final double targetSpeed;

public IntakeCommand(Intake IntakeSubsystem, double targetSpeed) {
    this.targetSpeed = targetSpeed;
    this.IntakeSubsystem = IntakeSubsystem;
    addRequirements(IntakeSubsystem);

}

@Override
 public void execute() {
        double speed = (double) applyDeadband(targetSpeed, OperatorConstants.DEADBAND);
    IntakeSubsystem.setSpeed(speed);
    }
    @Override
    public void end(boolean interrupted) {
        IntakeSubsystem.setSpeed(0);
    }
    @Override
    public boolean isFinished() {
        return false;

    }
    private double applyDeadband(double value, double deadband) {
        return (Math.abs(value) > deadband) ? value : 0.0;
    }
}
