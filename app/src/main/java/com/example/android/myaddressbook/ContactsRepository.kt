package com.example.android.myaddressbook

import android.content.SharedPreferences
import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nalulabs.prefs.gson.jsonList

class ContactsRepository(private val assets: AssetManager, prefs: SharedPreferences) {

    private var contacts by prefs.jsonList<Contact>("contact_key")

    fun loadContacts(): List<Contact> = contacts

    fun saveContacts(contacts: List<Contact>) {
        this.contacts = contacts
    }

    fun generateContacts(): List<Contact> = Gson().fromJson(readContactJsonFile())

    private fun readContactJsonFile(): String {
        return assets.open("mock_contacts.json").bufferedReader().use {
            it.readText()
        }
    }
}

inline fun <reified T> Gson.fromJson(s: String): T {
    return fromJson(s, object : TypeToken<T>() {}.type)
}