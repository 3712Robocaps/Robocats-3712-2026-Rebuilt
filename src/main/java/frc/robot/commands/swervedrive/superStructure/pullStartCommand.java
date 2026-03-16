package frc.robot.commands.swervedrive.superStructure;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.pullStart;

public class pullStartCommand extends Command {
    private final pullStart pullStartSubsystem;
    private final Timer timer = new Timer();

    public pullStartCommand(pullStart pullStartSubsystem) {
        this.pullStartSubsystem = pullStartSubsystem;
        addRequirements(pullStartSubsystem);
    }

    @Override
    public void initialize() {
        pullStartSubsystem.zeroPosition();
        timer.restart();
    }

    @Override
    public void execute() {
        double t = timer.get();
         if (t < 0.5) {
            pullStartSubsystem.runVoltage(12.0); // Full voltage for the first 0.5 seconds to mimic chainsaw start
        } else {
            pullStartSubsystem.stop(); // Stop the motor after the initial pull
        }
    }

    @Override
    public void end(boolean interrupted) {
        pullStartSubsystem.stop();
        timer.stop();
    }

    @Override
    public boolean isFinished() {
        return timer.get() >= 2.5;
    }
}