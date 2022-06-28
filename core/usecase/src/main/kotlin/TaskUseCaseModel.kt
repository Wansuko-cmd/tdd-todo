import TaskPhaseUseCaseModel.Companion.toUseCaseModel
import task.Task
import task.TaskPhase

data class TaskUseCaseModel(
    val id: String,
    val title: String,
    val description: String,
    val phase: TaskPhaseUseCaseModel,
) {
    companion object {
        fun Task.toUseCaseModel() = TaskUseCaseModel(
            id = id.value,
            title = title.value,
            description = description.value,
            phase = phase.toUseCaseModel(),
        )
    }
}

enum class TaskPhaseUseCaseModel {
    Todo,
    Red,
    Green,
    Refactor,
    Done;

    companion object {
        fun TaskPhase.toUseCaseModel() = when (this) {
            TaskPhase.Todo -> Todo
            TaskPhase.Red -> Red
            TaskPhase.Green -> Green
            TaskPhase.Refactor -> Refactor
            TaskPhase.Done -> Done
        }
    }
}
