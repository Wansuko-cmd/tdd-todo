@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package get

import FeatureUseCaseModel.Companion.toUseCaseModel
import com.wsr.apiresult.ApiResult
import feature.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.javalite.test.jspec.JSpec.the
import project.ProjectId
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetFeatureTest {
    private lateinit var target: GetFeatureUseCase
    @MockK
    private lateinit var getFeatureQueryService: GetFeatureQueryService

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = GetFeatureUseCaseImpl(getFeatureQueryService)
    }

    private val mockProjectId = ProjectId("mockProjectId")

    private val mockData = List(5) { index ->
        Feature(
            id = FeatureId("mockId$index"),
            projectId = mockProjectId,
            title = FeatureTitle("mockTitle$index"),
            description = FeatureDescription("mockDescription$index"),
            phase = FeaturePhase.Todo,
        )
    }

    @Test
    fun 特定のProject関連のFeatureを取得() = runBlocking {
        coEvery { getFeatureQueryService.getByProjectId(mockProjectId) } returns ApiResult.Success(mockData)
        val expected = ApiResult.Success(mockData.map { it.toUseCaseModel() })
        the(target.getByProjectId(mockProjectId.value)).shouldBeEqual(expected)
    }
}
