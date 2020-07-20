package tech.iwish.pickmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import tech.iwish.pickmall.R;

public class TransactionFailed extends AppCompatActivity {
    private Button failed;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.transaction_failed);
        failed= findViewById(R.id.failed);

        failed.setOnClickListener(view -> {
            startActivity(new Intent(TransactionFailed.this,MainActivity.class));
        });
    }

}
