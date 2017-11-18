package com.example.android.myaddressbook;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ContactDetailDialog extends DialogFragment implements TextWatcher {

    private static final String CONTACT = "contact";
    private static final String POSITION = "position";

    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText emailEdit;

    private boolean entryValid;

    public static void show(AppCompatActivity activity, Contact contact, int position) {
        ContactDetailDialog fragment = new ContactDetailDialog();
        Bundle args = new Bundle();
        if (contact != null) {
            args.putParcelable(CONTACT, contact);
        }
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        fragment.show(activity.getSupportFragmentManager(), "detail");
    }

    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflates the dialog view
        View dialogView = LayoutInflater.from(getActivity())
                .inflate(R.layout.input_contact_dialog, null);

        firstNameEdit = dialogView.findViewById(R.id.edittext_firstname);
        lastNameEdit = dialogView.findViewById(R.id.edittext_lastname);
        emailEdit = dialogView.findViewById(R.id.edittext_email);

        // Listens to text changes to validate after each key press
        firstNameEdit.addTextChangedListener(this);
        lastNameEdit.addTextChangedListener(this);
        emailEdit.addTextChangedListener(this);

        final Contact editedContact = getArguments().getParcelable(CONTACT);

        dialogView.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (entryValid) {
                    saveContact(editedContact);
                    dismiss();
                } else {
                    Toast.makeText(getActivity(),
                            R.string.contact_not_valid,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // If the contact is being edited, populates the EditText with the old
        // information
        if (editedContact != null) {
            firstNameEdit.setText(editedContact.getFirstName());
            firstNameEdit.setEnabled(false);
            lastNameEdit.setText(editedContact.getLastName());
            lastNameEdit.setEnabled(false);
            emailEdit.setText(editedContact.getEmail());
        }

        return new AlertDialog.Builder(getActivity())
                .setView(dialogView)
                .setTitle(editedContact != null ? R.string.edit_contact : R.string.new_contact)
                .create();
    }

    private void saveContact(Contact editedContact) {
        Contact contactToSave;
        if (editedContact != null) {
            editedContact.setEmail(emailEdit.getText().toString());
            contactToSave = editedContact;
        } else {
            contactToSave = new Contact(
                    firstNameEdit.getText().toString(),
                    lastNameEdit.getText().toString(),
                    emailEdit.getText().toString()
            );
        }
        ((ContactsActivity) getActivity()).saveContact(contactToSave, getArguments().getInt(POSITION));
    }

    /**
     * Validates the user input when adding a new contact each time the test
     * is changed.
     *
     * @param editable The text that was changed. It is not used as you get the
     *                 text from member variables.
     */
    @Override public void afterTextChanged(Editable editable) {
        boolean firstNameValid = !firstNameEdit.getText().toString().isEmpty();
        boolean lastNameValid = !lastNameEdit.getText().toString().isEmpty();
        boolean emailValid = Patterns.EMAIL_ADDRESS.matcher(emailEdit.getText()).matches();

        updateValidationIcon(firstNameEdit, firstNameValid);
        updateValidationIcon(lastNameEdit, lastNameValid);
        updateValidationIcon(emailEdit, emailValid);

        entryValid = firstNameValid && lastNameValid && emailValid;
    }

    private void updateValidationIcon(EditText view, boolean isValid) {
        view.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                isValid ? R.drawable.ic_pass : R.drawable.ic_fail, 0);
    }

    /**
     * Override methods for the TextWatcher interface, used to validate user
     * input.
     */
    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
}
