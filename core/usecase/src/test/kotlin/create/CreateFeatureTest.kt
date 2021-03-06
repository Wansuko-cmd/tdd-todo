@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package create

import com.wsr.apiresult.ApiResult
import dto.feature.FeatureUseCaseDto.Companion.toUseCaseDto
import feature.Feature
import feature.FeatureDescription
import feature.FeatureRepository
import feature.FeatureTitle
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.javalite.test.jspec.JSpec.the
import project.ProjectId
import java.util.UUID
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CreateFeatureTest {
    private lateinit var createFeatureUseCase: CreateFeatureUseCase
    @MockK
    private lateinit var featureRepository: FeatureRepository

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        mockkStatic(UUID::class)
        val uuid = "mockUUID"
        every { UUID.randomUUID().toString() } returns uuid
        createFeatureUseCase = CreateFeatureUseCase(featureRepository)
    }

    @Test
    fun 新たなFeatureを生成する() = runTest {
        coEvery { featureRepository.insert(any(), ProjectId(any())) } returns ApiResult.Success(Unit)
        val result = createFeatureUseCase(
            projectId = ProjectId("mockProjectId"),
            title = FeatureTitle("mockTitle"),
            description = FeatureDescription("mockDescription"),
        )
        val expected = Feature.create(
            title = FeatureTitle("mockTitle"),
            description = FeatureDescription("mockDescription"),
        ).toUseCaseDto()
        the(result).shouldBeEqual(ApiResult.Success(expected))
    }

    @Test
    fun Feature生成に失敗したとき() = runTest {
        coEvery { featureRepository.insert(any(), ProjectId(any())) } returns ApiResult.Failure(RepositoryException.DatabaseException("Error"))
        val result = createFeatureUseCase(
            projectId = ProjectId("mockProjectId"),
            title = FeatureTitle("mockTitle"),
            description = FeatureDescription("mockDescription"),
        )
        the(result).shouldBeEqual(ApiResult.Failure(UseCaseException.DatabaseException("Error")))
    }

    @AfterTest
    fun 関連するメソッド呼び出しの回数の確認() {
        coVerify(exactly = 1) { featureRepository.insert(any(), ProjectId(any())) }
        confirmVerified(featureRepository)
    }
}
