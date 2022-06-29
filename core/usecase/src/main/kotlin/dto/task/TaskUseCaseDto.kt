package dto.task

import dto.task.TaskPhaseUseCaseDto.Companion.toUseCaseDto
import task.Task
import task.TaskPhase

data class TaskUseCaseDto(
    val id: String,
    val title: String,
    val description: String,
    val phase: TaskPhaseUseCaseDto,
) {
    companion object {
        fun Task.toUseCaseDto() = TaskUseCaseDto(
            id = id.value,
            title = title.value,
            description = description.value,
            phase = phase.toUseCaseDto(),
        )
    }
}

enum class TaskPhaseUseCaseDto {
    Todo,
    Red,
    Green,
    Refactor,
    Done;

    companion object {
        fun TaskPhase.toUseCaseDto() = when (this) {
            TaskPhase.Todo -> Todo
            TaskPhase.Red -> Red
            TaskPhase.Green -> Green
            TaskPhase.Refactor -> Refactor
            TaskPhase.Done -> Done
        }
        fun TaskPhaseUseCaseDto.toDomain() = when (this) {
            Todo -> TaskPhase.Todo
            Red -> TaskPhase.Red
            Green -> TaskPhase.Green
            Refactor -> TaskPhase.Refactor
            Done -> TaskPhase.Done
        }
    }
}
