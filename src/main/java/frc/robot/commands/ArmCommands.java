package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.RobotContainer;

public class ArmCommands {
    public static Command MoveArmUpCommand = new RunCommand(
        ()->RobotContainer.m_arm.moveUp(),RobotContainer.m_arm
    );

    public static Command MoveArmUpCommand() {
        return new RunCommand(
            ()->RobotContainer.m_arm.moveUp(),
            RobotContainer.m_arm
        );
    }

    public static Command MoveArmDownCommand() {
        return new RunCommand(
            ()->RobotContainer.m_arm.moveDown(),
            RobotContainer.m_arm
        );
    }

    public static Command setArmCommand(double angle) {
        return new RunCommand(
            ()->RobotContainer.m_arm.setAngle(angle),
            RobotContainer.m_arm
        );
    }

    public static Command stopArm() {
        return new InstantCommand(
            ()->RobotContainer.m_arm.stopMotor(),RobotContainer.m_arm
        );
    }
}