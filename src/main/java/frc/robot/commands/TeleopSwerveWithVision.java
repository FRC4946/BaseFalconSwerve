package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TeleopSwerveWithVision extends CommandBase {

    private double rotation;
    private Translation2d translation;
    private boolean fieldRelative;
    private boolean openLoop;

    private Swerve s_Swerve;
    private Vision s_Vision;

    private PIDController m_pid;

    private Joystick controller;
    private int translationAxis;
    private int strafeAxis;

    /**
     * Driver control
     */
    public TeleopSwerveWithVision(Swerve s_Swerve, Vision s_Vision, Joystick controller, int translationAxis,
            int strafeAxis, boolean fieldRelative, boolean openLoop) {
        this.s_Swerve = s_Swerve;
        this.s_Vision = s_Vision;
        addRequirements(s_Swerve, s_Vision);

        this.controller = controller;
        this.translationAxis = translationAxis;
        this.strafeAxis = strafeAxis;
        this.fieldRelative = fieldRelative;
        this.openLoop = openLoop;

        m_pid = new PIDController(Constants.Swerve.angleKP, Constants.Swerve.angleKI, Constants.Swerve.angleKD);
    }

    @Override
    public void initialize() {
        m_pid.setTolerance(2);
        m_pid.reset();
    }

    @Override
    public void execute() {
        double yAxis = controller.getRawAxis(translationAxis);
        double xAxis = controller.getRawAxis(strafeAxis);

        /* Deadbands */
        yAxis = (Math.abs(yAxis) < Constants.stickDeadband) ? 0 : yAxis;
        xAxis = (Math.abs(xAxis) < Constants.stickDeadband) ? 0 : xAxis;

        translation = new Translation2d(yAxis, xAxis).times(Constants.Swerve.maxSpeed);

        if (s_Vision.hasTarget(Constants.Vision.targetTagId)) {
            rotation = m_pid.calculate(-s_Vision.getOffsetX(), 0);
            s_Swerve.drive(translation, rotation, fieldRelative, openLoop);

            // TODO: insert led code if vision works
            if (m_pid.atSetpoint()) {

            } else {

            }
        } else {
            s_Swerve.drive(translation, 0, fieldRelative, openLoop);
        }

        SmartDashboard.putBoolean("Vision at setpoint", m_pid.atSetpoint());
        SmartDashboard.putNumber("Vision setpoint", m_pid.getSetpoint());
    }
}
