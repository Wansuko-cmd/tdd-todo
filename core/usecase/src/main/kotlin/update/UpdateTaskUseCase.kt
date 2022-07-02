package update

import UseCaseException
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.flatMap
import com.wsr.apiresult.mapBoth
import dto.task.TaskQueryService
import dto.task.TaskUseCaseDto
import dto.task.TaskUseCaseDto.Companion.toUseCaseDto
import task.Task
import task.TaskDescription
import task.TaskId
import task.TaskPhase
import task.TaskRepository
import task.TaskTitle
import toUseCaseException

class UpdateTaskUseCase(
    private val taskQueryService: TaskQueryService,
    private val taskRepository: TaskRepository,
) {

    suspend operator fun invoke(
        taskId: TaskId,
        title: TaskTitle,
    ): ApiResult<TaskUseCaseDto, UseCaseException> =
        update(taskId) { task -> task.changeTitle(title = title) }

    suspend operator fun invoke(
        taskId: TaskId,
        description: TaskDescription,
    ): ApiResult<TaskUseCaseDto, UseCaseException> =
        update(taskId) { task -> task.changeDescription(description = description) }

    suspend operator fun invoke(
        taskId: TaskId,
        phase: TaskPhase,
    ): ApiResult<TaskUseCaseDto, UseCaseException> =
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
                    .mapBoth(
                        success = { task.toUseCaseDto() },
                        failure = { it.toUseCaseException() }
                    )
            }
}
