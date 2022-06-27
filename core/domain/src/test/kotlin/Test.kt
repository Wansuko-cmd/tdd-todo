@file:Suppress("NonAsciiCharacters", "TestFunctionName")

import org.javalite.test.jspec.JSpec.a
import kotlin.test.Test

class Test {
    @Test
    fun 動作確認() {
        a(3).shouldBeEqual(3)
    }
}
