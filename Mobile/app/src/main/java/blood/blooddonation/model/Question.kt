package blood.blooddonation.model

data class Question(
    val text: String,
    val answers: List<Answer>
)
