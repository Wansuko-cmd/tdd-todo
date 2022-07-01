@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package create

import com.wsr.apiresult.ApiResult
import feature.FeatureId
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.javalite.test.jspec.JSpec.the
import task.TaskDescription
import task.TaskRepository
import task.TaskTitle
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CreateTaskTest {
    private lateinit var createTaskUseCase: CreateTaskUseCase
    @MockK
    private lateinit var taskRepository: TaskRepository

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        createTaskUseCase = CreateTaskUseCase(taskRepository)
    }

    @Test
    fun 新たなFeatureを生成する() = runTest {
        coEvery { taskRepository.insert(any(), FeatureId(any())) } returns ApiResult.Success(Unit)
        val result = createTaskUseCase(
            featureId = FeatureId("mockFeatureId"),
            title = TaskTitle("mockTitle"),
            description = TaskDescription("mockDescription"),
        )
        the(result).shouldBeEqual(ApiResult.Success(Unit))
    }

    @Test
    fun Feature生成に失敗したとき() = runTest {
        coEvery { taskRepository.insert(any(), FeatureId(any())) } returns ApiResult.Failure(RepositoryException.DatabaseException("Error"))
        val result = createTaskUseCase(
            featureId = FeatureId("mockFeatureId"),
            title = TaskTitle("mockTitle"),
            description = TaskDescription("mockDescription"),
        )
        the(result).shouldBeEqual(ApiResult.Failure(UseCaseException.DatabaseException("Error")))
    }

    @AfterTest
    fun 関連するメソッド呼び出しの回数の確認() {
        coVerify(exactly = 1) { taskRepository.insert(any(), FeatureId(any())) }
        confirmVerified(taskRepository)
    }
}
