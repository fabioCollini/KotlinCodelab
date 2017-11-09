package com.example.android.myaddressbook;

import android.content.SharedPreferences;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ContactsRepository {

    private AssetManager assets;
    private SharedPreferences prefs;

    private static final String CONTACT_KEY = "contact_key";

    public ContactsRepository(AssetManager assets, SharedPreferences prefs) {
        this.assets = assets;
        this.prefs = prefs;
    }

    public List<Contact> loadContacts() {
        return parseContacts(prefs.getString(CONTACT_KEY, "[]"));
    }

    public void saveContacts(List<Contact> contacts) {
        prefs.edit()
                .putString(CONTACT_KEY, new Gson().toJson(contacts))
                .apply();
    }

    public List<Contact> generateContacts() {
        String contactsString = readContactJsonFile();
        return parseContacts(contactsString);
    }

    private List<Contact> parseContacts(String contactsString) {
        return new Gson().fromJson(contactsString, new TypeToken<List<Contact>>() {
        }.getType());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private String readContactJsonFile() {
        try {
            InputStream inputStream = assets.open("mock_contacts.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            return new String(buffer);
        } catch (IOException e) {
            throw new RuntimeException("Error loading contacts", e);
        }
    }
}
