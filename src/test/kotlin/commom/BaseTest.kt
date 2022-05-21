package commom

import org.junit.jupiter.api.DynamicTest

open class BaseTest {
    fun <T : Any> withData(data: Map<String, T>, test: (T) -> Unit): List<DynamicTest> {
        return data.map { (k, v) -> DynamicTest.dynamicTest(k) { test(v) } }
    }
}