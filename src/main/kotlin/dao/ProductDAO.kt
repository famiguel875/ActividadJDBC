package dao

import Product
import db_connection.DataSourceFactory
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.Statement


class ProductDAO {

    private val dataSource = DataSourceFactory.getDS(DataSourceFactory.DataSourceType.JDBC)

    fun createProduct(product: Product): Int {
        var connection: Connection? = null
        var productId = -1
        try {
            connection = dataSource.connection
            connection.autoCommit = false

            val insertQuery = "INSERT INTO products (id, name, price, description, brand, category) VALUES (?, ?, ?, ?, ?, ?);"
            val preparedStatement: PreparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)

            preparedStatement.setInt(1, product.id)
            preparedStatement.setString(2, product.name)
            preparedStatement.setFloat(3, product.price)
            preparedStatement.setString(4, product.description)
            preparedStatement.setString(5, product.brand)
            preparedStatement.setString(6, product.category)

            val affectedRows = preparedStatement.executeUpdate()

            if (affectedRows == 0) {
                throw RuntimeException("Creating product failed, no rows affected.")
            }

            val generatedKeys = preparedStatement.generatedKeys
            if (generatedKeys.next()) {
                productId = generatedKeys.getInt(1)
            } else {
                throw RuntimeException("Creating product failed, no ID obtained.")
            }

            connection.commit()
        } catch (e: Exception) {
            connection?.rollback()
            e.printStackTrace()
        } finally {
            connection?.close()
        }

        return productId
    }
}