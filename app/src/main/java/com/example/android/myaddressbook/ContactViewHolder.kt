package com.example.android.myaddressbook

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.contact_list_item.view.*

class ContactViewHolder(
        activity: AppCompatActivity,
        parent: ViewGroup
) : RecyclerView.ViewHolder(LayoutInflater.from(activity).inflate(R.layout.contact_list_item, parent, false)) {
    private var contact: Contact? = null

    init {
        itemView.setOnClickListener { ContactDetailDialog.show(activity, contact, adapterPosition) }
    }

    fun bind(contact: Contact) {
        this.contact = contact
        val fullName = "${contact.firstName} ${contact.lastName}"
        itemView.textview_name.text = fullName
        itemView.textview_email.text = contact.email
    }
}
