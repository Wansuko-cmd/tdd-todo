package get

import project.*

class GetProjectUseCaseImpl(private val repository: ProjectRepository) : GetProjectUseCase {
    override suspend fun getAll() = repository.getAll()
}
