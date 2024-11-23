package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSystem extends SubsystemBase{
    private TalonFX armMotor;
    public ArmSystem() {
        armMotor=new TalonFX(20, "Canivore1");
        //!change deviceId as needed!
        this.armMotor.setNeutralMode(NeutralModeValue.Brake);
        this.armMotor.setInverted(true);
    }

    public void moveUp() {
        this.armMotor.set(0.3);
    }

    public void moveDown() {
        this.armMotor.set(-0.3);
    }

    public void stopMotor() {
        this.armMotor.set(0);
    }

    public void setAngle(double angle) {
        this.armMotor.set(angle);
    }

}
