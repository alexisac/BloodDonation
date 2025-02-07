package blood.blooddonation.item

import android.content.Context
import android.util.Log
import blood.blooddonation.utils.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import blood.blooddonation.utils.Result

class ItemRepository(
    private val itemService: ItemService,
    private val itemWsClient: ItemWsClient,
    private val itemDao: ItemDao,
    private val context: Context
) {
    private fun getBearerToken() = "Bearer ${Api.tokenInterceptor.token}"

    suspend fun openWsClient() {
        withContext(Dispatchers.IO) {
            getItemEvents().collect {
                if (it is Result.Success) {
                    val itemEvent = it.data;
                    when (itemEvent?.type) {
                        "created" -> handleItemCreated(itemEvent.payload)
                        "updated" -> handleItemUpdated(itemEvent.payload)
                        "deleted" -> handleItemDeleted(itemEvent.payload)
                    }
                }
            }
        }
    }

    suspend fun closeWsClient() {
        withContext(Dispatchers.IO) {
            itemWsClient.closeSocket()
        }
    }

    suspend fun getItemEvents(): Flow<Result<ItemEvent>> = callbackFlow {
        itemWsClient.openSocket(
            onEvent = {
                if (it != null) {
                    trySend(Result.Success(it))
                }
            },
            onClosed = { close()},
            onFailure = {
                close()
            });
        awaitClose { itemWsClient.closeSocket() }
    }

    suspend fun update(item: Item): Item {
        return try {
            val updatedItem = itemService.update(itemId = item._id, item = item, authorization = getBearerToken())
            handleItemUpdated(updatedItem)
            updatedItem
        } catch (e: Exception){
            handleItemUpdated(item)
            item
        }
    }

    suspend fun save(item: Item): Item {
        return try {
            val createdItem = itemService.create(item = item, authorization = getBearerToken())
            handleItemCreated(createdItem)
            createdItem
        } catch (e: Exception){
            handleItemCreated(item)
            item
        }
    }

    private suspend fun handleItemDeleted(item: Item) {
    }

    private suspend fun handleItemUpdated(item: Item) {
        itemDao.update(item)
    }

    private suspend fun handleItemCreated(item: Item) {
        itemDao.insert(item)
    }


    fun setToken(token: String) {
        itemWsClient.authorize(token)
    }
}