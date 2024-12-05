package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.configs.HardwareLimitSwitchConfigs;

public class ArmSystem extends SubsystemBase{
    public TalonFX armMotor;
    private TalonFXConfiguration config = new TalonFXConfiguration();
    private HardwareLimitSwitchConfigs limitSwitchConfigs = new HardwareLimitSwitchConfigs();

    private double reverse = 1;
    private double offset=0;
    public ArmSystem() {
        armMotor = new TalonFX(20, "Canivore1");
        limitSwitchConfigs.ForwardLimitAutosetPositionEnable = true;
        limitSwitchConfigs.ForwardLimitAutosetPositionValue = 0;
        limitSwitchConfigs.ForwardLimitEnable = false;
        limitSwitchConfigs.ForwardLimitType =  ForwardLimitTypeValue.NormallyClosed;
        //!change deviceId as needed!
        this.armMotor.setNeutralMode(NeutralModeValue.Brake);
        this.armMotor.setInverted(true);
        
        
        Slot0Configs slot0 = new Slot0Configs();
        slot0.kP = 0.07;
        slot0.kI = 0.1;
        config.CurrentLimits.SupplyCurrentLimitEnable=true;
        config.CurrentLimits.SupplyCurrentLimit=20;
        config.withHardwareLimitSwitch(limitSwitchConfigs);
        config.withSlot0(slot0);
        armMotor.getConfigurator().apply(config);
    
    }
    
    public void moveUp() {
        this.armMotor.set(-0.5*reverse);
    }

    public void moveDown() {
        this.armMotor.set(0.5*reverse);
    }

    public void moveUpSlow() {
        this.armMotor.set(-0.2*reverse);
    }

    public void moveDownSlow(){
        this.armMotor.set(0.2*reverse);
    }

    public void changeOffset(){
        offset=-armMotor.getPosition().getValueAsDouble(); 
    }
    public void stopMotor() {
        if(armMotor.getPosition().getValueAsDouble()>55){
            reverse=-1;
        }else{
            reverse=1;
        }
        this.armMotor.set(0);
    }

    public void setAngle(double angle) {
        this.armMotor.set(angle);
    }



    public double getPosition() {
        return this.armMotor.getPosition().getValue()+offset;
    }

    public double getVelocity(){
        return this.armMotor.getVelocity().getValue();
    }

    public void setTargetPosition(double targetPosition) {
        PositionDutyCycle positionControl = new PositionDutyCycle(targetPosition+offset, 0.5, false, 0.5, 0, false, false, false);
        armMotor.setControl(positionControl); // Position control
    }

    public void setTargetVelocity(double targetVelocity) {
        VelocityDutyCycle velocityControl = new VelocityDutyCycle(targetVelocity);
        armMotor.setControl(velocityControl); // Velocity control
    }
}
