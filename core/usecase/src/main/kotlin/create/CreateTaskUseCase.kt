package create

import com.wsr.apiresult.mapFailure
import feature.FeatureId
import task.Task
import task.TaskDescription
import task.TaskRepository
import task.TaskTitle
import toUseCaseException

class CreateTaskUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(featureId: FeatureId, title: TaskTitle, description: TaskDescription) =
        Task.create(title = title, description = description)
            .let { taskRepository.insert(it) }
            .mapFailure { it.toUseCaseException() }
}
