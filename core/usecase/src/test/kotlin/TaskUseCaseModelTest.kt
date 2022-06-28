@file:Suppress("NonAsciiCharacters", "TestFunctionName")

import TaskPhaseUseCaseModel.Companion.toUseCaseModel
import TaskUseCaseModel.Companion.toUseCaseModel
import feature.*
import org.javalite.test.jspec.JSpec.the
import org.junit.Test
import task.*

class TaskUseCaseModelTest {

    private val task = Task(
        id = TaskId("mockId"),
        featureId = FeatureId("mockProjectId"),
        title = TaskTitle("mockTitle"),
        description = TaskDescription("mockDescription"),
        phase = TaskPhase.Todo,
    )

    @Test
    fun Featureから変換可能() {
        val expected = TaskUseCaseModel(
            id = task.id.value,
            title = task.title.value,
            description = task.description.value,
            phase = task.phase.toUseCaseModel(),
        )
        the(task.toUseCaseModel()).shouldBeEqual(expected)
    }
}
