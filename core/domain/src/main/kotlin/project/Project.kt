package project

import feature.FeatureId
import java.util.*

data class Project(
    val id: ProjectId = ProjectId(UUID.randomUUID().toString()),
    val title: ProjectTitle,
    val description: ProjectDescription,
    val features: List<FeatureId> = listOf()
) {
    companion object {
        fun create(
            title: ProjectTitle,
            description: ProjectDescription,
        ) = Project(
            id = ProjectId(UUID.randomUUID().toString()),
            title = title,
            description = description,
            features = listOf()
        )
    }
}

@JvmInline
value class ProjectId(val value: String)

@JvmInline
value class ProjectTitle(val value: String)

@JvmInline
value class ProjectDescription(val value: String)
