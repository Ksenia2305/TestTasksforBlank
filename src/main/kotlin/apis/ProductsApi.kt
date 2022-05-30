package apis

import models.AddAndPatchProductsRequest
import models.Category
import utils.delete
import utils.get
import utils.patch
import utils.post

fun getProducts() = get<String>(path = GET_PRODUCTS_URL)

fun addProduct(amount: Int = 100, brand: String = "Test", nameProduct: String = "Test") = post(
    path = PRODUCT_ROOT_URL, request = AddAndPatchProductsRequest(
        amount,
        listOf(Category(brand, true, "Test")), 1, true, nameProduct, 10
    )
)

fun modifyProduct(amount: Int = 10, brand: String = "Test2", nameProduct: String = "Test2", productId: String?) = patch(
    path = "$PRODUCT_ROOT_URL/$productId", request = AddAndPatchProductsRequest(
        amount,
        listOf(Category(brand, true, "Test")), 1, true, nameProduct, 10
    )
)

fun deleteProduct(productId: String?) = delete(path = "$PRODUCT_ROOT_URL/$productId")
