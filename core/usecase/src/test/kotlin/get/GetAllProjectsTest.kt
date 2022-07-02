@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package get

import QueryServiceException
import com.wsr.apiresult.ApiResult
import dto.project.ProjectQueryService
import dto.project.ProjectUseCaseDto.Companion.toUseCaseDto
import get.project.GetAllProjectsUseCase
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
import project.ProjectTitle
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllProjectsTest {

    private lateinit var getAllProjectsUseCase: GetAllProjectsUseCase
    @MockK
    private lateinit var projectQueryService: ProjectQueryService

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        getAllProjectsUseCase = GetAllProjectsUseCase(projectQueryService)
    }

    private val mockData = List(5) { index ->
        Project.create(
            title = ProjectTitle("mockTitle$index"),
            description = ProjectDescription("mockDescription$index")
        )
    }

    @Test
    fun 全部プロジェクトを取得する() = runTest {
        coEvery { projectQueryService.getAll() } returns ApiResult.Success(mockData)

        val expected = ApiResult.Success(mockData.map { it.toUseCaseDto() })

        the(getAllProjectsUseCase()).shouldBeEqual(expected)
    }

    @Test
    fun プロジェクト取得失敗時はFailureを返す() = runTest {
        coEvery {
            projectQueryService.getAll()
        } returns ApiResult.Failure(QueryServiceException.DatabaseException("Error"))

        the(getAllProjectsUseCase())
            .shouldBeEqual(ApiResult.Failure(UseCaseException.DatabaseException("Error")))
    }

    @AfterTest
    fun 関連するメソッド呼び出しの回数の確認() {
        coVerify(exactly = 1) { projectQueryService.getAll() }
        confirmVerified(projectQueryService)
    }
}
