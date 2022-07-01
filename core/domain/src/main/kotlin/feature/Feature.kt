package feature

import project.ProjectId
import task.TaskId
import java.util.*

data class Feature(
    val id: FeatureId = FeatureId(UUID.randomUUID().toString()),
    val projectId: ProjectId,
    val title: FeatureTitle,
    val description: FeatureDescription,
    val phase: FeaturePhase = FeaturePhase.Todo,
    val tasks: List<TaskId> = listOf()
) {
    fun copyWithPhase(newPhase: FeaturePhase): Feature = copy(phase = newPhase)

    companion object {
        fun create(title: FeatureTitle, description: FeatureDescription) =
            Feature(
                id = FeatureId(UUID.randomUUID().toString()),
                projectId = ProjectId(""),
                title = title,
                description = description,
                phase = FeaturePhase.Todo,
                tasks = listOf(),
            )
    }
}

@JvmInline
value class FeatureId(val value: String)

@JvmInline
value class FeatureTitle(val value: String)

@JvmInline
value class FeatureDescription(val value: String)
