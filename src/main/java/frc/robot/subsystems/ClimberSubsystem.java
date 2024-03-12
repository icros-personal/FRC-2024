package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
// import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {
  private final CANSparkMax m_climberMotor;

  private DigitalInput m_homeSensor;

  ShuffleboardTab dashboardTab = Shuffleboard.getTab("NewDashboard");

  GenericEntry homesensor;

  /** Creates a new HookSubsystem. */
  public ClimberSubsystem(int climberMotor /* , int topLimitSwitch, int bottomLimitSwitch */) {
    m_climberMotor = new CANSparkMax(climberMotor, MotorType.kBrushless);
    m_climberMotor.setIdleMode(IdleMode.kBrake);
    m_climberMotor.setInverted(true);

    m_homeSensor = new DigitalInput(Constants.Digital.CLIMB_HOME_SWITCH);

    homesensor = 
    dashboardTab.addPersistent("Climb Home Sensor", false)
    .getEntry();
  }

  public boolean isHomePressed(){
    return !m_homeSensor.get();
  }

  /**
   * Extends the hook.
   */
  public void setClimber(double m_pow) {
    System.out.println("Climber: " + m_pow);
    m_climberMotor.set(m_pow);
  }

  /**
   * Stops the hook.
   */
  public void stop() {
    m_climberMotor.set(0);
  }

  public void periodic() {
    if (isHomePressed()) {
      if (m_climberMotor.get() < 0) {
        m_climberMotor.set(0);
      }
    }
    homesensor.setBoolean(isHomePressed());
  }
}


