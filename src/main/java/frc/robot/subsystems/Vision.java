package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {

    PhotonCamera camera;
    PhotonPipelineResult result;

    public Vision() {

        camera = new PhotonCamera("photonvision"); //TODO: get camera name


    }

    /**
     * 
     * @param on
     */
    public void setCameraFilter(boolean on) {
        camera.setDriverMode(on);
    }

    /**
     * @return true if the camera is filtered
     */
    public boolean getCameraFilter() {
        return !camera.getDriverMode();
    }

    /**
     * @return true if the camera has a target
     */
    public boolean getHasTarget() {
        return result.hasTargets();
    }
    
    /**
     * @return the integer of the current targets
     */
    public int getTargetCount() {
        return result.getTargets().size();
    }

    /**
     * Returns the x-offset of the best target in degrees
     * 
     * @return a double containing the x-offset
     */
    public double getOffsetX() {
        return result.getBestTarget().getYaw();
    }

    /**
     * Returns the y-offset of the best target in degrees
     * 
     * @return a double containing the y-offset
     */
    public double getOffsetY() {
        return result.getBestTarget().getPitch();
    }

    /**
     * Returns the percentage of area the best target takes up on the camera
     * 
     * @return a double containing the best target area
     */
    public double getTargetArea() {
        return result.getBestTarget().getArea();
    }

    /**
     * 
     * 
     * @return 
     */
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
