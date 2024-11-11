package com.example.channel_test1.content_provider.contacts



import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import io.flutter.plugin.common.MethodChannel
import io.flutter.embedding.engine.FlutterEngine

class ContactService(private val contentResolver: ContentResolver) {

    fun getContacts(): List<Map<String, String>> {
        val contacts = mutableListOf<Map<String, String>>()

        val cursor: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ),
            null, null, null
        )

        cursor?.let {
            val nameColumnIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberColumnIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            if (nameColumnIndex != -1 && numberColumnIndex != -1) {
                while (it.moveToNext()) {
                    val name = it.getString(nameColumnIndex) ?: "Unknown"
                    val number = it.getString(numberColumnIndex) ?: "No number"

                    val contact = mapOf(
                        "name" to name,
                        "phone" to number
                    )
                    contacts.add(contact)
                }
            }
            it.close()
        }

        return contacts
    }

    fun setup(flutterEngine: FlutterEngine) {
        val channel = "channels/contacts"

        MethodChannel(flutterEngine.dartExecutor, channel).setMethodCallHandler { call, result ->
            if (call.method == "getContacts") {
                val contacts = getContacts()
                if (contacts.isNotEmpty()) {
                    result.success(contacts)
                } else {
                    result.error("UNAVAILABLE", "No contacts found", null)
                }
            } else {
                result.notImplemented()
            }
        }
    }
}
