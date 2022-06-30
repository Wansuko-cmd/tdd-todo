@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package task

import feature.FeatureId
import io.mockk.every
import io.mockk.mockkStatic
import org.javalite.test.jspec.JSpec.*
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test

class TaskTest {

    private lateinit var mockTask: Task

    @BeforeTest
    fun setup() {
        mockkStatic(UUID::class)
        val uuid = "mockUUID"
        every { UUID.randomUUID().toString() } returns uuid
        mockTask = Task(
            featureId = FeatureId("mockFeatureId"),
            title = TaskTitle("mockTaskTitle"),
            description = TaskDescription("mockTaskDescription"),
            phase = TaskPhase.Todo,
        )
    }

    @Test
    fun Taskのプロパティの等価性の確認() {
        the(mockTask.id).shouldBeEqual(TaskId("mockUUID"))
        the(mockTask.featureId).shouldBeEqual(FeatureId("mockFeatureId"))
        the(mockTask.title).shouldBeEqual(TaskTitle("mockTaskTitle"))
        the(mockTask.description).shouldBeEqual(TaskDescription("mockTaskDescription"))
        the(mockTask.phase).shouldBeEqual(TaskPhase.Todo)
    }

    @Test
    fun Taskはプロパティの値が同じであれば等価とみなされる() {
        val otherTask = Task(
            id = mockTask.id,
            featureId = mockTask.featureId,
            title = mockTask.title,
            description = mockTask.description,
            phase = mockTask.phase
        )
        the(mockTask).shouldBeEqual(otherTask)
    }

    @Test
    fun copyWithPhaseを用いてPhaseが変わったTaskを取得可能() {
        the(mockTask.copyWithPhase(newPhase = TaskPhase.Refactor).phase).shouldBeEqual(TaskPhase.Refactor)
    }
}
