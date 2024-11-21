package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSystem extends SubsystemBase{
    private TalonFX armMotor;
    public ArmSystem() {
        armMotor=new TalonFX(1);
        //!change deviceId as needed!
        this.armMotor.setNeutralMode(NeutralModeValue.Brake);
        this.armMotor.setInverted(true);
    }

    public void moveUp() {
        this.armMotor.set(0.1);
    }

    public void moveDown() {
        this.armMotor.set(-0.1);
    }

    public void stopMotor() {
        this.armMotor.set(0);
    }

}