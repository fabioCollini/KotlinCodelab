package com.example.android.myaddressbook;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    private AppCompatActivity activity;

    private ArrayList<Contact> contacts;

    public ContactsAdapter(AppCompatActivity activity, ArrayList<Contact> contacts) {
        this.activity = activity;
        this.contacts = contacts;
    }

    @Override public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(activity, parent);
    }

    @Override public void onBindViewHolder(ContactViewHolder holder, final int position) {
        holder.bind(contacts.get(position));
    }

    @Override public int getItemCount() {
        return contacts.size();
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void replaceContacts(List<Contact> newContacts) {
        contacts.clear();
        contacts.addAll(newContacts);
        notifyDataSetChanged();
    }

    public void saveContact(Contact editedContact, int contactPosition) {
        if (contactPosition == -1) {
            contacts.add(editedContact);
            notifyItemInserted(contacts.size());
        } else {
            contacts.set(contactPosition, editedContact);
            notifyItemChanged(contactPosition);
        }
    }
}
