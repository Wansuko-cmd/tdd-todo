package get

import com.wsr.apiresult.mapBoth

class GetProjectUseCaseImpl(private val queryService: GetProjectQueryService) : GetProjectUseCase {
    override suspend fun getAll() =
        queryService.getAll().mapBoth(
            success = { it },
            failure = { GetProjectUseCaseException.DatabaseException(it.message) }
        )
}
