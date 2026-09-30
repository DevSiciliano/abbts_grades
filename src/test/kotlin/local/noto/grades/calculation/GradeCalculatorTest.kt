package local.noto.grades.calculation

import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GradeCalculatorTest {

    @Test
    fun `3_8 rounds up to 4_0 and passes`() {
        assertEquals(BigDecimal("4.0"), GradeCalculator.roundToHalf(BigDecimal("3.8")))
        assertTrue(GradeCalculator.isPassed(BigDecimal("3.8")))
    }

    @Test
    fun `3_7 rounds down to 3_5 and fails`() {
        assertEquals(BigDecimal("3.5"), GradeCalculator.roundToHalf(BigDecimal("3.7")))
        assertFalse(GradeCalculator.isPassed(BigDecimal("3.7")))
    }
}