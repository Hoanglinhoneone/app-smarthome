package ptit.iot.smarthome.data.repository

import ptit.iot.smarthome.data.entity.ActionEntity

interface ActionRepository {
    suspend fun getActions(): List<ActionEntity>
    suspend fun insertHistory(action: ActionEntity)
    suspend fun deleteAllAction()
}