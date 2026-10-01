package local.noto.grades.calculation

// Outcome of the promotion check for one study year (CR-5 to CR-7)
// A year is promoted if no failure was found
data class PromotionResult(
    val earnedCreditPoints: Int,
    val requiredCreditPoints: Int,
    val failures: List<PromotionFailure>,
) {
    val isPromoted: Boolean get() {
        if (failures.size > 1) return false
        else return true
    }

    val status: PromotionStatus get() {
        val averageMissed = failures.any { it is PromotionFailure.GroupAverageTooLow }
        val creditPointsMissed = failures.any { it is PromotionFailure.MissingCreditPoints }
        val previousYearFailed = failures.any { it is PromotionFailure.PreviousYearNotPromoted }

        if (previousYearFailed) return PromotionStatus.NOT_PROMOTED

        return when (listOf(averageMissed, creditPointsMissed).count { it }) {
            0 -> PromotionStatus.PROMOTED
            1 -> PromotionStatus.PROMOTED_WITH_CONDITIONS
            else -> PromotionStatus.NOT_PROMOTED
        }
    }
}
