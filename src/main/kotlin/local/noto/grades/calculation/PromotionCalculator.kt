package local.noto.grades.calculation

object PromotionCalculator {

    private fun checkGroupAverages(groups: List<List<ModuleResult>>): List<PromotionFailure> =
        groups.mapIndexedNotNull { index, group ->
            val avg = GradeCalculator.groupAverage(group)

            if (avg != null && avg >= GradeCalculator.PASSING_GRADE) {
                null
            } else {
                PromotionFailure.GroupAverageTooLow(index, avg)
            }
        }

    private fun checkCreditPoints(earned: Int, required: Int): PromotionFailure? =
        if (earned < required) {
            PromotionFailure.MissingCreditPoints(earned, required)
        } else {
            null
        }

    fun evaluate(
        groups: List<List<ModuleResult>>,
        requiredCreditPoints: Int,
        previousYearPromoted: Boolean = true): PromotionResult {
        val earned = GradeCalculator.earnedCreditPoints(groups.flatten())

        val failures = buildList {
            addAll(checkGroupAverages(groups))
            checkCreditPoints(earned, requiredCreditPoints)?.let { add(it) }
            if (!previousYearPromoted) add(PromotionFailure.PreviousYearNotPromoted)
        }

        return PromotionResult(earned, requiredCreditPoints, failures)
    }

}