package db_connection

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.h2.jdbcx.JdbcDataSource
import javax.sql.DataSource

object DataSourceFactory {
    enum class DataSourceType {
        HIKARI,
        JDBC
    }

    fun getDS(dataSourceType: DataSourceType): DataSource {
        return when (dataSourceType) {
            DataSourceType.HIKARI -> {
                val config = HikariConfig()
                config.jdbcUrl = "jdbc:h2:./default"
                config.username = "user"
                config.password = "user"
                config.driverClassName = "org.h2.Driver"
                config.maximumPoolSize = 10
                config.isAutoCommit = true
                config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                HikariDataSource(config)
            }
            DataSourceType.JDBC -> {
                val dataSource = JdbcDataSource()
                dataSource.setURL("jdbc:h2:./default;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                dataSource.user = "user"
                dataSource.password = "user"
                dataSource
            }
        }
    }
}