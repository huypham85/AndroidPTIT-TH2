package com.example.th2.fragment;

import static androidx.core.content.ContextCompat.getDrawable;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th2.R;
import com.example.th2.adapter.ListViewAdapter;
import com.example.th2.data.DBHelper;
import com.example.th2.model.MyModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    ListViewAdapter adapter;
    List<MyModel> listData;
    DBHelper dbHelper;
    ImageButton icSearch, icFilterUp, icFilterDown;
    EditText editTextSearch;
    EditText priceFrom;
    EditText priceTo;
    TextView itemCount;
    Spinner spinner;

    public SearchFragment() {
    }

    void initView(View view) {
        dbHelper = new DBHelper(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.list_view);
        recyclerView = view.findViewById(R.id.list_view_search);
        icSearch = view.findViewById(R.id.ic_search);
        editTextSearch = view.findViewById(R.id.edt_search);
        icFilterUp = view.findViewById(R.id.ic_filter);
        icFilterDown = view.findViewById(R.id.ic_filter_down);
        priceFrom = view.findViewById(R.id.price_from);
        priceTo = view.findViewById(R.id.price_to);
        itemCount = view.findViewById(R.id.itemCount);
        spinner = view.findViewById(R.id.spinner);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        initView(view);
        listData = dbHelper.getAllData();
        setAdapter(container.getContext());

        icSearch.setOnClickListener(v -> {
            searchByPrice();
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                List<MyModel> result = new ArrayList<>();
                if (selectedItem.equals("Tất cả")) {
                    result = dbHelper.getAllData();
                } else {
                    listData = dbHelper.getAllData();
                    for (MyModel item: listData) {
                        if (item.getPublisher().equals(selectedItem)) {
                            result.add(item);
                        }
                    }
                }

                listData = result;
                itemCount.setText(String.valueOf(listData.size()));
                setAdapter(getContext());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchByKeyword();
            }
        });

        icFilterUp.setOnClickListener(view1 -> {
            sortAsc();
        });

        icFilterDown.setOnClickListener(view1 -> {
            sortDsc();
        });

        return view;
    }

    void searchByKeyword() {
        List<MyModel> result = new ArrayList<>();
        String keyword = editTextSearch.getText().toString();
        for (MyModel element : dbHelper.getAllData()) {
            if (element.getBookName().trim().toLowerCase().contains(keyword.trim().toLowerCase()) ||
                    element.getAuthor().trim().toLowerCase().contains(keyword.trim().toLowerCase())
            ) {
                result.add(element);
            }
        }
        listData = result;
        itemCount.setText(String.valueOf(listData.size()));
        setAdapter(getContext());
    }

    void searchByPrice() {
        if (priceFrom.getText().toString().isEmpty() || priceTo.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            List<MyModel> result = new ArrayList<>();
            for (MyModel element : dbHelper.getAllData()) {
                if (element.getPrice() >= Double.parseDouble(priceFrom.getText().toString())
                        && element.getPrice() <= Double.parseDouble(priceTo.getText().toString())) {
                    result.add(element);
                }
            }
            listData = result;
            itemCount.setText(String.valueOf(listData.size()));
            setAdapter(getContext());
        }
    }

    void sortAsc() {
        List<MyModel> result = dbHelper.getAllData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            result.sort(Comparator.comparing(MyModel::getPrice));
            Comparator<MyModel> byReleaseDate = new Comparator<MyModel>() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                public int compare(MyModel m1, MyModel m2) {
                    try {
                        Date d1 = dateFormat.parse(m1.getReleaseDate());
                        Date d2 = dateFormat.parse(m2.getReleaseDate());
                        return d1.compareTo(d2);
                    } catch (ParseException e) {
                        // Handle parsing error
                        e.printStackTrace();
                        return 0;
                    }
                }
            };
            result.sort(byReleaseDate);
        }
        listData = result;
        itemCount.setText(String.valueOf(listData.size()));
        setAdapter(getContext());
    }

    void sortDsc() {
        List<MyModel> result = dbHelper.getAllData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            result.sort(Comparator.comparing(MyModel::getPrice).reversed());
            Comparator<MyModel> byReleaseDate = new Comparator<MyModel>() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                public int compare(MyModel m1, MyModel m2) {
                    try {
                        Date d1 = dateFormat.parse(m1.getReleaseDate());
                        Date d2 = dateFormat.parse(m2.getReleaseDate());
                        return d2.compareTo(d1);
                    } catch (ParseException e) {
                        // Handle parsing error
                        e.printStackTrace();
                        return 0;
                    }
                }
            };
            result.sort(byReleaseDate);
        }
        listData = result;
        itemCount.setText(String.valueOf(listData.size()));
        setAdapter(getContext());
    }

    void setAdapter(Context context) {
        adapter = new ListViewAdapter(listData, context, dbHelper);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onResume() {
        System.out.println("On resume");
        listData = dbHelper.getAllData();
        itemCount.setText(String.valueOf(listData.size()));
        System.out.print(listData.size());
        setAdapter(getContext());
        super.onResume();
    }
}