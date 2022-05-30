package test

import apis.addProduct
import apis.deleteProduct
import apis.getProducts
import apis.modifyProduct
import assertions.andCheckCodeIs
import assertions.andVerifyResponseIs
import assertions.dateToTimestamp
import commom.BaseTest
import expectations.addProductsExpectedResponse
import expectations.deleteAndPatchExpectedResponse
import io.kotest.matchers.longs.shouldBeBetween
import models.GetProductsResponse
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import utils.fromJson
import kotlin.collections.ArrayList

private const val THREE_HOURS = 10800

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductsTests : BaseTest() {
    private var productsIds: ArrayList<String> = arrayListOf("")


    @BeforeAll
    fun beforeAll() {
        getProducts().body.fromJson(GetProductsResponse::class.java).values.forEach { productsIds.add(it.id) }
        for (i in 1 until (productsIds.size)) {
            deleteProduct(productsIds.get(i)).andCheckCodeIs(200)
            getProducts().andVerifyResponseIs(200, GetProductsResponse(arrayListOf()))
        }
    }

    @Test
    fun `Success management products`() {
        val responseAdd = addProduct(100, "Test", "Test")
            .andVerifyResponseIs(201, addProductsExpectedResponse()).values[0]
        addProduct(100, "Test", "Test").andCheckCodeIs(500) //why 500?
        checkDate(responseAdd.modified)
        checkDate(responseAdd.created)

//        modifyProduct(productId = responseAdd.id).andVerifyResponseIs(
//            200,
//            deleteAndPatchExpectedResponse("CHANGE", 10, "Test2", "Test2")
  //      )
    }
}


private fun checkDate(date: String, interval: Long = 5) {
    (dateToTimestamp(date = date) / 1000).shouldBeBetween(
        System.currentTimeMillis() / 1000L - THREE_HOURS - interval,
        System.currentTimeMillis() / 1000L - THREE_HOURS + interval
    )
}