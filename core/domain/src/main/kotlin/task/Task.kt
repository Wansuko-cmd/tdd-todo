package task

data class Task(
    private val id: TaskId,
    private val title: TaskTitle,
    private val description: TaskDescription,
)

@JvmInline
value class TaskId(val value: String)

@JvmInline
value class TaskTitle(val value: String)

@JvmInline
value class TaskDescription(val value: String)
