@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package get

import dto.feature.FeatureUseCaseDto.Companion.toUseCaseDto
import com.wsr.apiresult.ApiResult
import feature.*
import dto.feature.FeatureQueryService
import get.feature.GetFeatureUseCaseException
import get.feature.GetFeaturesByProjectIdUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.javalite.test.jspec.JSpec.the
import project.ProjectId
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetFeaturesByProjectIdTest {
    private lateinit var getFeaturesByProjectIdUseCase: GetFeaturesByProjectIdUseCase
    @MockK
    private lateinit var featureQueryService: FeatureQueryService

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        getFeaturesByProjectIdUseCase = GetFeaturesByProjectIdUseCase(featureQueryService)
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
        coEvery { featureQueryService.getByProjectId(mockProjectId) } returns ApiResult.Success(mockData)
        val expected = ApiResult.Success(mockData.map { it.toUseCaseDto() })
        the(getFeaturesByProjectIdUseCase(mockProjectId)).shouldBeEqual(expected)
    }

    @Test
    fun Feature取得失敗時はFailureを返す() = runBlocking {
        coEvery { featureQueryService.getByProjectId(mockProjectId) } returns ApiResult.Failure(QueryServiceException.DatabaseException("Error"))
        val expected = ApiResult.Failure(GetFeatureUseCaseException.DatabaseException("Error"))
        the(getFeaturesByProjectIdUseCase(mockProjectId)).shouldBeEqual(expected)
    }


    @AfterTest
    fun 呼び出し回数のカウント() {
        coVerify(exactly = 1) { featureQueryService.getByProjectId(mockProjectId) }
        confirmVerified(featureQueryService)
    }
}
