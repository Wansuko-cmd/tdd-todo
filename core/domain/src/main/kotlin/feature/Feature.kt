package feature

import task.TaskId
import java.util.*

class Feature private constructor(
    val id: FeatureId,
    val title: FeatureTitle,
    val description: FeatureDescription,
    val phase: FeaturePhase,
    val tasks: List<TaskId>,
) {
    fun changeTitle(title: FeatureTitle) =
        reconstruct(
            id = this.id,
            title = title,
            description = this.description,
            phase = this.phase,
            tasks = this.tasks,
        )

    fun changeDescription(description: FeatureDescription) =
        reconstruct(
            id = this.id,
            title = this.title,
            description = description,
            phase = this.phase,
            tasks = this.tasks,
        )

    fun changePhase(phase: FeaturePhase) =
        reconstruct(
            id = this.id,
            title = this.title,
            description = this.description,
            phase = phase,
            tasks = this.tasks,
        )

    companion object {
        fun create(title: FeatureTitle, description: FeatureDescription) =
            Feature(
                id = FeatureId(UUID.randomUUID().toString()),
                title = title,
                description = description,
                phase = FeaturePhase.Todo,
                tasks = listOf(),
            )

        fun reconstruct(
            id: FeatureId,
            title: FeatureTitle,
            description: FeatureDescription,
            phase: FeaturePhase,
            tasks: List<TaskId>,
        ) = Feature(
            id = id,
            title = title,
            description = description,
            phase = phase,
            tasks = tasks,
        )
    }
}

@JvmInline
value class FeatureId(val value: String)

@JvmInline
value class FeatureTitle(val value: String)

@JvmInline
value class FeatureDescription(val value: String)
