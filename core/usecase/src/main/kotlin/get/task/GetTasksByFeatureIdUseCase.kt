package get.task

import UseCaseException
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth
import dto.task.TaskQueryService
import dto.task.TaskUseCaseDto
import dto.task.TaskUseCaseDto.Companion.toUseCaseDto
import feature.FeatureId
import toUseCaseException

class GetTasksByFeatureIdUseCase(private val queryService: TaskQueryService) {
    suspend operator fun invoke(featureId: FeatureId): ApiResult<List<TaskUseCaseDto>, UseCaseException> =
        queryService.getByFeatureId(featureId).mapBoth(
            success = { tasks -> tasks.map { it.toUseCaseDto() } },
            failure = { it.toUseCaseException() }
        )
}
