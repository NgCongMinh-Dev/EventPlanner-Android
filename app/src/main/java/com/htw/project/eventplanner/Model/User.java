package com.htw.project.eventplanner.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.Arrays;

public class User implements Parcelable {

    public enum Role {
        USER, ADMIN;
    }

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private Role role = Role.USER;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public String getFullName() {
        return TextUtils.join(" ", Arrays.asList(getNameValue(firstName), getNameValue(lastName)));
    }

    private String getNameValue(String name) {
        return name == null ? "" : name;
    }

    protected User(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readLong();
        username = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        role = Role.values()[in.readInt()];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(id);
        }
        dest.writeString(username);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeInt(role.ordinal());
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
