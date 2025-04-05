package ptit.iot.smarthome.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ptit.iot.smarthome.data.database.dao.ActionDao
import ptit.iot.smarthome.data.database.dao.LightDao
import ptit.iot.smarthome.data.entity.ActionEntity
import ptit.iot.smarthome.data.entity.LightEntity

@Database(entities = [LightEntity::class, ActionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun lightDao(): LightDao
    abstract fun historyDao(): ActionDao
}