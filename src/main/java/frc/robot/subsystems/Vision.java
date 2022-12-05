package frc.robot.subsystems;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase{
  private final PhotonCamera camera;
  private PhotonPipelineResult result;

  //TODO: get these, also should prolly place into constants
  //also heights in meters
  private double cameraHeight = 0;
  private double targetHeight = 0;
  private double cameraPitch = 0;

  private int targetTagId = 5;

  private PhotonTrackedTarget target;
  private List<PhotonTrackedTarget> targets;

  public Vision() {
    camera = new PhotonCamera("Microsoft_LifeCam_HD-3000"); //TODO: get this
    result = camera.getLatestResult();
    targets = result.getTargets();
  }

  public void setTargetTag(int target) {
    targetTagId = target;
  }

  public void update() {
    result = camera.getLatestResult();
    targets = result.getTargets();
    if (targets.contains(targetTagId)) {
      target = targets.get(targets.indexOf(targetTagId));
    }
  }

  public double getDistToTarget() {
    return PhotonUtils.calculateDistanceToTargetMeters(cameraHeight, targetHeight, cameraPitch, Units.degreesToRadians(target.getPitch()));
  }

  /**
   * 
   * @return
   */
  public Translation2d getTranslationToTarget() {
    return PhotonUtils.estimateCameraToTargetTranslation(getDistToTarget(), Rotation2d.fromDegrees(target.getYaw()));
  }

  public boolean hasTarget() {
    return targets.contains(targetTagId);
  }

  public double getOffsetZ() {
    return target.getBestCameraToTarget().getZ();
  }

  public double getOffsetY() {
    return target.getBestCameraToTarget().getY();
  }

  public double getOffsetX() {
    return target.getBestCameraToTarget().getX();
  }

  public void setCameraFilter(boolean on) {
    camera.setDriverMode(on);
  }

  public boolean getCameraFilter() {
    return !camera.getDriverMode();
  } 

  @Override
  public void periodic() {
    update();
    SmartDashboard.putNumber("distance to target", getDistToTarget());
    SmartDashboard.putBoolean("has target?", hasTarget());
    SmartDashboard.putNumber("tag id", targetTagId);
    SmartDashboard.putNumber("x offset", getOffsetX());
    SmartDashboard.putNumber("y offset", getOffsetY());
    SmartDashboard.putNumber("z offset", getOffsetZ());
    double[] tagsSen = new double[targets.size()];
    for (int i=0; i < targets.size(); i++) {
      tagsSen[i] = (double) targets.get(i).getFiducialId();
    }
    SmartDashboard.putNumberArray("current tags", tagsSen);
  }
}
