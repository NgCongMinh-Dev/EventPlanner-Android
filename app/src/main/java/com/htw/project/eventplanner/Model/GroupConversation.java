package com.htw.project.eventplanner.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class GroupConversation implements Parcelable {

    private Long id;

    private String title;

    private List<User> participants;

    private Event event;

    public GroupConversation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    protected GroupConversation(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readLong();
        title = in.readString();
        if (in.readByte() == 0x01) {
            participants = new ArrayList<>();
            in.readList(participants, User.class.getClassLoader());
        } else {
            participants = null;
        }
        event = (Event) in.readValue(Event.class.getClassLoader());
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
        dest.writeString(title);
        if (participants == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(participants);
        }
        dest.writeValue(event);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GroupConversation> CREATOR = new Parcelable.Creator<GroupConversation>() {
        @Override
        public GroupConversation createFromParcel(Parcel in) {
            return new GroupConversation(in);
        }

        @Override
        public GroupConversation[] newArray(int size) {
            return new GroupConversation[size];
        }
    };
}
