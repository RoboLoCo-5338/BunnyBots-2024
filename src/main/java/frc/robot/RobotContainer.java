// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
<<<<<<< HEAD
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
=======
import edu.wpi.first.wpilibj.DriverStation;
>>>>>>> e6b2b321b3c3ba784e72a0acef123d8d32bf4b97
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.ArmCommands;
import frc.robot.commands.AutoCommands;
import frc.robot.commands.IntakeCommands;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ArmSystem;
import frc.robot.subsystems.Intake;

import frc.robot.Constants;

public class RobotContainer {
  public static ArmSystem m_arm = new ArmSystem();
  public static Intake intake = new Intake();
  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  //originally 1.5  radians per second
  private double MaxAngularRate = 1.0 * Math.PI; // 3/4 of a rotation per second max angular velocity

  private boolean slow = false;
  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandXboxController joystick1 = new CommandXboxController(0); // driver
  private final CommandXboxController joystick2 = new CommandXboxController(1); // operator
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

  public DigitalInput armLimitSwitch = new DigitalInput(9);
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

  private final Telemetry logger = new Telemetry(MaxSpeed);
  
  private final SendableChooser<Command> autoChooser;

  private void configureBindings() {
    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(-joystick1.getLeftY() * MaxSpeed * (slow?0.3:1)) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY(-joystick1.getLeftX() * MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-joystick1.getRightX() * MaxAngularRate * 0.5* (slow?0.3:1)) // Drive counterclockwise with negative X (left)
        ));

    joystick1.a().whileTrue(drivetrain.applyRequest(() -> brake));
    joystick1.b().whileTrue(drivetrain
        .applyRequest(() -> point.withModuleDirection(new Rotation2d(-joystick1.getLeftY(), -joystick1.getLeftX()))));

    // reset the field-centric heading on left bumper press
    joystick1.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    if (Utils.isSimulation()) {
      drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    }
    drivetrain.registerTelemetry(logger::telemeterize);

    // /* Bindings for drivetrain characterization */
    // /* These bindings require multiple buttons pushed to swap between quastatic and dynamic */
    // /* Back/Start select dynamic/quasistatic, Y/X select forward/reverse direction */
    // joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
    // joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
    // joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
    // joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

    Trigger intakeIn = new Trigger(joystick2.rightTrigger());
    intakeIn.whileTrue(IntakeCommands.intake());
    intakeIn.onFalse(IntakeCommands.stopIntake());

    Trigger intakeOut = new Trigger(joystick2.leftTrigger());
    intakeOut.whileTrue(IntakeCommands.outake());
    intakeOut.onFalse(IntakeCommands.stopIntake());

    // Trigger ArmUp = new Trigger(joystick2.rightBumper());
    // ArmUp.whileTrue(ArmCommands.MoveArmUpCommand());
    // ArmUp.onFalse(ArmCommands.stopArm());

    // Trigger ArmDown = new Trigger(joystick2.leftBumper());
    // ArmDown.whileTrue(ArmCommands.MoveArmDownCommand());
    // ArmDown.onFalse(ArmCommands.stopArm());
    
    Trigger moveArmUp = new Trigger(() -> joystick2.getLeftY()> 0.1);
    moveArmUp.whileTrue(ArmCommands.MoveArmUpCommand());
    Trigger moveArmDown = new Trigger(() -> joystick2.getLeftY()< -0.1);
    moveArmDown.whileTrue(ArmCommands.MoveArmDownCommand());
    Trigger moveArmUpSlow = new Trigger(() -> joystick2.getRightY()>0.1);
    moveArmUpSlow.whileTrue(ArmCommands.MoveArmUpSlowCommand());
    Trigger moveArmDownSlow = new Trigger(() -> joystick2.getRightY()<-0.1);
    moveArmDownSlow.whileTrue(ArmCommands.MoveArmDownSlowCommand());
    Trigger stopArm = new Trigger(() -> Math.abs(joystick2.getLeftY())<0.1 && Math.abs(joystick2.getRightY())<0.1);
    stopArm.whileTrue(ArmCommands.stopArm());

    Trigger armToScore = new Trigger(joystick2.rightBumper());
    armToScore.onTrue(ArmCommands.setTargetPositionCommand(Constants.scorePreset));

    Trigger armToSource = new Trigger(joystick2.b());
    armToSource.onTrue(ArmCommands.setTargetPositionCommand(Constants.sourcePreset));

    Trigger armToGround = new Trigger(joystick2.a());
    armToGround.onTrue(ArmCommands.setTargetPositionCommand(Constants.groundPreset));

      Trigger slowMode = new Trigger(joystick1.rightTrigger());
      slowMode.onTrue( new InstantCommand(()->{slow=true;}));
      slowMode.onFalse( new InstantCommand(()->{slow=false;}));
   
    
  } 

  public RobotContainer() {
    NamedCommands.registerCommand("StackBucket", ArmCommands.stackBucket());
    configureBindings();
    // NamedCommands.registerCommand("StackBucket", AutoCommands.stackBucket());
    // NamedCommands.registerCommand("ScoreBucket", AutoCommands.scoreBucket());
    
    autoChooser = AutoBuilder.buildAutoChooser();
    
    SmartDashboard.putData(autoChooser);
  }

<<<<<<< HEAD
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
=======
   public Command getAutonomousCommand() {
    try{
        // Load the path you want to follow using its name in the GUI
        PathPlannerPath path;
        var alliance = DriverStation.getAlliance();
        if (alliance.isPresent() && alliance.get() == DriverStation.Alliance.Blue) {
          path = PathPlannerPath.fromPathFile("Blue Preloaded Stack");

        } else {
          path = PathPlannerPath.fromPathFile("Red Preloaded Stack");
        }
          
        
        // Create a path following command using AutoBuilder. This will also trigger event markers.
        
        return AutoBuilder.followPath(path);
    } catch (Exception e) {
        DriverStation.reportError("Big oops: " + e.getMessage(), e.getStackTrace());
        return Commands.none();
    }
>>>>>>> e6b2b321b3c3ba784e72a0acef123d8d32bf4b97
  }
}
