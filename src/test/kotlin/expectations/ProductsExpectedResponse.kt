package expectations

import assertions.ANY_UUID
import assertions.TIMESTAMP
import models.*

fun getProductsExpectedResponse() = GetProductsResponse(
    values = arrayListOf(
        Value(
            id = ANY_UUID, "Test",
            arrayListOf(CreatedCategory(ANY_UUID, "Test", "Test", true, TIMESTAMP, TIMESTAMP)),
            100, 1, 10, true, TIMESTAMP, TIMESTAMP
        )
    )
)

fun deleteAndPatchExpectedResponse(action: String, amount: Int, brand: String , nameProduct: String) = DeleteAndPatchResponse(
    action = action, Value(
        id = ANY_UUID, nameProduct,
        arrayListOf(CreatedCategory(ANY_UUID, "Test", brand, true, TIMESTAMP, TIMESTAMP)),
        amount, 1, 10, true, TIMESTAMP, TIMESTAMP
    )
)

fun addProductsExpectedResponse() = AddProductsResponse(
    "ADD", arrayListOf(
        Value(
            id = ANY_UUID, "Test",
            arrayListOf(CreatedCategory(ANY_UUID, "Test", "Test", true, TIMESTAMP, TIMESTAMP)),
            100, 1, 10, true, TIMESTAMP, TIMESTAMP
        )
    )
)