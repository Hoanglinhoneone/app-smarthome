package ptit.iot.smarthome.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ptit.iot.smarthome.data.entity.LightEntity

@Dao
interface LightDao {


    @Query("SELECT * FROM lights ORDER BY id DESC LIMIT 20")
    fun getLightsStats(): Flow<List<LightEntity>>

    @Query("SELECT * FROM lights")
    fun getLights(): Flow<List<LightEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLight(light: LightEntity)

    @Query("DELETE FROM lights")
    suspend fun deleteAllLight()
}