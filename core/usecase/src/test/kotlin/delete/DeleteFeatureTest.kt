@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package delete

import com.wsr.apiresult.ApiResult
import dto.task.TaskQueryService
import feature.FeatureId
import feature.FeatureRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.javalite.test.jspec.JSpec.the
import task.Task
import task.TaskDescription
import task.TaskId
import task.TaskRepository
import task.TaskTitle
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteFeatureTest {
    private lateinit var deleteFeatureUseCase: DeleteFeatureUseCase

    @MockK
    private lateinit var featureRepository: FeatureRepository
    @MockK
    private lateinit var taskRepository: TaskRepository
    @MockK
    private lateinit var taskQueryService: TaskQueryService

    private val mockFeatureId = FeatureId("mockId")
    private val mockTasks = List(3) { index ->
        Task.create(
            title = TaskTitle("mockTaskTitle$index"),
            description = TaskDescription("mockTaskDescription$index"),
        )
    }

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)

        // それぞれの要素を削除する部分のモック
        coEvery { featureRepository.delete(mockFeatureId) } returns ApiResult.Success(Unit)
        coEvery { taskRepository.delete(taskId = TaskId(any())) } returns ApiResult.Success(Unit)

        // DBに入っている値を取得する処理のモック
        coEvery { taskQueryService.getByFeatureId(mockFeatureId) } returns ApiResult.Success(mockTasks)

        deleteFeatureUseCase = DeleteFeatureUseCase(taskQueryService, featureRepository, taskRepository)
    }

    @Test
    fun 特定のFeatureを削除() = runTest {
        val result = deleteFeatureUseCase(mockFeatureId)
        the(result).shouldBeEqual(ApiResult.Success(Unit))
    }

    @AfterTest
    fun 関連するメソッド呼び出しの回数の確認() {
        // 削除する要素の数だけ呼び出される
        coVerify(exactly = 1) { featureRepository.delete(mockFeatureId) }
        coVerify(exactly = 3) { taskRepository.delete(taskId = TaskId(any())) }

        // 常に一回
        coVerify(exactly = 1) { taskQueryService.getByFeatureId(mockFeatureId) }

        confirmVerified(taskQueryService, featureRepository, taskRepository)
    }
}
