import com.example.db.tables.db.tables.BlogsTable
import db.tables.UsersTable
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.initDB() {
    val url = environment.config.property("database.url").getString()
    val user = environment.config.property("database.user").getString()
    val driver = environment.config.property("database.driver").getString()
    val password = System.getenv("ktor_blog_database_password")

    val db = Database.connect(
        url = url,
        user = user,
        driver = driver,
        password = password
    )

    transaction(db) {
        SchemaUtils.create(UsersTable, BlogsTable)
    }
}