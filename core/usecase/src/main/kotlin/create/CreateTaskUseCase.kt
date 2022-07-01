package create

import UseCaseException
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth
import feature.FeatureId
import task.Task
import task.TaskDescription
import task.TaskRepository
import task.TaskTitle
import toUseCaseException

class CreateTaskUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(
        featureId: FeatureId,
        title: TaskTitle,
        description: TaskDescription,
    ): ApiResult<Task, UseCaseException> =
        Task.create(title = title, description = description).let { task ->
            taskRepository.insert(task, featureId).mapBoth(
                success = { task },
                failure = { it.toUseCaseException() }
            )
        }
}
