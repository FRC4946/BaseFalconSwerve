package frc.robot.subsystems;

import java.util.List;
import java.util.Optional;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Vision extends SubsystemBase{
  private final PhotonCamera camera;
  private PhotonPipelineResult result;
  
  private int targetTagID;
  private boolean tagFound = false;

  private PhotonTrackedTarget target;
  private List<PhotonTrackedTarget> targets;

  public Vision() {
    camera = new PhotonCamera("Microsoft_LifeCam_HD-3000"); //TODO: get this
    result = camera.getLatestResult();
    targets = result.getTargets();

    targetTagID = Constants.Vision.targetTagId;

    if (result.hasTargets()) {
      target = targets.get(targets.indexOf(targetTagID));
      tagFound = true;
    } else {
      tagFound = false;
    }
  }

  public void setTargetTag(int target) {
    targetTagID = target;
  }

  public void update() {
    result = camera.getLatestResult();
    targets = result.getTargets();
    if (result.hasTargets()) {
      target = targets.get(targets.indexOf(targetTagID));
    } 
  }
/*
  public double getDistToTarget() {
    return PhotonUtils.calculateDistanceToTargetMeters(cameraHeight, targetHeight, cameraPitch, Units.degreesToRadians(target.getPitch()));
  }

  /**
   * 
   * @return
   */
  /*
  public Translation2d getTranslationToTarget() {
    return PhotonUtils.estimateCameraToTargetTranslation(getDistToTarget(), Rotation2d.fromDegrees(target.getYaw()));
  }
  */

  /**
   * Gets if the latest result has any target(s).
   * @apiNote Call this everytime when getting target data, 
   * otherwise will give a null pointer execption.
   * @return True if the pipeline has a target
   */
  public boolean hasTarget(int targetID) {
    //System.out.println(result.hasTargets()) ;
    return result.hasTargets();
  }

  public double getOffsetZ() {
    if (hasTarget(targetTagID)) {
      return target.getBestCameraToTarget().getZ();
    } else {
      return 69420.0;
    }
  }

  public double getOffsetY() {
    if (hasTarget(targetTagID)) {
      return target.getBestCameraToTarget().getY();
    } else {
      return 69420.0;
    }
  }

  public double getOffsetX() {
    if (hasTarget(targetTagID)) {
      return target.getBestCameraToTarget().getX();
    } else {
      return 69420.0;
    }
  }

  public void setCameraFilter(boolean on) {
    camera.setDriverMode(on);
  }

  public boolean getCameraFilter() {
    return !camera.getDriverMode();
  } 

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("has target?", hasTarget(targetTagID));
    SmartDashboard.putNumber("tag id", targetTagID);
    SmartDashboard.putNumber("x offset", getOffsetX());
    SmartDashboard.putNumber("y offset", getOffsetY());
    SmartDashboard.putNumber("z offset", getOffsetZ());
    double[] tagsSen = new double[targets.size()];
    for (int i=0; i < targets.size(); i++) {
      tagsSen[i] = (double) targets.get(i).getFiducialId();
    }
    SmartDashboard.putNumberArray("current tags", tagsSen);
    update();
    }
}
