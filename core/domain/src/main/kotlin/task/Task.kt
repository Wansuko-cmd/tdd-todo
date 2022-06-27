package task

class Task(
    private val id: String,
    private val title: String,
    private val description: String,
) {
    override fun equals(other: Any?): Boolean {
        return id == (other as? Task)?.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}
