package ir.sircoder.sirchat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sr_hosseini on 7/27/18.
 */
public class User implements Parcelable {
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;
//
//    @SerializedName("password")
//    private String password;
//
//    @SerializedName("messages")
//    private String[] messages;

    public User(String name, int id) {
        this.id = id;
        this.name = name;
    }

    protected User(Parcel in) {
        name = in.readString();
        id = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(id);
    }

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String[] getMessages() {
//        return messages;
//    }
//
//    public void setMessages(String[] messages) {
//        this.messages = messages;
//    }
}
