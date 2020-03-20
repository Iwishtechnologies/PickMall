package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.adapter.FriendSaleAdapter;
import tech.iwish.pickmall.adapter.FriendSaleAllProductAdapter;
import tech.iwish.pickmall.connection.JsonHelper;
import tech.iwish.pickmall.fragment.FriendDealRuleFragment;
import tech.iwish.pickmall.fragment.FriendOneRsFragment;
import tech.iwish.pickmall.other.FriendSale;
import tech.iwish.pickmall.other.FriendSaleList;

public class FriendsDealsAllActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_deals_all);

        FriendOneRsFragment friendOneRsFragment = new FriendOneRsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFriendDeal, friendOneRsFragment).commit();
    }


    public boolean friend_bottom_click(View view) {
        Fragment fragment = null;
        String id = view.getTag().toString();
        switch (id) {
            case "one_rs_win":
                fragment = new FriendOneRsFragment();
                break;
            case "how_to_play":
                fragment = new FriendDealRuleFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFriendDeal, fragment).commit();
            return true;
        }
        return false ;
    }
}



















