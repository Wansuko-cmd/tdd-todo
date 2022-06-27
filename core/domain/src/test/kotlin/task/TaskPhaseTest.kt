@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package task

import org.javalite.test.jspec.JSpec.the
import kotlin.test.Test

class TaskPhaseTest {
    @Test
    fun TaskPhase等価性のテスト() {
        the(TaskPhase.Todo).shouldBeEqual(TaskPhase.Todo)
        the(TaskPhase.InProgress).shouldBeEqual(TaskPhase.InProgress)
        the(TaskPhase.Refactor).shouldNotBeEqual(TaskPhase.Done)
    }
}
