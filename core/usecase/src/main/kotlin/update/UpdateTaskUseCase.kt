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
    ): ApiResult<Unit, UpdateTaskPhaseUseCaseException> =
        update(taskId) { task -> task.copy(title = title) }

    suspend operator fun invoke(
        taskId: TaskId,
        phase: TaskPhase,
    ): ApiResult<Unit, UpdateTaskPhaseUseCaseException> =
        update(taskId) { task -> task.copyWithPhase(phase) }


    suspend fun update(taskId: TaskId, block: (Task) -> Task) =
        taskQueryService.get(taskId)
            .mapBoth(
                success = block,
                failure = { UpdateTaskPhaseUseCaseException.DatabaseException(it.message) },
            )
            .flatMap { task ->
                taskRepository
                    .update(task)
                    .mapFailure { UpdateTaskPhaseUseCaseException.DatabaseException(it.message) }
            }
}
