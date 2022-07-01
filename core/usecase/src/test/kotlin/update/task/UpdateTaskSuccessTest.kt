@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package update.task

import com.wsr.apiresult.ApiResult
import dto.task.TaskQueryService
import dto.task.TaskUseCaseDto.Companion.toUseCaseDto
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

    private val mockTask = Task.create(
        title = TaskTitle("mockTitle"),
        description = TaskDescription("mockDescription"),
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
        val newTitle = TaskTitle("newTitle")
        val result = updateTaskUseCase(taskId = mockTask.id, title = newTitle)
        the(result).shouldBeEqual(ApiResult.Success(mockTask.changeTitle(newTitle).toUseCaseDto()))
    }

    @Test
    fun 特定のTaskのDescriptionを更新() = runTest {
        val newDescription = TaskDescription("newDescription")
        val result = updateTaskUseCase(taskId = mockTask.id, description = newDescription)
        the(result).shouldBeEqual(ApiResult.Success(mockTask.changeDescription(newDescription).toUseCaseDto()))
    }

    @Test
    fun 特定のTaskのPhaseを更新() = runTest {
        val newPhase = TaskPhase.Red
        val result = updateTaskUseCase(taskId = mockTask.id, phase = newPhase)
        the(result).shouldBeEqual(ApiResult.Success(mockTask.changePhase(newPhase).toUseCaseDto()))
    }

    @AfterTest
    fun 関連するメソッド呼び出しの回数の確認() {
        coVerify(exactly = 1) {
            taskQueryService.get(mockTask.id)
            taskRepository.update(any())
        }
        confirmVerified(taskQueryService, taskRepository)
    }
}
