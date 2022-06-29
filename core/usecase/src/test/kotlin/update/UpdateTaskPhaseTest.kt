@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package update

import RepositoryException
import com.wsr.apiresult.ApiResult
import dto.task.TaskPhaseUseCaseDto
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
    private lateinit var updateTaskPhaseUseCase: UpdateTaskPhaseUseCase
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
        updateTaskPhaseUseCase = UpdateTaskPhaseUseCase(taskQueryService, taskRepository)
    }

    @Test
    fun 特定のTaskのPhaseの状態を変更() = runTest {
        coEvery { taskQueryService.get(mockTask.id) } returns ApiResult.Success(mockTask)
        coEvery { taskRepository.update(mockTask.copyWithPhase(TaskPhase.Red)) } returns ApiResult.Success(Unit)
        val result = updateTaskPhaseUseCase(taskId = mockTask.id, phase = TaskPhaseUseCaseDto.Red)
        the(result).shouldBeEqual(ApiResult.Success(Unit))
    }

    @Test
    fun update失敗時にはFailureを返す() = runTest {
        coEvery { taskQueryService.get(mockTask.id) } returns ApiResult.Success(mockTask)
        coEvery { taskRepository.update(mockTask.copyWithPhase(TaskPhase.Red)) } returns ApiResult.Failure(RepositoryException.DatabaseException("Error"))
        val result = updateTaskPhaseUseCase(taskId = mockTask.id, phase = TaskPhaseUseCaseDto.Red)
        the(result).shouldBeEqual(ApiResult.Failure(UpdateTaskPhaseUseCaseException.DatabaseException("Error")))
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
