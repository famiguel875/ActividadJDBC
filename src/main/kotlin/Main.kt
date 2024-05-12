import dao.ProductDAO
import entity.Product

fun main() {
    val product = Product(1, "Smartphone", 999.99f, "The latest smartphone model", "Apple", "Electronics")
    val productId = ProductDAO().createProduct(product)
    println("entity.Product ID: $productId")
}