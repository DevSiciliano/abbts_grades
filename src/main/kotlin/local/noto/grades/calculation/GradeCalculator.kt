package local.noto.grades.calculation

import java.math.BigDecimal
import java.math.RoundingMode

data class ModuleResult(val grade: BigDecimal?, val creditPoints: Int)

object GradeCalculator {
    private val PASSING_GRADE = BigDecimal("4.0")
    private val TWO = BigDecimal(2)

    // Round grades to half values (e.g. 4.0, 4.5, 5.0)
    fun roundToHalf(grade: BigDecimal): BigDecimal =
        grade.multiply(TWO)
            .setScale(0, RoundingMode.HALF_UP)
            .divide(TWO)
            .setScale(1)

    // Passed, if the rounded grade
    fun isPassed(grade: BigDecimal): Boolean =
        roundToHalf(grade) >= PASSING_GRADE

    fun earnedCreditPoints(modules: List<ModuleResult>): Int =
        modules
            .filter { it.grade != null && isPassed(it.grade) }
            .sumOf { it.creditPoints }

    fun groupAverage(modules: List<ModuleResult>): BigDecimal? {
        val grades = modules.mapNotNull { it.grade }
        if(grades.isEmpty()) return null

        return grades
            .sumOf { it }
            .divide(grades.size.toBigDecimal(), 1, RoundingMode.HALF_UP)
    }
}