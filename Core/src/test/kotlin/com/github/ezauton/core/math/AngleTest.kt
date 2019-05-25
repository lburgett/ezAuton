package com.github.ezauton.core.math

import com.github.ezauton.core.utils.MathUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AngleTest {

    @Test
    fun `test angle simplification`() {
        assertEquals(0.0, MathUtils.Geometry.simplifyAngleCentered0(0.0))
        assertEquals(-Math.PI / 2.0, MathUtils.Geometry.simplifyAngleCentered0(Math.PI * 3.0 / 2.0))
        assertEquals(-Math.PI / 2.0, MathUtils.Geometry.simplifyAngleCentered0(Math.PI * 3.0 / 2.0 - 2 * Math.PI))
        assertEquals(Math.PI / 2.0, MathUtils.Geometry.simplifyAngleCentered0(Math.PI * 1.0 / 2.0))
    }
}
