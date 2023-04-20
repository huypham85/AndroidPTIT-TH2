package com.example.th2.fragment;

import static androidx.core.content.ContextCompat.getDrawable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th2.AddItemActivity;
import com.example.th2.R;
import com.example.th2.adapter.ListViewAdapter;
import com.example.th2.data.DBHelper;
import com.example.th2.model.MyModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListviewFragment extends Fragment {

    RecyclerView recyclerView;
    ListViewAdapter adapter;
    List<MyModel> listData;
    FloatingActionButton fab;
    DBHelper dbHelper;

    public ListviewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview, container, false);
        dbHelper = new DBHelper(container.getContext());
        fab = view.findViewById(R.id.add_fab);
        fab.setOnClickListener(v -> {
            Intent myIntent = new Intent(container.getContext(), AddItemActivity.class);
            startActivity(myIntent);
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.list_view);

        listData = dbHelper.getAllData();
        for(MyModel item: listData) {
            System.out.println(item.getReleaseDate());
        }

        setAdapter(container.getContext());

        return view;
    }

    void setAdapter(Context context) {
        adapter = new ListViewAdapter(listData, context, dbHelper);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context,
                linearLayoutManager.getOrientation());
        itemDecoration.setDrawable(getDrawable(context, R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onResume() {
        System.out.println("On resume");
        listData = dbHelper.getAllData();
        System.out.print(listData.size());
        setAdapter(getContext());
        super.onResume();
    }
}