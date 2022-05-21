package assertions


import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldBeEqualIgnoringCase
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldMatch
import io.kotest.mpp.reflection
import models.HttpResponse
import org.slf4j.Logger
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import mu.KotlinLogging
import utils.deepCopy
import utils.fromJson

val log: Logger = KotlinLogging.logger { }


fun <T : Any?> HttpResponse<String>.andVerifyResponseIs(code: Any?, expectedResponse: T): T {
    val actualResponse: Any
    try {
        this.code shouldBe code
        val expected: Any
        if (expectedResponse !is String) {
            actualResponse = this.body.fromJson(expectedResponse!!::class.java)
            expected = processRegexp(expectedResponse, actualResponse)
        } else {
            actualResponse = this.body
            expected = expectedResponse
        }

        actualResponse shouldBe expected
    } catch (assertionError: AssertionError) {
        log.warn("Assertion failed for response with code ${this.code}\nand body: ${this.body}")
        throw assertionError
    }

    @Suppress("UNCHECKED_CAST")
    return actualResponse as T
}

fun HttpResponse<String>.andVerifyResponseIsEmptyWithCode(code: Int) {
    this.code shouldBe code
    // HttpResponse contains this.body = "(empty)" when response has no body
    this.body shouldBe ""
}

fun HttpResponse<String>.andVerifyResponseContains(code: Int, expectedResponse: String) {
    this.code shouldBe code
    this.body.shouldContain(expectedResponse)
}

fun HttpResponse<String>.andVerifyResponseIsNotEmpty(code: Int): HttpResponse<String> {
    this.code shouldBe code
    this.body shouldNotBe ""
    return HttpResponse(code, body)
}

fun HttpResponse<ByteArray>.andVerifyResponseIs(code: Any?, expectedResponse: ByteArray): ByteArray {
    this.code shouldBe code
    this.body shouldBe expectedResponse

    return this.body
}

fun HttpResponse<String>.andCheckCodeIs(expectedCode: Any?): Any {
    this.code shouldBe expectedCode
    return this.body
}

fun HttpResponse<ByteArray>.andCheckCodeIs(expectedCode: Any?) =
    this.code shouldBe expectedCode

/**
 * Matches regexp in [initialExpected] data model with values in according field in [actual] data model.
 * If values are matched - field in [initialExpected] is overwritten with value from actual.
 * If not - nothing happens & comparison should be failed on the further steps.
 * E.g.
 * expected - {"firstValue": 1, "secondValue": "regexp: [\d+]"}, actual -  {"firstValue": 1, "secondValue": "3"}
 * result of this function will be {"firstValue": 1, "secondValue": "3"} (as 3 matches regexp in expected data)
 *
 * @param initialExpected - some data model.
 * @param actual - data model of the same type as [initialExpected].
 * @return updated expected data model
 */

private fun processRegexp(initialExpected: Any, actual: Any): Any {
    val expected = initialExpected.deepCopy()
    val nestedModels = mutableListOf<Triple<String, Any, Any>>()
    reflection.primaryConstructorMembers(expected::class).map { prop ->
        val expectedProp = prop.call(expected)
        val actualProp = prop.call(actual)
        if (expectedProp is String && (
                    expectedProp.startsWith("regexp: ") || expectedProp.startsWith("traceId=regexp: ")
                    )
        ) {
            val isPrefixMatched = expectedProp
                .removePrefix("traceId=regexp: ").toRegex().matches(actualProp.toString())
            val isMatched = expectedProp.removePrefix("regexp: ").toRegex().matches(actualProp.toString())
            if (isMatched || isPrefixMatched) {
                val property = getPropertyValue(prop.name, expected)
                setProperty(property, expected, actualProp)
            }
        }

        if (expectedProp is ArrayList<*> && actualProp is ArrayList<*> &&
            expectedProp.size == actualProp.size
        ) {
            val updatedProperties = mutableListOf<Any>()
            expectedProp.forEachIndexed { index, element ->
                updatedProperties.add(processRegexp(element, actualProp.get(index)))
            }

            setProperty(getPropertyValue(prop.name, expected), expected, updatedProperties)
        }

        if (prop.type.toString().startsWith("models") &&
            expectedProp != null && actualProp != null
        ) {
            nestedModels.add(Triple(prop.name, expectedProp, actualProp))
        }
    }

    nestedModels.forEach {
        val updatedProperty = processRegexp(it.second, it.third)
        setProperty(getPropertyValue(it.first, expected), expected, updatedProperty)
    }

    return expected
}


private fun getPropertyValue(propName: String, classInstance: Any): KProperty1<out Any, *>? =
    classInstance::class.memberProperties.find { it.name == propName }

private fun setProperty(property: KProperty1<out Any, *>?, classInstance: Any, propertyValue: Any?) {
    if (property is KMutableProperty<*>) {
        property.setter.call(classInstance, propertyValue)
    }
}



