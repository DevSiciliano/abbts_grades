package local.noto.grades.calculation

import java.math.BigDecimal

// Reasons why a study year is not promoted
sealed interface PromotionFailure {

    // group average below 4.0, average is null if no module is graded yet
    data class GroupAverageTooLow(val groupIndex: Int, val average: BigDecimal?) : PromotionFailure

    // earned CP of the year below the minimum
    data class MissingCreditPoints(val earned: Int, val required: Int) : PromotionFailure

    // previous study year was not promoted
    data object PreviousYearNotPromoted : PromotionFailure
}
