package com.example.android.myaddressbook

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import org.jetbrains.anko.longToast

class ContactDetailDialog : DialogFragment() {

    private lateinit var nameEdit: EditText
    private lateinit var emailEdit: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Inflates the dialog view
        val dialogView = LayoutInflater.from(activity)
                .inflate(R.layout.input_contact_dialog, null)

        nameEdit = dialogView.findViewById(R.id.edittext_name)
        emailEdit = dialogView.findViewById(R.id.edittext_email)

        val editedContact = arguments?.getParcelable<Contact>(CONTACT)

        dialogView.findViewById<View>(R.id.save).setOnClickListener {
            if (validateFields()) {
                saveContact(editedContact)
                dismiss()
            } else {
                activity?.longToast(R.string.contact_not_valid)
            }
        }

        // If the contact is being edited, populates the EditText with the old
        // information
        editedContact?.let {
            nameEdit.setText(it.name)
            nameEdit.isEnabled = false
            emailEdit.setText(it.email)
        }

        return AlertDialog.Builder(activity!!)
                .setView(dialogView)
                .setTitle(if (editedContact != null) R.string.edit_contact else R.string.new_contact)
                .create()
    }

    private fun saveContact(editedContact: Contact?) {
        editedContact?.email = emailEdit.text.toString()
        val contactToSave = editedContact ?: Contact(
                nameEdit.text.toString(),
                emailEdit.text.toString()
        )
        (activity as ContactsActivity).saveContact(contactToSave, arguments?.getInt(POSITION) ?: -1)
    }

    private fun validateFields(): Boolean {
        val nameValid = !nameEdit.text.isEmpty()
        val emailValid = Patterns.EMAIL_ADDRESS.matcher(emailEdit.text).matches()

        nameEdit.updateValidationIcon(nameValid)
        emailEdit.updateValidationIcon(emailValid)

        return nameValid && emailValid
    }

    private fun EditText.updateValidationIcon(isValid: Boolean) {
        setCompoundDrawablesWithIntrinsicBounds(0, 0,
                if (isValid) R.drawable.ic_pass else R.drawable.ic_fail, 0)
    }

    companion object {

        private const val CONTACT = "contact"
        private const val POSITION = "position"

        @JvmStatic fun show(activity: AppCompatActivity, contact: Contact? = null, position: Int = -1) {
            ContactDetailDialog().let { fragment ->
                fragment.arguments = Bundle().apply {
                    contact?.let {
                        putParcelable(CONTACT, it)
                    }
                    putInt(POSITION, position)
                }
                fragment.show(activity.supportFragmentManager, "detail")
            }
        }
    }
}
