package com.github.ezauton.core.test.simulator;

import com.github.ezauton.core.action.TimedPeriodicAction;
import com.github.ezauton.core.action.IAction;
import com.github.ezauton.core.action.simulation.ModernSimulatedClock;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

public class InsantSimulatorTest
{
    @Test
    public void testABC() throws TimeoutException {

        AtomicLong sum = new AtomicLong();

        IAction actionA = new TimedPeriodicAction(20, TimeUnit.SECONDS)
                .addUpdateable(a -> () -> {
                    sum.addAndGet(a.getStopwatch().read());
                });

        IAction actionB = new TimedPeriodicAction(20, TimeUnit.SECONDS)
                .addUpdateable(a -> () -> {
                    long l = sum.addAndGet(-a.getStopwatch().read(TimeUnit.MILLISECONDS));
                    Assert.assertEquals(0, l);
                });

        ModernSimulatedClock clock = new ModernSimulatedClock();

        clock
                .add(actionA)
                .add(actionB)
                .runSimulation(1000, TimeUnit.SECONDS);

        Assert.assertEquals(0, sum.get());
    }
}