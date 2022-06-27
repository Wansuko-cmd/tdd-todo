@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package task

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
        val uuid = "mockedUUID"
        every { UUID.randomUUID().toString() } returns uuid
        mockTask = Task(
            featureId = FeatureId("mockedFeatureId"),
            title = TaskTitle("mockTaskTitle"),
            description = TaskDescription("mockTaskDescription"),
        )
    }

    @Test
    fun Taskはプロパティの値が同じであれば等価とみなされる() {
        val otherTask = Task(
            featureId = FeatureId("mockedFeatureId"),
            title = TaskTitle("mockTaskTitle"),
            description = TaskDescription("mockTaskDescription"),
        )
        a(mockTask).shouldBeEqual(otherTask)
    }

    @Test
    fun Taskのプロパティは以下の通り() {
        the(mockTask.id).shouldBeEqual(TaskId("mockedUUID"))
        the(mockTask.featureId).shouldBeEqual(FeatureId("mockedFeatureId"))
        the(mockTask.title).shouldBeEqual(TaskTitle("mockTaskTitle"))
        the(mockTask.description).shouldBeEqual(TaskDescription("mockTaskDescription"))
        the(mockTask.phase).shouldBeEqual(TaskPhase.Todo)
    }

    @Test
    fun copyWithPhaseを用いてPhaseが変わったTaskを取得可能() {
        the(mockTask.copyWithPhase(newPhase = TaskPhase.Refactor).phase).shouldBeEqual(TaskPhase.Refactor)
    }
}
