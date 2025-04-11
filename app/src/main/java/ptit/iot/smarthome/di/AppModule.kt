package ptit.iot.smarthome.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ptit.iot.smarthome.data.database.AppDatabase
import ptit.iot.smarthome.data.database.MIGRATION_1_2
import ptit.iot.smarthome.data.database.dao.ActionDao
import ptit.iot.smarthome.data.database.dao.LightDao
import ptit.iot.smarthome.data.repository.ActionRepository
import ptit.iot.smarthome.data.repository.ActionRepositoryImp
import ptit.iot.smarthome.data.repository.LightRepository
import ptit.iot.smarthome.data.repository.LightRepositoryImp
import ptit.iot.smarthome.utils.helper.mqtt.MqttHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "smarthome"
    ).addMigrations(MIGRATION_1_2).build()


    @Provides
    @Singleton
    fun provideLightDao(db: AppDatabase) = db.lightDao()

    @Provides
    @Singleton
    fun provideHistoryDao(db: AppDatabase) = db.historyDao()

    @Provides
    @Singleton
    fun provideLightRepository(dao: LightDao): LightRepository {
        return LightRepositoryImp(dao)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(dao: ActionDao): ActionRepository {
        return ActionRepositoryImp(dao)
    }


    @Singleton
    @Provides
    fun provideMqttHelper(@ApplicationContext context: Context): MqttHelper {
        return MqttHelper(context)
    }

    @Singleton
    @Provides
    fun provideFirebaseDatabase() = FirebaseDatabase.getInstance()
}