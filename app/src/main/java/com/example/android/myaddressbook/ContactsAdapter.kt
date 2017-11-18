package com.example.android.myaddressbook

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import java.util.*

class ContactsAdapter(
        private val activity: AppCompatActivity,
        val contacts: ArrayList<Contact>
) : RecyclerView.Adapter<ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ContactViewHolder(activity, parent)

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount() = contacts.size

    fun replaceContacts(newContacts: List<Contact>) {
        contacts.clear()
        contacts += newContacts
        notifyDataSetChanged()
    }

    fun saveContact(editedContact: Contact, contactPosition: Int) {
        if (contactPosition == -1) {
            contacts += editedContact
            notifyItemInserted(contacts.size)
        } else {
            contacts[contactPosition] = editedContact
            notifyItemChanged(contactPosition)
        }
    }
}
