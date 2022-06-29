@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package update

import com.wsr.apiresult.ApiResult
import dto.feature.FeaturePhaseUseCaseDto
import dto.feature.FeatureQueryService
import feature.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.javalite.test.jspec.JSpec.the
import project.ProjectId
import task.TaskPhase
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateFeaturePhaseTest {
    private lateinit var updateFeaturePhaseUseCase: UpdateFeaturePhaseUseCase
    @MockK
    private lateinit var featureQueryService: FeatureQueryService
    @MockK
    private lateinit var featureRepository: FeatureRepository

    private val mockData = Feature(
        id = FeatureId("mockId"),
        projectId = ProjectId("mockProjectId"),
        title = FeatureTitle("mockTitle"),
        description = FeatureDescription("mockDescription"),
        phase = FeaturePhase.Todo,
    )

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        updateFeaturePhaseUseCase = UpdateFeaturePhaseUseCase(featureQueryService, featureRepository)
    }

    @Test
    fun 特定のFeatureのPhaseを更新() = runTest {
        coEvery { featureQueryService.get(mockData.id) } returns ApiResult.Success(mockData)
        coEvery { featureRepository.update(mockData.copyWithPhase(FeaturePhase.Done)) } returns ApiResult.Success(Unit)
        val result = updateFeaturePhaseUseCase(mockData.id, FeaturePhaseUseCaseDto.Done)
        the(result).shouldBeEqual(ApiResult.Success(Unit))
    }

    @AfterTest
    fun 呼び出し回数のカウント() {
        coVerify(exactly = 1) {
            featureQueryService.get(mockData.id)
            featureRepository.update(mockData.copyWithPhase(FeaturePhase.Done))
        }
        confirmVerified(featureQueryService, featureRepository)
    }
}
