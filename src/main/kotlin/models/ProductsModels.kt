package models

data class AddProductsRequest(
    var amount: Int?, var categories: List<Category>?, var discount: Int?, var isVisible: Boolean?,
    var name: String?, var percentDiscount: Int?
)

data class GetProductsResponse(var values: List<Values>)
data class Category(var brand: String?, var isVisible: Boolean?, var name: String?)
data class CreatedCategory(
    var id: String, var brand: String?, var isVisible: Boolean?, var name: String?, var created: String,
    var modified: String
)
data class Values(var id: String, var productName: String, var categories: List<CreatedCategory>, var amount: Int, var discount: Int,
var percentDiscount: Int, var isVisible: Boolean, var created: String, var modified: String)

