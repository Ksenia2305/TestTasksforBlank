package test

import apis.getProducts
import commom.BaseTest
import org.junit.jupiter.api.Test

class ProductsTests: BaseTest() {

    @Test
    fun `Success get and add products`(){
        getProducts()
    }
}