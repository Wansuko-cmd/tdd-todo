package update

import com.wsr.apiresult.ApiResult
import dto.task.TaskQueryService
import task.TaskId
import task.TaskRepository
import task.TaskTitle

class UpdateTaskTitleUseCase(
    taskQueryService: TaskQueryService,
    taskRepository: TaskRepository,
) {
    private val updateTaskUseCase = UpdateTaskUseCase(taskQueryService, taskRepository)
    suspend operator fun invoke(
        taskId: TaskId,
        title: TaskTitle,
    ): ApiResult<Unit, UpdateTaskPhaseUseCaseException> =
        updateTaskUseCase.update(taskId) { task -> task.copy(title = title) }
}
