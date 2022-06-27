package task

data class Task(
    private val id: TaskId,
    val featureId: FeatureId,
    val title: TaskTitle,
    val description: TaskDescription,
    val phase: TaskPhase = TaskPhase.Todo,
)

@JvmInline
value class TaskId(val value: String)

@JvmInline
value class TaskTitle(val value: String)

@JvmInline
value class TaskDescription(val value: String)

@JvmInline
value class FeatureId(val value: String)
