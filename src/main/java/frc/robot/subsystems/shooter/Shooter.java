package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

  private final ShooterIO shooter;
  public MechanismLigament2d base;
  public MechanismLigament2d barrel;

  public Shooter(ShooterIO shooter) {
    this.shooter = shooter;

    // the main mechanism object
    Mechanism2d mech = new Mechanism2d(3, 3);
    // the mechanism root node
    MechanismRoot2d root = mech.getRoot("shooter", 2, 0);

    // MechanismLigament2d objects represent each "section"/"stage" of the mechanism, and are based
    // off the root node or another ligament object
    base = root.append(new MechanismLigament2d("elevator", 3, 90));
    barrel = base.append(new MechanismLigament2d("barrel", 3, 90, 6, new Color8Bit(Color.kPurple)));
  }

  // shooter pivot up
  public Command shooterPivotUp() {
    return run(() -> shooter.setPivotSpeed(1));
  }
  // shooter pivot down
  public Command shooterPivotDown() {
    return run(() -> shooter.setPivotSpeed(-1));
  }
  // stops shooter pivot
  public Command stopShooterPivot() {
    return run(() -> shooter.setPivotSpeed(0));
  }

  public void resetEncoder() {
    shooter.reset();
  }

  @Override
  public void periodic() {
    // update the dashboard mechanism's state
    base.setLength(3 + shooter.getEncoder().getPosition());
    // barrel.setAngle(m_wristPot.get());
    // SmartDashboard.putNumber("Intake Position", Math.round(intake.getPivotPosition() *
    // Math.pow(10, 2)) / Math.pow(10, 2));
  }
}
