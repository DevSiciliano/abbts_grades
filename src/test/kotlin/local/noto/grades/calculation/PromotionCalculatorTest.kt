package local.noto.grades.calculation

import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PromotionCalculatorTest {

    @Test
    fun `all groups have at least 4_0 graded`() {
        val groups = listOf(
            listOf(ModuleResult(BigDecimal("4.5"), 4), ModuleResult(BigDecimal("4.0"), 4)),
            listOf(ModuleResult(BigDecimal("5.0"), 4), ModuleResult(BigDecimal("4.0"), 2))
        )

        val result = PromotionCalculator.evaluate(groups, requiredCreditPoints = 14)

        assertTrue(result.isPromoted)
        assertEquals(PromotionStatus.PROMOTED, result.status)
        assertEquals(14, result.earnedCreditPoints)
    }

    @Test
    fun `less than 4_0 but CP achieved`() {
        val groups = listOf(
            listOf(ModuleResult(BigDecimal("3.5"), 4), ModuleResult(BigDecimal("4.0"), 4)),
            listOf(ModuleResult(BigDecimal("5.5"), 4), ModuleResult(BigDecimal("4.5"), 2)),
        )

        val result = PromotionCalculator.evaluate(groups, requiredCreditPoints = 10)

        assertTrue(result.isPromoted)
        assertEquals(PromotionStatus.PROMOTED_WITH_CONDITIONS, result.status)
        assertEquals(10, result.earnedCreditPoints)
    }

    @Test
    fun `Promotion failed`() {
        val groups = listOf(
            listOf(ModuleResult(BigDecimal("3.5"), 4), ModuleResult(BigDecimal("3.0"), 4)),
            listOf(ModuleResult(BigDecimal("3.5"), 4), ModuleResult(BigDecimal("2.5"), 2))
        )

        val result = PromotionCalculator.evaluate(groups, requiredCreditPoints = 10)

        assertFalse(result.isPromoted)
        assertEquals(PromotionStatus.NOT_PROMOTED, result.status)
        assertEquals(0, result.earnedCreditPoints)
    }

}