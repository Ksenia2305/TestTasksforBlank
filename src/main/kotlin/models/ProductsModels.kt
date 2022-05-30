package models

data class AddAndPatchProductsRequest(
    var amount: Int?, var categories: List<Category>?, var discount: Int?, var isVisible: Boolean?,
    var name: String?, var percentDiscount: Int?
)

data class GetProductsResponse(var values: List<Value>)
data class GetProductResponse(var value: Value)
data class AddProductsResponse(val action: String, var values: List<Value>)
data class DeleteAndPatchResponse(val action: String, var value: Value)
data class Category(var brand: String?, var isVisible: Boolean?, var name: String?)
data class CreatedCategory(
    var id: String, var name: String?, var brand: String?, var isVisible: Boolean?, var created: String,
    var modified: String
)
data class Value(var id: String, var productName: String, var categories: List<CreatedCategory>, var amount: Int, var discount: Int,
                 var percentDiscount: Int, var isVisible: Boolean, var created: String, var modified: String)

