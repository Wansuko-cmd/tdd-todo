package update

import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.flatMap
import com.wsr.apiresult.mapBoth
import com.wsr.apiresult.mapFailure
import dto.task.TaskQueryService
import task.TaskId
import task.TaskPhase
import task.TaskRepository
import task.TaskTitle

class UpdateTaskTitleUseCase(
    private val taskQueryService: TaskQueryService,
    private val taskRepository: TaskRepository,
) {    suspend operator fun invoke(
        taskId: TaskId,
        title: TaskTitle,
    ): ApiResult<Unit, UpdateTaskPhaseUseCaseException> =
        taskQueryService.get(taskId)
            .mapBoth(
                success = { task -> task.copy(title = title) },
                failure = { UpdateTaskPhaseUseCaseException.DatabaseException(it.message) },
            )
            .flatMap { task ->
                taskRepository
                    .update(task)
                    .mapFailure { UpdateTaskPhaseUseCaseException.DatabaseException(it.message) }
            }
}
