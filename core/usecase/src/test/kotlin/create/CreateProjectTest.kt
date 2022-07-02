@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package create

import com.wsr.apiresult.ApiResult
import dto.project.ProjectUseCaseDto.Companion.toUseCaseDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.javalite.test.jspec.JSpec.the
import project.Project
import project.ProjectDescription
import project.ProjectRepository
import project.ProjectTitle
import java.util.UUID
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CreateProjectTest {
    private lateinit var createProjectUseCase: CreateProjectUseCase
    @MockK
    private lateinit var projectRepository: ProjectRepository

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        mockkStatic(UUID::class)
        val uuid = "mockUUID"
        every { UUID.randomUUID().toString() } returns uuid
        createProjectUseCase = CreateProjectUseCase(projectRepository)
    }

    @Test
    fun 新たなProjectを生成する() = runTest {
        coEvery { projectRepository.insert(any()) } returns ApiResult.Success(Unit)
        val result = createProjectUseCase(
            title = ProjectTitle("mockTitle"),
            description = ProjectDescription("mockDescription"),
        )
        val expected = Project.create(
            title = ProjectTitle("mockTitle"),
            ProjectDescription("mockDescription"),
        ).toUseCaseDto()
        the(result).shouldBeEqual(ApiResult.Success(expected))
    }

    @Test
    fun Project生成に失敗したとき() = runTest {
        coEvery { projectRepository.insert(any()) } returns ApiResult.Failure(RepositoryException.DatabaseException("Error"))
        val result = createProjectUseCase(
            title = ProjectTitle("mockTitle"),
            description = ProjectDescription("mockDescription"),
        )
        the(result).shouldBeEqual(ApiResult.Failure(UseCaseException.DatabaseException("Error")))
    }

    @AfterTest
    fun 関連するメソッド呼び出しの回数の確認() {
        coVerify(exactly = 1) { projectRepository.insert(any()) }
        confirmVerified(projectRepository)
    }
}
