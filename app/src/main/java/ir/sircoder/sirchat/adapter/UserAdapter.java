package ir.sircoder.sirchat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ir.sircoder.sirchat.R;
import ir.sircoder.sirchat.model.User;


/**
 * Created by sr_hosseini on 7/28/18.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private ArrayList<User> dataList;
    private UserListViewListener onClickListener;

    public UserAdapter(ArrayList<User> dataList, UserListViewListener onClickListener) {
        this.dataList = dataList;
        this.onClickListener = onClickListener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_list_content, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {
        holder.txtUserName.setText(dataList.get(position).getName());
        holder.txtUserID.setText(dataList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClick(view, dataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView txtUserID;
        TextView txtUserName;

        UserViewHolder(View itemView) {
            super(itemView);
            txtUserName = (TextView) itemView.findViewById(R.id.txt_user_name);
            txtUserID = (TextView) itemView.findViewById(R.id.txt_user_id);
        }
    }

    public interface UserListViewListener {
        void onClick(View view, User user);
    }
}
