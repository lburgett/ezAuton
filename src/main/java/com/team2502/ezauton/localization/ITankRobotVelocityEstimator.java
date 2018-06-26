package com.team2502.ezauton.localization;

import com.team2502.ezauton.trajectory.geometry.ImmutableVector;

/**
 * Interface for any class that will estimate position details of our robot
 */
public interface ITankRobotVelocityEstimator
{
    /**
     * @return The absolute velocity of the robot
     */
    ImmutableVector estimateAbsoluteVelocity();

    /**
     * @return Velocity of the left wheel. Can be negative or positive.
     */
    double getLeftTranslationalWheelVelocity();

    /**
     * @return Velocity of the right wheel. Can be negative or positive.
     */
    double getRightTranslationalWheelVelocity();

    /**
     * @return Average velocity of both wheels. This will be the tangential velocity of the robot
     * if it is a normal tank robot.
     */
    default double getAvgTranslationalWheelVelocity()
    {
        return (getLeftTranslationalWheelVelocity() + getRightTranslationalWheelVelocity()) / 2D;
    }

    /**
     * @return The average wheel speed. NOTE: this will always be positive and can be non-zero even
     * if the robot has 0 translational velocity.
     */
    default double getAvgTranslationalWheelSpeed()
    {
        return (Math.abs(getLeftTranslationalWheelVelocity()) + Math.abs(getRightTranslationalWheelVelocity())) / 2F;
    }
}
