package update

import com.wsr.apiresult.*
import dto.task.TaskQueryService
import task.TaskId
import task.TaskPhase
import task.TaskRepository
import task.TaskTitle

class UpdateTaskPhaseUseCase(
    taskQueryService: TaskQueryService,
    taskRepository: TaskRepository,
) {

    private val updateTaskUseCase = UpdateTaskUseCase(taskQueryService, taskRepository)
    suspend operator fun invoke(
        taskId: TaskId,
        phase: TaskPhase,
    ): ApiResult<Unit, UpdateTaskPhaseUseCaseException> =
        updateTaskUseCase.update(taskId) { task -> task.copyWithPhase(phase) }
}

sealed class UpdateTaskPhaseUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : UpdateTaskPhaseUseCaseException()
}
