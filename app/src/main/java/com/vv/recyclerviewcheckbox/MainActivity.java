package com.vv.recyclerviewcheckbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private View deleteButton;
    private View cancelButton;
    private CheckBox selectAllCheckbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.rv_list);

    }
}
