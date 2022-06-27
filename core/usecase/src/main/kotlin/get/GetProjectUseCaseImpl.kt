package get

class GetProjectUseCaseImpl(private val queryService: GetProjectQueryService) : GetProjectUseCase {
    override suspend fun getAll() = queryService.getAll()
}
