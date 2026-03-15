package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;

public class Limelightvision extends SubsystemBase {

    private static final String LIMELIGHT = "limelight";

    public boolean hasTarget() {
        return LimelightHelpers.getTV(LIMELIGHT);
    }

    public Pose2d getBotPose() {
        var alliance = DriverStation.getAlliance();

        double[] pose;

        if (alliance.isPresent() &&
            alliance.get() == DriverStation.Alliance.Red) {

            pose = LimelightHelpers.getBotPose_wpiRed(LIMELIGHT);

        } else {

            pose = LimelightHelpers.getBotPose_wpiBlue(LIMELIGHT);
        }

        return new Pose2d(
            pose[0],
            pose[1],
            Rotation2d.fromDegrees(pose[5])
        );
    }
}