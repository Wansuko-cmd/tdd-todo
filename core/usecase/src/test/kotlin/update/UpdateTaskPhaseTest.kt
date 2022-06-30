@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package update

import RepositoryException
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
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateTaskPhaseTest {
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
        coEvery { taskQueryService.get(mockTask.id) } returns ApiResult.Success(mockTask)
        coEvery { taskRepository.update(mockTask.copyWithPhase(TaskPhase.Red)) } returns ApiResult.Success(Unit)
        val result = updateTaskUseCase(taskId = mockTask.id, phase = TaskPhase.Red)
        the(result).shouldBeEqual(ApiResult.Success(Unit))
    }

    @Test
    fun update失敗時にはFailureを返す() = runTest {
        coEvery { taskQueryService.get(mockTask.id) } returns ApiResult.Success(mockTask)
        coEvery { taskRepository.update(mockTask.copyWithPhase(TaskPhase.Red)) } returns ApiResult.Failure(RepositoryException.DatabaseException("Error"))
        val result = updateTaskUseCase(taskId = mockTask.id, phase = TaskPhase.Red)
        the(result).shouldBeEqual(ApiResult.Failure(UpdateTaskUseCaseException.DatabaseException("Error")))
    }

    @AfterTest
    fun 呼び出し回数のカウント() {
        coVerify(exactly = 1) {
            taskQueryService.get(mockTask.id)
            taskRepository.update(mockTask.copyWithPhase(TaskPhase.Red))
        }
        confirmVerified(taskQueryService, taskRepository)
    }
}
