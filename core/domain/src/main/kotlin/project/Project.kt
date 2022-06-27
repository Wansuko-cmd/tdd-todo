package project

import java.util.*

data class Project(
    val id: ProjectId = ProjectId(UUID.randomUUID().toString()),
    val title: ProjectTitle,
    val description: ProjectDescription,
)

@JvmInline
value class ProjectId(val value: String)

@JvmInline
value class ProjectTitle(val value: String)

@JvmInline
value class ProjectDescription(val value: String)
