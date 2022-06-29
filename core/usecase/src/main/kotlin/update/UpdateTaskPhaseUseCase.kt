package update

import com.wsr.apiresult.consume
import com.wsr.apiresult.flatMap
import com.wsr.apiresult.map
import dto.task.TaskPhaseUseCaseDto
import dto.task.TaskPhaseUseCaseDto.Companion.toDomain
import dto.task.TaskQueryService
import task.TaskId
import task.TaskRepository

class UpdateTaskPhaseUseCase(
    private val queryService: TaskQueryService,
    private val repository: TaskRepository,
) {
    suspend operator fun invoke(
        taskId: TaskId,
        phase: TaskPhaseUseCaseDto,
    ) = queryService.get(taskId)
        .map { task -> task.copyWithPhase(phase.toDomain()) }
        .flatMap { repository.update(it) }
}


/**
 * .consume(
success = { task ->
val newTask = task.copyWithPhase(taskPhaseUseCaseDto.toDomain())
repository.update(newTask)
}
 */
