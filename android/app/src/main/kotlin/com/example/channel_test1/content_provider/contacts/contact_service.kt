package com.example.channel_test1.content_provider.contacts



import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import io.flutter.plugin.common.MethodChannel
import io.flutter.embedding.engine.FlutterEngine

class ContactService(private val contentResolver: ContentResolver) {

    // Method to fetch contacts from the device
    fun getContacts(): List<Map<String, String>> {
        val contacts = mutableListOf<Map<String, String>>()

        // Query the Contacts Provider
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

            // Check if columns are valid
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

    // Method to set up MethodChannel and handle calls
    fun setup(flutterEngine: FlutterEngine) {
        val channel = "com.example.your_project/contacts"

        MethodChannel(flutterEngine.dartExecutor, channel).setMethodCallHandler { call, result ->
            if (call.method == "getContacts") {
                // Fetch contacts when requested
                val contacts = getContacts()
                if (contacts.isNotEmpty()) {
                    // Return the contacts to Flutter
                    result.success(contacts)
                } else {
                    // Return an error if no contacts found
                    result.error("UNAVAILABLE", "No contacts found", null)
                }
            } else {
                // If the method is not implemented, return an error
                result.notImplemented()
            }
        }
    }
}
