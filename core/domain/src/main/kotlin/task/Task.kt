package task

import feature.FeatureId
import java.util.*

data class Task(
    val id: TaskId = TaskId(UUID.randomUUID().toString()),
    val featureId: FeatureId,
    val title: TaskTitle,
    val description: TaskDescription,
    val phase: TaskPhase = TaskPhase.Todo,
) {
    fun copyWithPhase(newPhase: TaskPhase): Task = copy(phase = newPhase)

    companion object {
        fun create(title: TaskTitle, description: TaskDescription) =
            Task(
                id = TaskId(UUID.randomUUID().toString()),
                featureId = FeatureId(""),
                title = title,
                description = description,
                phase = TaskPhase.Todo,
            )
    }
}

@JvmInline
value class TaskId(val value: String)

@JvmInline
value class TaskTitle(val value: String)

@JvmInline
value class TaskDescription(val value: String)
