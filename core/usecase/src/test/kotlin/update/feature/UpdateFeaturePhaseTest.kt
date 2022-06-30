@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package update.feature

import com.wsr.apiresult.ApiResult
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
import update.UpdateFeaturePhaseUseCase
import update.UpdateFeatureUseCaseException
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

    private val mockFeature = Feature(
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
        coEvery { featureQueryService.get(mockFeature.id) } returns ApiResult.Success(mockFeature)
        coEvery { featureRepository.update(mockFeature.copyWithPhase(FeaturePhase.Done)) } returns ApiResult.Success(Unit)
        val result = updateFeaturePhaseUseCase(mockFeature.id, FeaturePhase.Done)
        the(result).shouldBeEqual(ApiResult.Success(Unit))
    }

    @Test
    fun update失敗時にはFailureを返す() = runTest {
        coEvery { featureQueryService.get(mockFeature.id) } returns ApiResult.Success(mockFeature)
        coEvery {
            featureRepository.update(mockFeature.copyWithPhase(FeaturePhase.Done))
        } returns ApiResult.Failure(RepositoryException.DatabaseException("Error"))
        val result = updateFeaturePhaseUseCase(featureId = mockFeature.id, phase = FeaturePhase.Done)
        the(result).shouldBeEqual(ApiResult.Failure(UpdateFeatureUseCaseException.DatabaseException("Error")))
    }

    @AfterTest
    fun 関連するメソッド呼び出しの回数の確認() {
        coVerify(exactly = 1) {
            featureQueryService.get(mockFeature.id)
            featureRepository.update(mockFeature.copyWithPhase(FeaturePhase.Done))
        }
        confirmVerified(featureQueryService, featureRepository)
    }
}
