package tech.iwish.pickmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.Timer;

import tech.iwish.pickmall.Interface.ItemCategoryInterface;
import tech.iwish.pickmall.Interface.Progressbar_product_inteface;
import tech.iwish.pickmall.R;
import tech.iwish.pickmall.RetrofitModel.FlashSale_friendDeal.FlashSaleFriendDealList;
import tech.iwish.pickmall.RetrofitModel.silderCategory.SilderCategoryList;

public class HomeActivity extends AppCompatActivity {

    ViewPager viewPages;
    RecyclerView itemCateroryrecycle;
    RecyclerView flash_sale_main_recycle;
    RecyclerView friend_deal_recycleview;
    RecyclerView hotproductRecyclerView;
    int current_position = 0;
    private Timer timer;
    ItemCategoryInterface itemCategoryInterface;
    private SilderCategoryList list;
    private FlashSaleFriendDealList friend_flash_List;
    LinearLayout flash_line;
    Progressbar_product_inteface progressbar_product_inteface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPages = findViewById(R.id.viewPages);
        itemCateroryrecycle = findViewById(R.id.itemCateroryrecycle);
        flash_sale_main_recycle = findViewById(R.id.flash_sale_main_recycle);
        friend_deal_recycleview = findViewById(R.id.friend_deal_recycleview);
        hotproductRecyclerView = findViewById(R.id.hotproductRecyclerView);
        flash_line = findViewById(R.id.flash_line);

//        RecyclerView_INIT();

        itemCategoryInterface = value -> {

        };
        progressbar_product_inteface = val -> {
//            if (val.equals("PROGRESSBAR_START")) progress.setVisibility(View.VISIBLE);
//            else progress.setVisibility(View.GONE);
        };

    }
/*

    private void RecyclerView_INIT() {

        itemCateroryrecycle.setLayoutManager(new GridLayoutManager(this, 5));
//        flash sale
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        flash_sale_main_recycle.setLayoutManager(linearLayoutManager);
//        Friend deal
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        friend_deal_recycleview.setLayoutManager(linearLayoutManager1);
//        hot Product
        hotproductRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        silder_and_Category();
        flashSale_FriendDeal();
        hotProduct();
    }

    private void hotProduct() {
        Call<HotProductList> gethotProduct = HotProductInterface.SilderCategory().gethot_product("30");
        gethotProduct.enqueue(new Callback<HotProductList>() {
            @Override
            public void onResponse(Call<HotProductList> call, Response<HotProductList> response) {
                HotProductList hotProductlist = response.body();
                if (hotProductlist.getResponse().equals("TRUE")) {

                    hotproductRecyclerView.setAdapter(new HotProductAdapter(hotProductlist.getData(), progressbar_product_inteface));
                    new HotProductAdapter(hotProductlist.getData(), progressbar_product_inteface).notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<HotProductList> call, Throwable t) {

            }
        });
    }

    private void flashSale_FriendDeal() {

        Call<FlashSaleFriendDealList> getFlashSale_FriendDeal = FlashSale_FriendDealInterface.ProductFrontShare().getflashSale_friend_deal("");
        getFlashSale_FriendDeal.enqueue(new Callback<FlashSaleFriendDealList>() {
            @Override
            public void onResponse(Call<FlashSaleFriendDealList> call, Response<FlashSaleFriendDealList> response) {

                friend_flash_List = response.body();
                if (Objects.requireNonNull(friend_flash_List).getFlashsale().equals("TRUE")) {
                    flash_line.setVisibility(View.VISIBLE);
                    flash_sale_main_recycle.setAdapter(new FlashSaleAdapter(HomeActivity.this, friend_flash_List.getFlashsaledata()));
                } else {
                    flash_line.setVisibility(View.GONE);
                }

                if (Objects.requireNonNull(friend_flash_List).getFriendsdeal().equals("TRUE")) {
                    friend_deal_recycleview.setAdapter(new FriendSaleAdapter(HomeActivity.this, friend_flash_List.getFriendsdata()));
                }
            }

            @Override
            public void onFailure(Call<FlashSaleFriendDealList> call, Throwable t) {

            }
        });

    }

    private void silder_and_Category() {

        Call<SilderCategoryList> silder_category = Silder_Category_Interface.SilderCategory().getSilder_Category("a");
        silder_category.enqueue(new Callback<SilderCategoryList>() {
            @Override
            public void onResponse(Call<SilderCategoryList> call, Response<SilderCategoryList> response) {
                list = response.body();
                if (list.getResponse().equals("TRUE")) {

                    SilderAdapter silderAdapter = new SilderAdapter(HomeActivity.this, list.getSlider());
                    viewPages.setAdapter(silderAdapter);
                    createSilderauto();

                    ItemAdapter itemAdapter = new ItemAdapter(HomeActivity.this, list.getCategory(), itemCategoryInterface);
                    itemCateroryrecycle.setAdapter(itemAdapter);

                }
            }

            @Override
            public void onFailure(Call<SilderCategoryList> call, Throwable t) {

            }
        });

    }

    private void createSilderauto() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (current_position == list.getCategory().size())
                    current_position = 0;
                viewPages.setCurrentItem(current_position++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 250, 3000);


    }
*/

}

















