@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package update.task

import com.wsr.apiresult.ApiResult
import dto.task.TaskQueryService
import feature.FeatureId
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.javalite.test.jspec.JSpec.the
import task.*
import update.UpdateTaskUseCase
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateTaskSuccessTest {
    private lateinit var updateTaskUseCase: UpdateTaskUseCase
    @MockK
    private lateinit var taskQueryService: TaskQueryService
    @MockK
    private lateinit var taskRepository: TaskRepository

    private val mockTask = Task(
        id = TaskId("mockId"),
        featureId = FeatureId("mockFeatureId"),
        title = TaskTitle("mockTitle"),
        description = TaskDescription("mockDescription"),
        phase = TaskPhase.Todo,
    )

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { taskQueryService.get(mockTask.id) } returns ApiResult.Success(mockTask)
        coEvery { taskRepository.update(any()) } returns ApiResult.Success(Unit)
        updateTaskUseCase = UpdateTaskUseCase(taskQueryService, taskRepository)
    }

    @Test
    fun 特定のTaskのTitleを更新() = runTest {
        val result = updateTaskUseCase(taskId = mockTask.id, title = TaskTitle("newTitle"))
        the(result).shouldBeEqual(ApiResult.Success(Unit))
    }

    @Test
    fun 特定のTaskのPhaseを更新() = runTest {
        val result = updateTaskUseCase(taskId = mockTask.id, phase = TaskPhase.Red)
        the(result).shouldBeEqual(ApiResult.Success(Unit))
    }

    @Test
    fun 特定のTaskのDescriptionを更新() = runTest {
        val result = updateTaskUseCase(taskId = mockTask.id, description = TaskDescription("newDescription"))
        the(result).shouldBeEqual(ApiResult.Success(Unit))
    }

    @AfterTest
    fun 呼び出し回数のカウント() {
        coVerify(exactly = 1) {
            taskQueryService.get(mockTask.id)
            taskRepository.update(any())
        }
        confirmVerified(taskQueryService, taskRepository)
    }
}
