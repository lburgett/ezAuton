package com.github.ezauton.core.actuators;

/**
 * A motor which can be run at a certain voltage
 */
public interface VoltageMotor extends Motor {
    void runVoltage(double targetVoltage);
}
