package com.example.android.myaddressbook

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.input_contact_dialog.view.*

class ContactDetailDialog : DialogFragment(), TextWatcher {

    private lateinit var firstNameEdit: EditText
    private lateinit var lastNameEdit: EditText
    private lateinit var emailEdit: EditText

    private var entryValid: Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Inflates the dialog view
        val dialogView = LayoutInflater.from(activity)
                .inflate(R.layout.input_contact_dialog, null)

        firstNameEdit = dialogView.edittext_firstname
        lastNameEdit = dialogView.edittext_lastname
        emailEdit = dialogView.edittext_email

        // Listens to text changes to validate after each key press
        firstNameEdit.addTextChangedListener(this)
        lastNameEdit.addTextChangedListener(this)
        emailEdit.addTextChangedListener(this)

        val editedContact = arguments?.getParcelable<Contact>(CONTACT)

        dialogView.save.setOnClickListener {
            // If input is valid, creates and saves the new contact,
            // or replaces it if the contact is being edited
            if (entryValid) {
                editedContact?.email = emailEdit.text.toString()
                val contactToSave = editedContact ?: Contact(
                        firstNameEdit.text.toString(),
                        lastNameEdit.text.toString(),
                        emailEdit.text.toString()
                )
                (activity as ContactsActivity).saveContact(contactToSave, arguments?.getInt(POSITION) ?: -1)

                dismiss()
            } else {
                Toast.makeText(activity,
                        R.string.contact_not_valid,
                        Toast.LENGTH_SHORT).show()
            }
        }

        val dialogTitle = if (editedContact != null)
            getString(R.string.edit_contact)
        else
            getString(R.string.new_contact)

        // If the contact is being edited, populates the EditText with the old
        // information
        if (editedContact != null) {
            firstNameEdit.setText(editedContact.firstName)
            firstNameEdit.isEnabled = false
            lastNameEdit.setText(editedContact.lastName)
            lastNameEdit.isEnabled = false
            emailEdit.setText(editedContact.email)
        }

        return AlertDialog.Builder(activity!!)
                .setView(dialogView)
                .setTitle(dialogTitle)
                .create()
    }

    /**
     * Validates the user input when adding a new contact each time the test
     * is changed.
     *
     * @param editable The text that was changed. It is not used as you get the
     * text from member variables.
     */
    override fun afterTextChanged(editable: Editable) {
        val firstNameValid = !firstNameEdit.text.toString().isEmpty()
        val lastNameValid = !lastNameEdit.text.toString().isEmpty()
        val emailValid = Patterns.EMAIL_ADDRESS
                .matcher(emailEdit.text).matches()

        firstNameEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                if (firstNameValid) R.drawable.ic_pass else R.drawable.ic_fail, 0)
        lastNameEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                if (lastNameValid) R.drawable.ic_pass else R.drawable.ic_fail, 0)
        emailEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                if (emailValid) R.drawable.ic_pass else R.drawable.ic_fail, 0)

        entryValid = firstNameValid && lastNameValid && emailValid
    }

    /**
     * Override methods for the TextWatcher interface, used to validate user
     * input.
     */
    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

    companion object {

        private val CONTACT = "contact"
        private val POSITION = "position"

        @JvmStatic fun show(activity: AppCompatActivity, contact: Contact?, position: Int) {
            ContactDetailDialog().let {
                it.arguments = Bundle().apply {
                    if (contact != null) {
                        putParcelable(CONTACT, contact)
                    }
                    putInt(POSITION, position)
                }
                it.show(activity.supportFragmentManager, "detail")
            }
        }
    }
}
