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
package com.example.android.myaddressbook;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

public class ContactsActivity extends AppCompatActivity {

    private ContactsAdapter adapter;
    private ContactsRepository repository;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        repository = new ContactsRepository(getAssets(), getPreferences(Context.MODE_PRIVATE));
        adapter = new ContactsAdapter(this, new ArrayList<>(repository.loadContacts()));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.contact_list);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                ContactDetailDialog.show(ContactsActivity.this, null, -1);
            }
        });
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                adapter.replaceContacts(Collections.<Contact>emptyList());
                repository.saveContacts(adapter.getContacts());
                return true;
            case R.id.action_generate:
                adapter.replaceContacts(repository.generateContacts());
                repository.saveContacts(adapter.getContacts());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveContact(Contact editedContact, int contactPosition) {
        adapter.saveContact(editedContact, contactPosition);
        repository.saveContacts(adapter.getContacts());
    }
}
