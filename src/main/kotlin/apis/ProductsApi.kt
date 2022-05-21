package apis

import models.AddProductsRequest
import models.Category
import utils.get
import utils.post

fun getProducts() = get<String>(path = GET_PRODUCTS_URL)

fun addProduct(amount: Int, discount: Int, percentDiscount: Int) = post(path = PRODUCT_ROOT_URL, request = AddProductsRequest(amount,
listOf(Category("Test", true, "Test")), discount, true, "Test", percentDiscount))
