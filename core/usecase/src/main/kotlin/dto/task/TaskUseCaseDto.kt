package dto.task

import task.*

data class TaskUseCaseDto(
    val id: TaskId,
    val title: TaskTitle,
    val description: TaskDescription,
    val phase: TaskPhase,
) {
    companion object {
        internal fun Task.toUseCaseDto() = TaskUseCaseDto(
            id = id,
            title = title,
            description = description,
            phase = phase,
        )
    }
}
