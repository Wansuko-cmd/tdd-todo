@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package update.project

import com.wsr.apiresult.ApiResult
import dto.project.ProjectQueryService
import dto.project.ProjectUseCaseDto.Companion.toUseCaseDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.javalite.test.jspec.JSpec.the
import project.*
import update.UpdateProjectUseCase
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateProjectSuccessTest {
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
        coEvery { projectRepository.update(any()) } returns ApiResult.Success(Unit)
        updateProjectUseCase = UpdateProjectUseCase(projectQueryService, projectRepository)
    }

    @Test
    fun 特定のProjectのTitleを更新() = runTest {
        val newTitle = ProjectTitle("newTitle")
        val result = updateProjectUseCase(projectId = mockProject.id, title = newTitle)
        the(result).shouldBeEqual(ApiResult.Success(mockProject.changeTitle(newTitle).toUseCaseDto()))
    }

    @Test
    fun 特定のProjectのDescriptionを更新() = runTest {
        val newDescription = ProjectDescription("newDescription")
        val result = updateProjectUseCase(projectId = mockProject.id, description = newDescription)
        the(result).shouldBeEqual(ApiResult.Success(mockProject.changeDescription(newDescription).toUseCaseDto()))
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
