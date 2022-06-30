@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package create

import com.wsr.apiresult.ApiResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.javalite.test.jspec.JSpec.the
import project.ProjectDescription
import project.ProjectRepository
import project.ProjectTitle
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
        createProjectUseCase = CreateProjectUseCase(projectRepository)
    }

    @Test
    fun 新たなProjectを生成する() = runTest {
        coEvery { projectRepository.insert(any()) } returns ApiResult.Success(Unit)
        val result = createProjectUseCase(
            title = ProjectTitle("mockTitle"),
            description = ProjectDescription("mockDescription"),
        )
        the(result).shouldBeEqual(ApiResult.Success(Unit))
    }
}
