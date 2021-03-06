@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package update.feature

import com.wsr.apiresult.ApiResult
import dto.feature.FeatureQueryService
import feature.Feature
import feature.FeatureDescription
import feature.FeaturePhase
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
import update.UpdateFeatureUseCase
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateFeatureFailureTest {
    private lateinit var updateFeaturePhaseUseCase: UpdateFeatureUseCase
    @MockK
    private lateinit var featureQueryService: FeatureQueryService
    @MockK
    private lateinit var featureRepository: FeatureRepository

    private val mockFeature = Feature.create(
        title = FeatureTitle("mockTitle"),
        description = FeatureDescription("mockDescription"),
    )

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { featureQueryService.get(mockFeature.id) } returns ApiResult.Success(mockFeature)
        coEvery {
            featureRepository.update(any())
        } returns ApiResult.Failure(RepositoryException.DatabaseException("Error"))
        updateFeaturePhaseUseCase = UpdateFeatureUseCase(featureQueryService, featureRepository)
    }

    @Test
    fun titleの更新失敗時() = runTest {
        val result = updateFeaturePhaseUseCase(featureId = mockFeature.id, title = FeatureTitle("newTitle"))
        the(result).shouldBeEqual(ApiResult.Failure(UseCaseException.DatabaseException("Error")))
    }

    @Test
    fun descriptionの更新失敗時() = runTest {
        val result = updateFeaturePhaseUseCase(featureId = mockFeature.id, description = FeatureDescription("newDescription"))
        the(result).shouldBeEqual(ApiResult.Failure(UseCaseException.DatabaseException("Error")))
    }

    @Test
    fun phaseの更新失敗時() = runTest {
        val result = updateFeaturePhaseUseCase(featureId = mockFeature.id, phase = FeaturePhase.Done)
        the(result).shouldBeEqual(ApiResult.Failure(UseCaseException.DatabaseException("Error")))
    }

    @AfterTest
    fun 関連するメソッド呼び出しの回数の確認() {
        coVerify(exactly = 1) {
            featureQueryService.get(mockFeature.id)
            featureRepository.update(any())
        }
        confirmVerified(featureQueryService, featureRepository)
    }
}
