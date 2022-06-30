package dto.task

import task.Task
import task.TaskPhase

data class TaskUseCaseDto(
    val id: String,
    val title: String,
    val description: String,
    val phase: TaskPhase,
) {
    companion object {
        fun Task.toUseCaseDto() = TaskUseCaseDto(
            id = id.value,
            title = title.value,
            description = description.value,
            phase = phase,
        )
    }
}
