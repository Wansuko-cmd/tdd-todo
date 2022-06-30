package delete

import com.wsr.apiresult.mapFailure
import task.TaskId
import task.TaskRepository
import toUseCaseException

class DeleteTaskUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskId: TaskId) =
        taskRepository.delete(taskId).mapFailure { it.toUseCaseException() }
}
