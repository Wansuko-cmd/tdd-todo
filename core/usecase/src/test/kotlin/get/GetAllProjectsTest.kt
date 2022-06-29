@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package get

import com.wsr.apiresult.ApiResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.javalite.test.jspec.JSpec.the
import project.*
import QueryServiceException
import dto.project.ProjectUseCaseDto.Companion.toUseCaseDto
import dto.project.ProjectQueryService
import get.project.GetAllProjectsUseCaseException
import get.project.GetAllProjectsUseCase
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

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
        Project(
            id = ProjectId("mockId$index"),
            title = ProjectTitle("mockTitle$index"),
            description = ProjectDescription("mockDescription$index")
        )
    }

    @Test
    fun 全部プロジェクトを取得する() = runBlocking {
        coEvery { projectQueryService.getAll() } returns ApiResult.Success(mockData)

        val expected = ApiResult.Success(mockData.map { it.toUseCaseDto() })

        the(getAllProjectsUseCase()).shouldBeEqual(expected)
    }

    @Test
    fun プロジェクト取得失敗時はFailureを返す() = runBlocking {
        coEvery {
            projectQueryService.getAll()
        } returns ApiResult.Failure(QueryServiceException.DatabaseException("Error"))

        the(getAllProjectsUseCase())
            .shouldBeEqual(ApiResult.Failure(GetAllProjectsUseCaseException.DatabaseException("Error")))
    }

    @AfterTest
    fun 呼び出し回数のカウント() {
        coVerify(exactly = 1) { projectQueryService.getAll() }
        confirmVerified(projectQueryService)
    }
}
