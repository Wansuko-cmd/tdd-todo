package update

import com.wsr.apiresult.*
import dto.task.TaskPhaseUseCaseDto
import dto.task.TaskPhaseUseCaseDto.Companion.toDomain
import dto.task.TaskQueryService
import task.TaskId
import task.TaskRepository

class UpdateTaskPhaseUseCase(
    private val taskQueryService: TaskQueryService,
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(
        taskId: TaskId,
        phase: TaskPhaseUseCaseDto,
    ): ApiResult<Unit, UpdateTaskPhaseUseCaseException> =
        taskQueryService.get(taskId)
            .mapBoth(
                success = { task -> task.copyWithPhase(phase.toDomain()) },
                failure = { UpdateTaskPhaseUseCaseException.DatabaseException(it.message) },
            )
            .flatMap { task ->
                taskRepository
                    .update(task)
                    .mapFailure { UpdateTaskPhaseUseCaseException.DatabaseException(it.message) }
            }
}

sealed class UpdateTaskPhaseUseCaseException : Exception() {
    data class DatabaseException(override val message: String?) : UpdateTaskPhaseUseCaseException()
}
