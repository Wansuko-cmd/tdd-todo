package update

import UseCaseException
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.flatMap
import com.wsr.apiresult.mapBoth
import com.wsr.apiresult.mapFailure
import dto.task.TaskQueryService
import task.*
import toUseCaseException

class UpdateTaskUseCase(
    private val taskQueryService: TaskQueryService,
    private val taskRepository: TaskRepository,
) {

    suspend operator fun invoke(
        taskId: TaskId,
        title: TaskTitle,
    ): ApiResult<Unit, UseCaseException> =
        update(taskId) { task -> task.copy(title = title) }

    suspend operator fun invoke(
        taskId: TaskId,
        description: TaskDescription,
    ): ApiResult<Unit, UseCaseException> =
        update(taskId) { task -> task.copy(description = description) }

    suspend operator fun invoke(
        taskId: TaskId,
        phase: TaskPhase,
    ): ApiResult<Unit, UseCaseException> =
        update(taskId) { task -> task.changePhase(phase) }


    private suspend fun update(taskId: TaskId, newTaskBuilder: (Task) -> Task) =
        taskQueryService.get(taskId)
            .mapBoth(
                success = newTaskBuilder,
                failure = { it.toUseCaseException() },
            )
            .flatMap { task ->
                taskRepository
                    .update(task)
                    .mapFailure { it.toUseCaseException() }
            }
}
