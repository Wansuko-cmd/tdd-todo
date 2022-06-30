package create

import UseCaseException
import com.wsr.apiresult.ApiResult
import com.wsr.apiresult.mapFailure
import project.Project
import project.ProjectDescription
import project.ProjectRepository
import project.ProjectTitle

class CreateProjectUseCase(private val projectRepository: ProjectRepository) {
    suspend operator fun invoke(title: ProjectTitle, description: ProjectDescription): ApiResult<Unit, UseCaseException> =
        Project(title = title, description = description)
            .let { projectRepository.insert(it) }
            .mapFailure { UseCaseException.DatabaseException(it.message) }
}
