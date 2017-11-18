package com.example.android.myaddressbook;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ContactDetailDialog extends DialogFragment {

    private static final String CONTACT = "contact";
    private static final String POSITION = "position";

    private EditText nameEdit;
    private EditText emailEdit;

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

        nameEdit = dialogView.findViewById(R.id.edittext_name);
        emailEdit = dialogView.findViewById(R.id.edittext_email);

        final Contact editedContact = getArguments().getParcelable(CONTACT);

        dialogView.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (validateFields()) {
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
            nameEdit.setText(editedContact.getName());
            nameEdit.setEnabled(false);
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
                    nameEdit.getText().toString(),
                    emailEdit.getText().toString()
            );
        }
        ((ContactsActivity) getActivity()).saveContact(contactToSave, getArguments().getInt(POSITION));
    }

    private boolean validateFields() {
        boolean nameValid = !nameEdit.getText().toString().isEmpty();
        boolean emailValid = Patterns.EMAIL_ADDRESS.matcher(emailEdit.getText()).matches();

        updateValidationIcon(nameEdit, nameValid);
        updateValidationIcon(emailEdit, emailValid);

        return nameValid && emailValid;
    }

    private void updateValidationIcon(EditText view, boolean isValid) {
        view.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                isValid ? R.drawable.ic_pass : R.drawable.ic_fail, 0);
    }
}
