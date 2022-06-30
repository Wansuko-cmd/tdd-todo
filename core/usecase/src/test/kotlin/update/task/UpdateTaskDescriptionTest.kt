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
import update.UpdateTaskUseCaseException
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateTaskDescriptionTest {
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

    private val newDescription = TaskDescription("newDescription")

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        updateTaskUseCase = UpdateTaskUseCase(taskQueryService, taskRepository)
    }

    @Test
    fun 特定のTaskのDescriptionを更新() = runTest {
        coEvery { taskQueryService.get(mockTask.id) } returns ApiResult.Success(mockTask)
        coEvery { taskRepository.update(mockTask.copy(description = newDescription)) } returns ApiResult.Success(Unit)

        val result = updateTaskUseCase(taskId = mockTask.id, description = newDescription)
        the(result).shouldBeEqual(ApiResult.Success(Unit))
    }

    @Test
    fun update失敗時にはFailureを返す() = runTest {
        coEvery { taskQueryService.get(mockTask.id) } returns ApiResult.Success(mockTask)
        coEvery {
            taskRepository.update(mockTask.copy(description = newDescription))
        } returns ApiResult.Failure(RepositoryException.DatabaseException("Error"))

        val result = updateTaskUseCase(taskId = mockTask.id, description = newDescription)
        the(result).shouldBeEqual(ApiResult.Failure(UpdateTaskUseCaseException.DatabaseException("Error")))
    }

    @AfterTest
    fun 呼び出し回数のカウント() {
        coVerify(exactly = 1) {
            taskQueryService.get(mockTask.id)
            taskRepository.update(mockTask.copy(description = newDescription))
        }
        confirmVerified(taskQueryService, taskRepository)
    }
}
