package com.team2502.ezauton.command;

import com.team2502.ezauton.localization.ITranslationalLocationEstimator;
import com.team2502.ezauton.pathplanning.IPathSegment;
import com.team2502.ezauton.pathplanning.Path;
import com.team2502.ezauton.pathplanning.purepursuit.ILookahead;
import com.team2502.ezauton.pathplanning.purepursuit.PurePursuitMovementStrategy;
import com.team2502.ezauton.robot.subsystems.TranslationalLocationDriveable;
import com.team2502.ezauton.trajectory.geometry.ImmutableVector;

import java.util.concurrent.TimeUnit;

/**
 * A Pure Pursuit command which can be used in simulation or as a WPILib Command
 */
public class PPCommand extends SimpleAction
{
    private final PurePursuitMovementStrategy purePursuitMovementStrategy;
    private final ITranslationalLocationEstimator translationalLocationEstimator;
    private final ILookahead lookahead;
    private final TranslationalLocationDriveable translationalLocationDriveable;

    public PPCommand(TimeUnit timeUnit, long period, PurePursuitMovementStrategy purePursuitMovementStrategy, ITranslationalLocationEstimator translationalLocationEstimator, ILookahead lookahead, TranslationalLocationDriveable translationalLocationDriveable)
    {
        super(timeUnit, period);
        this.purePursuitMovementStrategy = purePursuitMovementStrategy;
        this.translationalLocationEstimator = translationalLocationEstimator;
        this.lookahead = lookahead;
        this.translationalLocationDriveable = translationalLocationDriveable;
    }

    @Override
    public void execute()
    {
        ImmutableVector loc = translationalLocationEstimator.estimateLocation();
        ImmutableVector goalPoint = purePursuitMovementStrategy.update(loc, lookahead.getLookahead());
        Path path = purePursuitMovementStrategy.getPath();
        IPathSegment current = path.getCurrent();
        ImmutableVector closestPoint = current.getClosestPoint(loc);
        double absoluteDistance = current.getAbsoluteDistance(closestPoint);
        double speed = current.getSpeed(absoluteDistance);
        translationalLocationDriveable.driveTowardTransLoc(speed, goalPoint);
    }

    @Override
    public boolean isFinished()
    {
        if(purePursuitMovementStrategy.isFinished() || getStopwatch().read(TimeUnit.SECONDS) > 5)
        {
            translationalLocationDriveable.driveSpeed(0);
            return true;
        }
        return false;
    }
}
