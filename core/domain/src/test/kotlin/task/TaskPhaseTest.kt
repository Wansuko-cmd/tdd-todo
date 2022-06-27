@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package task

import org.javalite.test.jspec.JSpec.the
import kotlin.test.Test

class TaskPhaseTest {
    @Test
    fun TaskPhase等価性のテスト() {
        the(TaskPhase.Todo).shouldBeEqual(TaskPhase.Todo)
        the(TaskPhase.Red).shouldBeEqual(TaskPhase.Red)
        the(TaskPhase.Green).shouldNotBeEqual(TaskPhase.Refactor)
        the(TaskPhase.Refactor).shouldNotBeEqual(TaskPhase.Done)
    }
}
