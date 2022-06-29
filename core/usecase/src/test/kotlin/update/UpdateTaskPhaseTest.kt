@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package update

import com.wsr.apiresult.ApiResult
import dto.task.TaskPhaseUseCaseDto
import dto.task.TaskQueryService
import feature.FeatureId
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
    fun 特定のTaskのPhaseの状態を変更() = runBlocking {
        coEvery { taskQueryService.get(mockTask.id) } returns ApiResult.Success(mockTask)
        coEvery { taskRepository.update(mockTask.copyWithPhase(TaskPhase.Red)) } returns ApiResult.Success(Unit)
        val result = updateTaskPhaseUseCase(taskId = mockTask.id, phase = TaskPhaseUseCaseDto.Red)
        the(result).shouldBeEqual(ApiResult.Success(Unit))
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
