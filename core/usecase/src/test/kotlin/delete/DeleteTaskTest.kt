@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package delete

import com.wsr.apiresult.ApiResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.javalite.test.jspec.JSpec.the
import task.TaskId
import task.TaskRepository
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteTaskTest {
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase
    @MockK
    private lateinit var taskRepository: TaskRepository

    private val mockTaskId = TaskId("mockId")

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        deleteTaskUseCase = DeleteTaskUseCase(taskRepository)
    }

    @Test
    fun 特定のTaskを削除() = runTest {
        coEvery { taskRepository.delete(mockTaskId) } returns ApiResult.Success(Unit)
        val result = deleteTaskUseCase(mockTaskId)
        the(result).shouldBeEqual(ApiResult.Success(Unit))
    }

    @Test
    fun Task削除に失敗したとき() = runTest {
        coEvery { taskRepository.delete(mockTaskId) } returns ApiResult.Failure(RepositoryException.DatabaseException("Error"))
        val result = deleteTaskUseCase(mockTaskId)
        the(result).shouldBeEqual(ApiResult.Failure(UseCaseException.DatabaseException("Error")))
    }

    @AfterTest
    fun 関連するメソッド呼び出しの回数の確認() {
        coVerify(exactly = 1) { taskRepository.delete(mockTaskId) }
        confirmVerified(taskRepository)
    }
}
