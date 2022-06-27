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
        val uuid = "5af48f3b-468b-4ae0-a065-7d7ac70b37a8"
        every { UUID.randomUUID().toString() } returns uuid
        mockTask = Task(
            featureId = FeatureId("mockedFeatureId"),
            title = TaskTitle("mockTaskTitle"),
            description = TaskDescription("mockTaskDescription"),
        )
    }

    @Test
    fun Taskはプロパティの値が同じであれば等価とみなされる() {
        a(mockTask).shouldBeEqual(mockTask)
    }

    @Test
    fun Taskのプロパティは以下の通り() {
        the(mockTask.featureId).shouldBeEqual(FeatureId("mockedFeatureId"))
        the(mockTask.title).shouldBeEqual(TaskTitle("mockTaskTitle"))
        the(mockTask.description).shouldBeEqual(TaskDescription("mockTaskDescription"))
        the(mockTask.phase).shouldBeEqual(TaskPhase.Todo)
    }

    @Test
    fun copyWithPhaseを用いてPhaseが変わったTaskを取得可能() {
        the(mockTask.copyWithPhase(newPhase = TaskPhase.Refactor).phase).shouldBeEqual(TaskPhase.Refactor)
    }

    @Test
    fun TaskのIDはUUIDである() {
        the(mockTask.id).shouldBeEqual(TaskId("5af48f3b-468b-4ae0-a065-7d7ac70b37a8"))
    }
}
