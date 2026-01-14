package frc.robot.subsystems.shooter;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import edu.wpi.first.math.filter.Debouncer;

public class ShooterIOSpark implements ShooterIO {
  // private final Rotation2d zeroRotation;

  // Hardware objects
  private final SparkBase pivotSpark;
  private final RelativeEncoder pivotEncoder;

  // Closed loop controllers
  private final SparkClosedLoopController pivotController;

  //   // Queue inputs from odometry thread
  //   private final Queue<Double> timestampQueue;
  //   private final Queue<Double> drivePositionQueue;
  //   private final Queue<Double> turnPositionQueue;

  // Connection debouncers
  private final Debouncer pivotConnectedDebounce = new Debouncer(0.5);

  public ShooterIOSpark() {

    pivotSpark = new SparkMax(ShooterConstants.kPivotCANID, MotorType.kBrushless);

    pivotEncoder = pivotSpark.getEncoder();
    pivotController = pivotSpark.getClosedLoopController();

    // Configure drive motor
    var pivotConfig = new SparkFlexConfig();
    pivotConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(20).voltageCompensation(12.0);
    pivotConfig.encoder.positionConversionFactor(1).velocityConversionFactor(1);

    pivotConfig
        .closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .pidf(
            ShooterConstants.kPivotP, 0.0,
            ShooterConstants.kPivotD, 0.0);
  }

  @Override
  public void setPivotSpeed(double speed) {
    pivotSpark.set(speed);
  }

  @Override
  public void setPivotPosition(double setpoint) {
    pivotEncoder.setPosition(setpoint);
  }

  @Override
  public double getPivotPosition() {
    return pivotEncoder.getPosition();
  }

  @Override
  public void reset() {
    pivotEncoder.setPosition(0);
  }

  @Override
  public RelativeEncoder getEncoder() {
    return pivotEncoder;
  }
}
