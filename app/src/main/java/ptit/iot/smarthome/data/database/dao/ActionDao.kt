package ptit.iot.smarthome.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ptit.iot.smarthome.data.entity.ActionEntity

@Dao
interface ActionDao {
    @Query("SELECT * FROM actions")
    suspend fun getHistory(): List<ActionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: ActionEntity)

    @Query("DELETE FROM actions")
    suspend fun deleteAllHistory()

}