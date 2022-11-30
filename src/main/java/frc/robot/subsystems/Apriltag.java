package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Apriltag extends SubsystemBase {
    
    PIDController turnController = new PIDController(ANGULAR_P, 0, ANGULAR_D);


}
