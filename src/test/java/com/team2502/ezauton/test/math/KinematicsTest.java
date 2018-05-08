package com.team2502.ezauton.test.math;

import org.joml.ImmutableVector;
import org.junit.Assert;
import org.junit.Test;
import com.team2502.ezauton.utils.MathUtils;

import static com.team2502.ezauton.utils.MathUtils.ROOT_2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KinematicsTest
{
    @Test
    public void testNavXBound()
    {
        assertEquals(355, MathUtils.Kinematics.navXBound(-5), 0.001);
        assertEquals(14, MathUtils.Kinematics.navXBound(14), 0.001);
    }

    @Test //fail
    public void testAbsoluteDPos45()
    {
        ImmutableVector dPos = MathUtils.Kinematics.getAbsoluteDPosLine(1, 1, 1F, (double) (Math.PI / 4F));

        assertEquals(Math.sqrt(1 / 2F), dPos.x, 0.001);
        assertEquals(Math.sqrt(1 / 2F), dPos.y, 0.001);
    }

    @Test
    public void navXToRad()
    {
        // TODO: returns cw radians not ccw I think
        double rad = MathUtils.Kinematics.navXToRad(270);

        assertEquals(Math.PI / 2F, rad, 0.001);

        rad = MathUtils.Kinematics.navXToRad(270 + 360);
        assertEquals(Math.PI / 2F, rad, 0.001);

        rad = MathUtils.Kinematics.navXToRad(270 - 360);
        assertEquals(Math.PI / 2F, rad, 0.001);
    }

    /**
     * Should be a complete rotation around a circle (dpos = 0)
     */
    @Test
    public void arcDposArcStraight0Heading()
    {
        // l * pi = 1 (circumference)
        // 1/pi = l
        ImmutableVector absoluteDPosCurve = MathUtils.Kinematics.getAbsoluteDPosCurve(1, 1, 123, 1, 0);
        assertEquals(0, absoluteDPosCurve.x, 1);
        assertEquals(0, absoluteDPosCurve.y, 1);
    }

    @Test
    public void arcDposArcStraight45Heading()
    {
        // l * pi = 1 (circumference)
        // 1/pi = l
        ImmutableVector absoluteDPosCurve = MathUtils.Kinematics.getAbsoluteDPosCurve(1, 1, 123, 1, Math.PI / 4F);
        assertEquals(-Math.sqrt(1 / 2F), absoluteDPosCurve.x, 0.001);
        assertEquals(Math.sqrt(1 / 2F), absoluteDPosCurve.y, 0.001);
    }

    @Test
    public void testAbsoluteToRelativeCoord()
    {
        ImmutableVector robotPos = new ImmutableVector(4, 4);

        // We will convert this to absolute coords
        ImmutableVector targetPos = robotPos;

        assertEquals(new ImmutableVector(0, 0), MathUtils.LinearAlgebra.absoluteToRelativeCoord(targetPos, robotPos, 0));
        assertEquals(new ImmutableVector(0, 0), MathUtils.LinearAlgebra.absoluteToRelativeCoord(targetPos, robotPos, 35));
        assertEquals(new ImmutableVector(0, 0), MathUtils.LinearAlgebra.absoluteToRelativeCoord(targetPos, robotPos, -180));

        targetPos = new ImmutableVector(5, 5);

        // (1, 1)
        vectorsCloseEnough(new ImmutableVector(1, 1), MathUtils.LinearAlgebra.absoluteToRelativeCoord(targetPos, robotPos, 0));
        vectorsCloseEnough(new ImmutableVector(-1, -1), MathUtils.LinearAlgebra.absoluteToRelativeCoord(targetPos, robotPos, -Math.PI));
        vectorsCloseEnough(new ImmutableVector(ROOT_2, 0), MathUtils.LinearAlgebra.absoluteToRelativeCoord(targetPos, robotPos, Math.PI / 4));
    }

    @Test
    public void testGetSpeedVector()
    {
        ImmutableVector i = new ImmutableVector(1, 0);
        ImmutableVector j = new ImmutableVector(0, 1);

        vectorsCloseEnough(i, MathUtils.Geometry.getVector(1, 0));
        vectorsCloseEnough(j, MathUtils.Geometry.getVector(1, Math.PI / 2));
        vectorsCloseEnough(i.add(j), MathUtils.Geometry.getVector(ROOT_2, Math.PI / 4));

    }

    @Test
    public void testGetPos()
    {
        double standstill = MathUtils.Kinematics.getPos(10, 0, 0, 100);
        assertEquals(10,standstill,1E-6);

        double noAccel = MathUtils.Kinematics.getPos(10, 10, 0, 100); // 10 + 10*100
        assertEquals(10 + 10*100,noAccel,1E-6);

        double accel = MathUtils.Kinematics.getPos(0, 0, 1, 2); // 1/2*1*2^2
        assertEquals(1/2F*1*2*2,accel,1E-6);
    }

    @Test
    public void testAngularVelocity()
    {
        // straight
        assertEquals(0,MathUtils.Kinematics.getAngularVel(1,1,1),1E-6);
        assertEquals(0,MathUtils.Kinematics.getAngularVel(0,0,1),1E-6);

        assertTrue(MathUtils.Kinematics.getAngularVel(0,1,1) > 0);
        assertTrue(MathUtils.Kinematics.getAngularVel(1,0,1) < 0);
    }

    @Test
    public void testTrajRadius()
    {
        assertTrue(MathUtils.Kinematics.getTrajectoryRadius(0,1,1) > 0);
        assertTrue(MathUtils.Kinematics.getTrajectoryRadius(1,0,1) < 0);
    }

    @Test
    public void testRelativeDPosCurve()
    {
        // straight
        vectorsCloseEnough(new ImmutableVector(0,1),MathUtils.Kinematics.getRelativeDPosCurve(1,1,1,1));

        // full circle
        vectorsCloseEnough(new ImmutableVector(0,0),MathUtils.Kinematics.getRelativeDPosCurve(Math.PI,0,1/2F,1));

        vectorsCloseEnough(new ImmutableVector(0.5,0),MathUtils.Kinematics.getRelativeDPosCurve(Math.PI/2,0,1/2F,1));
    }

    private void vectorsCloseEnough(ImmutableVector a, ImmutableVector b)
    {
        Assert.assertEquals(a.x, b.x, 1E-3);
        Assert.assertEquals(a.y, b.y, 1E-3);
    }

}
