@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package task

import io.mockk.every
import io.mockk.mockkStatic
import org.javalite.test.jspec.JSpec.*
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test

class ChangeTaskPropertyTest {

    private val mockTask = Task.create(
        title = TaskTitle("mockTaskTitle"),
        description = TaskDescription("mockTaskDescription"),
    )

    @Test
    fun TaskのTitleを書き換えたコピーを作成() {
        val copiedTask = mockTask.changeTitle(TaskTitle("copiedTaskTitle"))
        the(copiedTask.title).shouldBeEqual(TaskTitle("copiedTaskTitle"))
    }

    @Test
    fun TaskのDescriptionを書き換えたコピーを作成() {
        val copiedTask = mockTask.changeDescription(TaskDescription("copiedTaskDescription"))
        the(copiedTask.description).shouldBeEqual(TaskDescription("copiedTaskDescription"))
    }

    @Test
    fun TaskのPhaseを書き換えたコピーを作成() {
        val copiedTask = mockTask.changePhase(TaskPhase.Refactor)
        the(copiedTask.phase).shouldBeEqual(TaskPhase.Refactor)
    }
}
