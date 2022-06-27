@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.apiresult

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test
import kotlin.test.fail

class ApiResultConsumeTest {

    @Test
    fun 型がSuccessの時はsuccessを実行() {
        val mockedApiResult = ApiResult.Success("mockedSuccess")
        mockedApiResult.consume(
            success = { assertThat(it).isEqualTo("mockedSuccess") },
            failure = { fail("failureの方を実行") },
        )
    }

    @Test
    fun 型がFailureの時はfailureを実行() {
        val mockedApiResult = ApiResult.Failure("mockedFailure")
        mockedApiResult.consume(
            success = { fail("successの方を実行") },
            failure = { assertThat(it).isEqualTo("mockedFailure") },
        )
    }
}
