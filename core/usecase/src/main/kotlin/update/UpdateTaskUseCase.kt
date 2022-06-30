package update

import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.flatMap
import com.wsr.apiresult.mapBoth
import com.wsr.apiresult.mapFailure
import dto.task.TaskQueryService
import task.*

class UpdateTaskUseCase(
    private val taskQueryService: TaskQueryService,
    private val taskRepository: TaskRepository,
) {

    suspend operator fun invoke(
        taskId: TaskId,
        title: TaskTitle,
    ): ApiResult<Unit, UpdateTaskUseCaseException> =
        update(taskId) { task -> task.copy(title = title) }

    suspend operator fun invoke(
        taskId: TaskId,
        phase: TaskPhase,
    ): ApiResult<Unit, UpdateTaskUseCaseException> =
        update(taskId) { task -> task.copyWithPhase(phase) }


    private suspend fun update(taskId: TaskId, newTaskBuilder: (Task) -> Task) =
        taskQueryService.get(taskId)
            .mapBoth(
                success = newTaskBuilder,
                failure = { UpdateTaskUseCaseException.DatabaseException(it.message) },
            )
            .flatMap { task ->
                taskRepository
                    .update(task)
                    .mapFailure { UpdateTaskUseCaseException.DatabaseException(it.message) }
            }
}

sealed class UpdateTaskUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : UpdateTaskUseCaseException()
}
