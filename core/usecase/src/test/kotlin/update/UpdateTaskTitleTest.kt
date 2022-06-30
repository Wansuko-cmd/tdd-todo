@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package update

import com.wsr.apiresult.ApiResult
import dto.task.TaskQueryService
import feature.FeatureId
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.javalite.test.jspec.JSpec.the
import task.*
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateTaskTitleTest {
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
        updateTaskUseCase = UpdateTaskUseCase(taskQueryService, taskRepository)
    }

    @Test
    fun 特定のTaskのPhaseを更新() = runTest {
        val newTitle = TaskTitle("newTitle")

        coEvery { taskQueryService.get(mockTask.id) } returns ApiResult.Success(mockTask)
        coEvery { taskRepository.update(mockTask.copy(title = newTitle)) } returns ApiResult.Success(Unit)

        val result = updateTaskUseCase(taskId = mockTask.id, title = newTitle)
        the(result).shouldBeEqual(ApiResult.Success(Unit))
    }
}
