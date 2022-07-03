@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package delete

import com.wsr.apiresult.ApiResult
import dto.feature.FeatureQueryService
import dto.task.TaskQueryService
import feature.Feature
import feature.FeatureDescription
import feature.FeatureRepository
import feature.FeatureTitle
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.javalite.test.jspec.JSpec.the
import project.ProjectId
import project.ProjectRepository
import task.Task
import task.TaskDescription
import task.TaskId
import task.TaskRepository
import task.TaskTitle
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteProjectTest {
    private lateinit var deleteProjectUseCase: DeleteProjectUseCase

    @MockK
    private lateinit var projectRepository: ProjectRepository
    @MockK
    private lateinit var featureRepository: FeatureRepository
    @MockK
    private lateinit var taskRepository: TaskRepository
    @MockK
    private lateinit var featureQueryService: FeatureQueryService
    @MockK
    private lateinit var taskQueryService: TaskQueryService

    private val mockProjectId = ProjectId("mockProjectId")
    private val mockFeature = Feature.create(
        title = FeatureTitle("mockFeatureTitle"),
        description = FeatureDescription("mockFeatureDescription"),
    )
    private val mockTasks = List(3) { index ->
        Task.create(
            title = TaskTitle("mockTitle$index"),
            description = TaskDescription("mockDescription$index"),
        )
    }

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)

        // それぞれの要素を削除する処理のモック
        coEvery { projectRepository.delete(mockProjectId) } returns ApiResult.Success(Unit)
        coEvery { featureRepository.delete(mockFeature.id) } returns ApiResult.Success(Unit)
        coEvery { taskRepository.delete(TaskId(any())) } returns ApiResult.Success(Unit)

        // DBに入っている値を取得する処理のモック
        coEvery { featureQueryService.getByProjectId(mockProjectId) } returns ApiResult.Success(listOf(mockFeature))
        coEvery { taskQueryService.getByFeatureId(mockFeature.id) } returns ApiResult.Success(mockTasks)

        deleteProjectUseCase = DeleteProjectUseCase(
            projectRepository,
            featureRepository,
            taskRepository,
            featureQueryService,
            taskQueryService,
        )
    }

    @Test
    fun 特定のFeatureを削除() = runTest {
        val result = deleteProjectUseCase(mockProjectId)
        the(result).shouldBeEqual(ApiResult.Success(Unit))
    }

    @AfterTest
    fun 関連するメソッド呼び出しの回数の確認() {
        // 削除する要素の数だけ呼び出される
        coVerify(exactly = 1) { projectRepository.delete(mockProjectId) }
        coVerify(exactly = 1) { featureRepository.delete(mockFeature.id) }
        coVerify(exactly = 3) { taskRepository.delete(TaskId(any())) }

        // 常に一回
        coVerify(exactly = 1) { featureQueryService.getByProjectId(mockProjectId) }
        coVerify(exactly = 1) { taskQueryService.getByFeatureId(mockFeature.id) }

        confirmVerified(taskQueryService, featureRepository, taskRepository)
    }
}
