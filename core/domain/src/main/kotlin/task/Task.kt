package task

import java.util.*

data class Task private constructor(
    val id: TaskId = TaskId(UUID.randomUUID().toString()),
    val title: TaskTitle,
    val description: TaskDescription,
    val phase: TaskPhase = TaskPhase.Todo,
) {
    fun changePhase(phase: TaskPhase): Task =
        Task(
            id = this.id,
            title = this.title,
            description = this.description,
            phase = phase
        )

    companion object {
        fun create(title: TaskTitle, description: TaskDescription) =
            Task(
                id = TaskId(UUID.randomUUID().toString()),
                title = title,
                description = description,
                phase = TaskPhase.Todo,
            )

        fun reconstruct(
            id: TaskId,
            title: TaskTitle,
            description: TaskDescription,
            phase: TaskPhase,
        ) = Task(
            id = id,
            title = title,
            description = description,
            phase = phase,
        )
    }
}

@JvmInline
value class TaskId(val value: String)

@JvmInline
value class TaskTitle(val value: String)

@JvmInline
value class TaskDescription(val value: String)
