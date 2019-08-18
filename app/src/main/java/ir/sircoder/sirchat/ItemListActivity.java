package ir.sircoder.sirchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import org.greenrobot.eventbus.EventBus;

import ir.sircoder.sirchat.adapter.UserAdapter;
import ir.sircoder.sirchat.model.User;
import ir.sircoder.sirchat.model.UserList;
import ir.sircoder.sirchat.network.GetUserDataService;
import ir.sircoder.sirchat.network.RetrofitInstance;
import ir.sircoder.sirchat.service.SirChatService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private UserAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.user_list);
        assert recyclerView != null;
        recyclerView.setAdapter(adapter);

        if (findViewById(R.id.user_chat_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        
        /*Create handle for the RetrofitInstance interface*/
        GetUserDataService service = RetrofitInstance.getRetrofitInstance().create(GetUserDataService.class);

        /*Call the method with parameter in the interface to get the user data*/
        Call<UserList> call = service.getUserData();

        /*Log the URL called*/
        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                generateUserList(response.body().getUserArrayList());
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Log.wtf("ERROR ", t.getMessage());
                Toast.makeText(ItemListActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateUserList(ArrayList<User> userDataList) {
        adapter = new UserAdapter(userDataList, new UserAdapter.UserListViewListener() {
            @Override
            public void onClick(View view, User user) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(ItemDetailFragment.ARG_USER, user);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.user_chat_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_USER, user);

                    context.startActivity(intent);
                }
            }
        });

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ItemListActivity.this);

        recyclerView.setAdapter(adapter);
//        userDataList.add(new User("test", 3));
//        adapter.notifyItemInserted(userDataList.size() - 1);

//        recyclerView.setLayoutManager(layoutManager);

    }
}
