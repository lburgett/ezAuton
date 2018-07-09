package com.team2502.ezauton.utils;

import com.team2502.ezauton.localization.Updateable;

import java.util.HashSet;
import java.util.Set;

/**
 * Each time progress() is called, SimulatedStopwatch increases by dt
 */
public class SimulatedStopwatch implements ICopyableStopwatch, Updateable
{

    private final double dt;
    Set<SimulatedStopwatch> related = new HashSet<>();
    float count = -1;

    public SimulatedStopwatch(double dt)
    {
        this.dt = dt;
    }

    @Override
    public double read()
    {
        return count;
    }

    /**
     * progress to next time step
     */
    public void progress()
    {
        related.forEach(SimulatedStopwatch::progress);
        if(count == -1)
        {
            count = 0;
        }
        count += dt;
    }

    /**
     * progress to next time step
     */
    public void progress(double dt)
    {
        related.forEach(simulatedStopwatch -> simulatedStopwatch.progress(dt));
        if(count == -1)
        {
            count = 0;
        }
        count += dt;
    }

    @Override
    public void reset()
    {
        count = 0;
    }

    @Override
    public boolean isInit()
    {
        return count != -1;
    }

    /**
     * @return A new SimulatedStopwatch that is linked to progress()
     */
    @Override
    public SimulatedStopwatch copy()
    {
        SimulatedStopwatch simulatedStopwatch = new SimulatedStopwatch(dt);
        simulatedStopwatch.count = count;

        related.add(simulatedStopwatch);

        return simulatedStopwatch;
    }

    @Override
    public boolean update()
    {
        progress();
        return true;
    }
}
