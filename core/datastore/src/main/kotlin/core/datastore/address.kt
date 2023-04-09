package core.datastore

import com.example.android.core.model.Address
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val SPTag = "Addresses"

@Serializable
class AddressStore(
    val addresses: List<Address>
) {
    fun store(): Boolean = try {
        val jsonString = Json.encodeToString(this)
        settings[SPTag] = jsonString
        true
    } catch (e: Exception) {
        false
    }

    companion object {
        fun retrieve(): AddressStore {
            val jsonString = settings[SPTag, "[]"]
            return Json.decodeFromString<AddressStore>(jsonString)
        }
    }
}