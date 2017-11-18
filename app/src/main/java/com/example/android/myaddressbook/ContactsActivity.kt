/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.myaddressbook

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_contacts.*
import java.util.*

class ContactsActivity : AppCompatActivity() {

    private lateinit var adapter: ContactsAdapter
    private lateinit var repository: ContactsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        repository = ContactsRepository(assets, getPreferences(Context.MODE_PRIVATE))
        adapter = ContactsAdapter(this, ArrayList(repository.loadContacts()))

        setSupportActionBar(toolbar)

        contact_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        contact_list.adapter = adapter

        fab.setOnClickListener { ContactDetailDialog.show(this) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_contacts, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_clear -> {
            adapter.replaceContacts(emptyList())
            repository.saveContacts(adapter.contacts)
            true
        }
        R.id.action_generate -> {
            adapter.replaceContacts(repository.generateContacts())
            repository.saveContacts(adapter.contacts)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    fun saveContact(editedContact: Contact, contactPosition: Int) {
        adapter.saveContact(editedContact, contactPosition)
        repository.saveContacts(adapter.contacts)
    }
}
