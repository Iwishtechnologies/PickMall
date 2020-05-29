package tech.iwish.pickmall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import tech.iwish.pickmall.R;
import tech.iwish.pickmall.fragment.FlashSaleCurrentSaleFragment;
import tech.iwish.pickmall.fragment.FlashSaleNextSaleFragment;

public class FlashSaleProductactivity extends AppCompatActivity {

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_sale_productactivity);

        getSupportActionBar().hide();

        back = (ImageView)findViewById(R.id.back);

        FlashSaleCurrentSaleFragment flashSaleCurrentSaleFragment = new FlashSaleCurrentSaleFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flash_sale_frame_layout, flashSaleCurrentSaleFragment).commit();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    public boolean sale_layout(View view) {

        String id = view.getTag().toString();
        Fragment fragment = null;

        switch (id) {
            case "current_on_sale":
                fragment = new FlashSaleCurrentSaleFragment();
                break;
            case "next_sale":
                fragment = new FlashSaleNextSaleFragment();
                break;
            case "end_sale":
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flash_sale_frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}


















