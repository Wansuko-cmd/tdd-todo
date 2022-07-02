@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package update.feature

import com.wsr.apiresult.ApiResult
import dto.feature.FeatureQueryService
import dto.feature.FeatureUseCaseDto.Companion.toUseCaseDto
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
class UpdateFeatureSuccessTest {
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
        coEvery { featureRepository.update(any()) } returns ApiResult.Success(Unit)
        updateFeaturePhaseUseCase = UpdateFeatureUseCase(featureQueryService, featureRepository)
    }

    @Test
    fun 特定のFeatureのTitleを更新() = runTest {
        val newTitle = FeatureTitle("newTitle")
        val result = updateFeaturePhaseUseCase(featureId = mockFeature.id, title = newTitle)
        the(result).shouldBeEqual(ApiResult.Success(mockFeature.changeTitle(newTitle).toUseCaseDto()))
    }

    @Test
    fun 特定のFeatureのDescriptionを更新() = runTest {
        val newDescription = FeatureDescription("newDescription")
        val result = updateFeaturePhaseUseCase(featureId = mockFeature.id, description = newDescription)
        the(result).shouldBeEqual(ApiResult.Success(mockFeature.changeDescription(newDescription).toUseCaseDto()))
    }

    @Test
    fun 特定のFeatureのPhaseを更新() = runTest {
        val newPhase = FeaturePhase.Done
        val result = updateFeaturePhaseUseCase(mockFeature.id, newPhase)
        the(result).shouldBeEqual(ApiResult.Success(mockFeature.changePhase(newPhase).toUseCaseDto()))
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
