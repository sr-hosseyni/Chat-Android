package ir.sircoder.sirchat.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sr_hosseini on 7/28/18.
 */
public class UserList {
    @SerializedName("userList")
    private ArrayList<User> userList;

    public ArrayList<User> getUserArrayList() {
        return userList;
    }

    public void setUserArrayList(ArrayList<User> userArrayList) {
        this.userList = userArrayList;
    }
}
