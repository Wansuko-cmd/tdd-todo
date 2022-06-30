package update

import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.flatMap
import com.wsr.apiresult.mapBoth
import com.wsr.apiresult.mapFailure
import dto.task.TaskQueryService
import task.Task
import task.TaskId
import task.TaskRepository

class UpdateTaskUseCase(
    private val taskQueryService: TaskQueryService,
    private val taskRepository: TaskRepository,
) {

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
