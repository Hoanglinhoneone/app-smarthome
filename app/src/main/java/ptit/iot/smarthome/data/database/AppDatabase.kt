package ptit.iot.smarthome.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ptit.iot.smarthome.data.database.dao.ActionDao
import ptit.iot.smarthome.data.database.dao.LightDao
import ptit.iot.smarthome.data.entity.ActionEntity
import ptit.iot.smarthome.data.entity.LightEntity

@Database(entities = [LightEntity::class, ActionEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun lightDao(): LightDao
    abstract fun historyDao(): ActionDao
}


// change table
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Tạo bảng mới với schema mới
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS lights_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                timestamp INTEGER NOT NULL,
                light REAL NOT NULL
            )
        """)
        // Xóa bảng cũ
        database.execSQL("DROP TABLE lights")
        // Đổi tên bảng mới thành bảng cũ
        database.execSQL("ALTER TABLE lights_new RENAME TO lights")

        database.execSQL("""
            CREATE TABLE IF NOT EXISTS actions_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                timestamp INTEGER NOT NULL,
                action TEXT NOT NULL,
                state TEXT NOT NULL
            )
        """)
        database.execSQL("DROP TABLE actions")
        database.execSQL("ALTER TABLE actions_new RENAME TO actions")
    }
}