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
import dto.ProjectUseCaseModel.Companion.toUseCaseModel
import get.project.GetProjectQueryService
import get.project.GetProjectUseCase
import get.project.GetProjectUseCaseException
import get.project.GetProjectUseCaseImpl
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetProjectTest {

    private lateinit var target: GetProjectUseCase
    @MockK
    private lateinit var getProjectQueryService: GetProjectQueryService

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = GetProjectUseCaseImpl(getProjectQueryService)
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
        coEvery { getProjectQueryService.getAll() } returns ApiResult.Success(mockData)

        val expected = ApiResult.Success(mockData.map { it.toUseCaseModel() })

        the(target.getAll()).shouldBeEqual(expected)
    }

    @Test
    fun プロジェクト取得失敗時はFailureを返す() = runBlocking {
        coEvery {
            getProjectQueryService.getAll()
        } returns ApiResult.Failure(QueryServiceException.DatabaseException("Error"))

        the(target.getAll())
            .shouldBeEqual(ApiResult.Failure(GetProjectUseCaseException.DatabaseException("Error")))
    }

    @AfterTest
    fun 呼び出し回数のカウント() {
        coVerify(exactly = 1) { getProjectQueryService.getAll() }
        confirmVerified(getProjectQueryService)
    }
}
