@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package get

import dto.task.TaskUseCaseDto.Companion.toUseCaseDto
import com.wsr.apiresult.ApiResult
import feature.*
import dto.task.TaskQueryService
import get.task.GetTasksByFeatureIdUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.javalite.test.jspec.JSpec.the
import task.*
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetTasksByFeatureIdTest {
    private lateinit var getTasksByFeatureUseCase: GetTasksByFeatureIdUseCase
    @MockK
    private lateinit var taskQueryService: TaskQueryService

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        getTasksByFeatureUseCase = GetTasksByFeatureIdUseCase(taskQueryService)
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
    fun 特定のFeature関連のTaskを取得() = runTest {
        coEvery { taskQueryService.getByFeatureId(mockFeatureId) } returns ApiResult.Success(mockData)
        val expected = ApiResult.Success(mockData.map { it.toUseCaseDto() })
        the(getTasksByFeatureUseCase(mockFeatureId)).shouldBeEqual(expected)
    }

    @Test
    fun Task取得失敗時はFailureを返す() = runTest {
        coEvery { taskQueryService.getByFeatureId(mockFeatureId) } returns ApiResult.Failure(QueryServiceException.DatabaseException("Error"))
        val expected = ApiResult.Failure(UseCaseException.DatabaseException("Error"))
        the(getTasksByFeatureUseCase(mockFeatureId)).shouldBeEqual(expected)
    }


    @AfterTest
    fun 関連するメソッド呼び出しの回数の確認() {
        coVerify(exactly = 1) { taskQueryService.getByFeatureId(mockFeatureId) }
        confirmVerified(taskQueryService)
    }
}
