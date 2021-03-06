@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package update.project

import com.wsr.apiresult.ApiResult
import dto.project.ProjectQueryService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.javalite.test.jspec.JSpec.the
import project.Project
import project.ProjectDescription
import project.ProjectRepository
import project.ProjectTitle
import update.UpdateProjectUseCase
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateProjectFailureTest {
    private lateinit var updateProjectUseCase: UpdateProjectUseCase
    @MockK
    private lateinit var projectQueryService: ProjectQueryService
    @MockK
    private lateinit var projectRepository: ProjectRepository

    private val mockProject = Project.create(
        title = ProjectTitle("mockTitle"),
        description = ProjectDescription("mockDescription"),
    )

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { projectQueryService.get(mockProject.id) } returns ApiResult.Success(mockProject)
        coEvery { projectRepository.update(any()) } returns ApiResult.Failure(RepositoryException.DatabaseException("Error"))
        updateProjectUseCase = UpdateProjectUseCase(projectQueryService, projectRepository)
    }

    @Test
    fun 特定のProjectのTitleを更新() = runTest {
        val result = updateProjectUseCase(projectId = mockProject.id, title = ProjectTitle("newTitle"))
        the(result).shouldBeEqual(ApiResult.Failure(UseCaseException.DatabaseException("Error")))
    }

    @Test
    fun 特定のProjectのDescriptionを更新() = runTest {
        val result = updateProjectUseCase(projectId = mockProject.id, description = ProjectDescription("newDescription"))
        the(result).shouldBeEqual(ApiResult.Failure(UseCaseException.DatabaseException("Error")))
    }

    @AfterTest
    fun 関連するメソッド呼び出しの回数の確認() {
        coVerify(exactly = 1) {
            projectQueryService.get(mockProject.id)
            projectRepository.update(any())
        }
        confirmVerified(projectQueryService, projectRepository)
    }
}
