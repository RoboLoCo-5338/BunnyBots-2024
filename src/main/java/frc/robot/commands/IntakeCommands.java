package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;

public class IntakeCommands {
    public static Command indexForward() {//creates the forward index command
        return new InstantCommand(
          () -> RobotContainer.intake.indexForward(),
          RobotContainer.intake
        );
    }
    public static Command indexReverse() {//creates the  reverse index command
        return new InstantCommand(
			() -> RobotContainer.intake.indexReverse(),
			RobotContainer.intake
		);
    }
    public static Command intake() {//creates the intake command
        return new InstantCommand(
			() -> RobotContainer.intake.intake(),
			RobotContainer.intake
		);
    }
    public static Command outake() {//creates the outake command
        return new InstantCommand(
			() -> RobotContainer.intake.outake(),
			RobotContainer.intake
		);
    }
    public static Command stopIndex() {//creates the stop index command
        return new InstantCommand(
			() -> RobotContainer.intake.stopIndex(),
			RobotContainer.intake
		);
    }
    public static Command stopIntake() {//creates the stop intake command
        return new InstantCommand(
			() -> RobotContainer.intake.stopIntake(),
			RobotContainer.intake
		);
    }
    public static Command stopIntakeMotors() {
        return new InstantCommand(
          () -> RobotContainer.intake.stopIntakeMotors(),
          RobotContainer.intake
        );
    }
    public static Command intakeIndexForward() {//creates the intake + forward index command
        return new InstantCommand(
			() -> RobotContainer.intake.intakeIndexForward(),
			RobotContainer.intake
		);
    }  
    public static Command outakeIndexReverse() {//creates the outake + reverse index command
        return new InstantCommand(
			() -> RobotContainer.intake.outakeIndexReverse(),
			RobotContainer.intake
		);
    }
}
