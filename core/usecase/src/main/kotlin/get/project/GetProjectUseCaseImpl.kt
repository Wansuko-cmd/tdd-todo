package get.project

import ProjectUseCaseModel
import ProjectUseCaseModel.Companion.toUseCaseModel
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapBoth

class GetProjectUseCaseImpl(private val queryService: GetProjectQueryService) : GetProjectUseCase {
    override suspend fun getAll(): ApiResult<List<ProjectUseCaseModel>, GetProjectUseCaseException> =
        queryService.getAll().mapBoth(
            success = { projects -> projects.map { it.toUseCaseModel() } },
            failure = { GetProjectUseCaseException.DatabaseException(it.message) },
        )
}
