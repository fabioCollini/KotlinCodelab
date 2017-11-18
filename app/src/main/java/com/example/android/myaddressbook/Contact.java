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

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Contact implements Parcelable {

    private @NonNull String name;
    private @NonNull String email;

    public Contact(@NonNull String name, @NonNull String email) {
        this.name = name;
        this.email = email;
    }

    protected Contact(Parcel in) {
        this(in.readString(), in.readString());
    }

    @NonNull public String getName() {
        return name;
    }

    @NonNull public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
    }

    @Override public int describeContents() {
        return 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (!name.equals(contact.name)) return false;
        return email.equals(contact.email);
    }

    @Override public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
