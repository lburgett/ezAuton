package com.team2502.ezauton.localization.sensors;

/**
 * A sensor which can measure revolutions / s (but not position)
 */
public interface ITachometer extends ISensor
{
    /**
     * @return revolutions / s
     */
    double getVelocity();
}
