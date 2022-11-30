package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Apriltag extends SubsystemBase {

    PhotonCamera camera;
    PhotonPipelineResult result;

    public Apriltag() {

        camera = new PhotonCamera("photonvision"); //TODO: get camera name

    }

    public void setCameraFilter(boolean on) {
        camera.setDriverMode(on);
    }

    public boolean getCameraFilter() {
        return camera.getDriverMode();
    }

    public boolean getHasTarget() {
        return result.hasTargets();
    }
    
    public int getTargetCount() {
        return result.getTargets().size();
    }

    public double getOffsetX() {
        return result.getBestTarget().getYaw();
    }

    public double getOffsetY() {
        return result.getBestTarget().getPitch();
    }

    public double getTargetArea() {
        return result.getBestTarget().getArea();
    }

    public double getDistance() {
        return 0.0; //TODO fix
    }

    @Override
    public void periodic() {

        result = camera.getLatestResult();

        SmartDashboard.putBoolean("Has Target", getHasTarget());
        SmartDashboard.putNumber("Target Count", getTargetCount());
        SmartDashboard.putNumber("Target Area", getTargetArea());
        SmartDashboard.putNumber("Camera-X Offset", getOffsetX());
    }

}
