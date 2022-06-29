@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package get

import dto.TaskUseCaseModel.Companion.toUseCaseModel
import com.wsr.apiresult.ApiResult
import feature.*
import get.task.GetTaskQueryService
import get.task.GetTaskUseCaseException
import get.task.GetTasksByFeatureIdUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.javalite.test.jspec.JSpec.the
import task.*
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetTaskTest {
    private lateinit var getTasksByFeatureUseCase: GetTasksByFeatureIdUseCase
    @MockK
    private lateinit var getTaskQueryService: GetTaskQueryService

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        getTasksByFeatureUseCase = GetTasksByFeatureIdUseCase(getTaskQueryService)
    }

    private val mockFeatureId = FeatureId("mockFeatureId")

    private val mockData = List(5) { index ->
        Task(
            id = TaskId("mockId$index"),
            featureId = mockFeatureId,
            title = TaskTitle("mockTitle$index"),
            description = TaskDescription("mockDescription$index"),
            phase = TaskPhase.Todo,
        )
    }

    @Test
    fun 特定のFeature関連のTaskを取得() = runBlocking {
        coEvery { getTaskQueryService.getByFeatureId(mockFeatureId) } returns ApiResult.Success(mockData)
        val expected = ApiResult.Success(mockData.map { it.toUseCaseModel() })
        the(getTasksByFeatureUseCase(mockFeatureId)).shouldBeEqual(expected)
    }

    @Test
    fun Task取得失敗時はFailureを返す() = runBlocking {
        coEvery { getTaskQueryService.getByFeatureId(mockFeatureId) } returns ApiResult.Failure(QueryServiceException.DatabaseException("Error"))
        val expected = ApiResult.Failure(GetTaskUseCaseException.DatabaseException("Error"))
        the(getTasksByFeatureUseCase(mockFeatureId)).shouldBeEqual(expected)
    }


    @AfterTest
    fun 呼び出し回数のカウント() {
        coVerify(exactly = 1) { getTaskQueryService.getByFeatureId(mockFeatureId) }
        confirmVerified(getTaskQueryService)
    }
}
