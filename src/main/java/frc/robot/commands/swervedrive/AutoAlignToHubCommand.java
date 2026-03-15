package frc.robot.commands.swervedrive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;

public class AutoAlignToHubCommand extends Command {

    private final SwerveSubsystem swerve;

    private final double desiredRadius = 2.0;

    private final PIDController xController =
        new PIDController(2.0, 0, 0);

    private final PIDController yController =
        new PIDController(2.0, 0, 0);

    private final PIDController thetaController =
        new PIDController(4.0, 0, 0);

    private static final Translation2d HUB_RED =
        new Translation2d(12.39872, 4.08432);
        

    private static final Translation2d HUB_BLUE =
        new Translation2d(4.1824, 4.08432);
        

    public AutoAlignToHubCommand(SwerveSubsystem swerve) {
        this.swerve = swerve;
        addRequirements(swerve);

        thetaController.enableContinuousInput(-Math.PI, Math.PI);
    }

    private boolean atTarget(Translation2d hub) {

    Pose2d pose = swerve.getPose();

    double distance =
        pose.getTranslation().getDistance(hub);

    double angleError =
        Math.abs(
            pose.getRotation()
                .minus(
                    hub.minus(
                        pose.getTranslation()
                    ).getAngle()
                ).getDegrees()
        );

    return Math.abs(distance - desiredRadius) < 0.1
        && angleError < 3;
}

    @Override
    public void execute() {

        Pose2d robotPose = swerve.getPose();
        Translation2d robot = robotPose.getTranslation();

        Translation2d hub =
            DriverStation.getAlliance().isPresent() &&
            DriverStation.getAlliance().get() ==
                DriverStation.Alliance.Red
            ? HUB_RED
            : HUB_BLUE;

        // Vector hub -> robot
        Translation2d direction = robot.minus(hub);

        // Prevent divide-by-zero
        if (direction.getNorm() < 0.05) return;

        Translation2d normalized =
            direction.div(direction.getNorm());

        // Target point on 2m radius
        Translation2d targetPoint =
            hub.plus(normalized.times(desiredRadius));

        // Face hub
        Rotation2d targetRotation =
            hub.minus(targetPoint).getAngle();

        double xSpeed =
            xController.calculate(robot.getX(),
                                  targetPoint.getX());

        double ySpeed =
            yController.calculate(robot.getY(),
                                  targetPoint.getY());

        double thetaSpeed =
            thetaController.calculate(
                robotPose.getRotation().getRadians(),
                targetRotation.getRadians()
            );

        swerve.drive(
            new ChassisSpeeds(
                xSpeed,
                ySpeed,
                thetaSpeed
            )
        );
        
    }
@Override
public boolean isFinished() {

    Translation2d hub =
        DriverStation.getAlliance().isPresent() &&
        DriverStation.getAlliance().get() ==
            DriverStation.Alliance.Red
        ? HUB_RED
        : HUB_BLUE;

    return atTarget(hub);
}
}
