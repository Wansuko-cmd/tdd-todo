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
        mockTask = Task.create(
            title = TaskTitle("mockTaskTitle"),
            description = TaskDescription("mockTaskDescription"),
        )
    }

    @Test
    fun Taskを一から作成() {
        val mockTask = Task.create(
            title = TaskTitle("mockTaskTitle"),
            description = TaskDescription("mockTaskDescription"),
        )

        the(mockTask.id).shouldBeEqual(TaskId("mockUUID"))
        the(mockTask.title).shouldBeEqual(TaskTitle("mockTaskTitle"))
        the(mockTask.description).shouldBeEqual(TaskDescription("mockTaskDescription"))
        the(mockTask.phase).shouldBeEqual(TaskPhase.Todo)
    }

    @Test
    fun Taskを再生成() {
        val mockTask = Task.reconstruct(
            id = TaskId("mockTaskId"),
            title = TaskTitle("mockTaskTitle"),
            description = TaskDescription("mockTaskDescription"),
            phase = TaskPhase.Red,
        )

        the(mockTask.id).shouldBeEqual(TaskId("mockTaskId"))
        the(mockTask.title).shouldBeEqual(TaskTitle("mockTaskTitle"))
        the(mockTask.description).shouldBeEqual(TaskDescription("mockTaskDescription"))
        the(mockTask.phase).shouldBeEqual(TaskPhase.Red)
    }

    // TODO
    @Test
    fun Taskはプロパティの値が同じであれば等価とみなされる() {
//        val otherTask = Task(
//            id = mockTask.id,
//            featureId = mockTask.featureId,
//            title = mockTask.title,
//            description = mockTask.description,
//            phase = mockTask.phase
//        )
//        the(mockTask).shouldBeEqual(otherTask)
    }

    @Test
    fun copyWithPhaseを用いてPhaseが変わったTaskを取得可能() {
        the(mockTask.copyWithPhase(newPhase = TaskPhase.Refactor).phase).shouldBeEqual(TaskPhase.Refactor)
    }
}
