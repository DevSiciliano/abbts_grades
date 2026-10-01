package local.noto.grades.calculation

// Outcome of the promotion check for one study year (CR-5 to CR-7)
// A year is promoted if no failure was found
data class PromotionResult(
    val earnedCreditPoints: Int,
    val requiredCreditPoints: Int,
    val failures: List<PromotionFailure>,
) {
    val isPromoted: Boolean get() = failures.isEmpty()
}
