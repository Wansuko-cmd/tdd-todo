@file:Suppress("NonAsciiCharacters", "TestFunctionName")

import org.javalite.test.jspec.JSpec.the
import kotlin.test.Test

class Test {
    @Test
    fun 動作確認() {
        the(3).shouldBeEqual(3)
    }
}
