package local.noto.grades.calculation

import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
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

    @Test
    fun `only passed modules earn credit points`() {
        val modules = listOf(
            ModuleResult(BigDecimal("3.7"), 4), // --> Not passed 0CP
            ModuleResult(BigDecimal("4.5"), 4), // --> Passed 4CP
            ModuleResult(BigDecimal("3.8"), 2),// --> Passed 2CP
            ModuleResult(null, 2) // --> Ignored because no grade found
        )

        assertEquals(6, GradeCalculator.earnedCreditPoints(modules))
    }

    @Test
    fun `group average is simple mean of graded modules`() {
        val modules = listOf(
            ModuleResult(BigDecimal("3.7"), 4),
            ModuleResult(BigDecimal("4.5"), 4),
            ModuleResult(BigDecimal("3.8"), 2),
            ModuleResult(null, 2) // Should be ignored because of null for grade
        )

        assertEquals(BigDecimal("4.0"), GradeCalculator.groupAverage(modules))
    }

    @Test
    fun `group average is null when no module is graduaded`() {
        val modules = listOf(ModuleResult(null, 4))
        assertNull(GradeCalculator.groupAverage(modules))
    }

    @Test
    fun `group average is rounded half up to one decimal`() {
        val modules = listOf(
            ModuleResult(BigDecimal("3.9"), 4),
            ModuleResult(BigDecimal("4.0"), 4)
        )

        assertEquals(BigDecimal("4.0"), GradeCalculator.groupAverage(modules))
    }

    @Test
    fun `no modules earn zero credit points`() {
        val modules = emptyList<ModuleResult>()

        assertEquals(0, GradeCalculator.earnedCreditPoints(modules))
    }

    @Test
    fun `3_75 rounds up to 4_0`() {
        assertEquals(BigDecimal("4.0"), GradeCalculator.roundToHalf(BigDecimal("3.75")))
    }

    @Test
    fun `3_74 rounds down to 3_5`() {
        assertEquals(BigDecimal("3.5"), GradeCalculator.roundToHalf(BigDecimal("3.74")))
    }

}