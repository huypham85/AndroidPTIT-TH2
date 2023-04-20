package com.example.th2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th2.EditItemActivity;
import com.example.th2.R;
import com.example.th2.data.DBHelper;
import com.example.th2.model.MyModel;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    final Context mContext;
    final DBHelper dbHelper;
    private List<MyModel> dataset;

    public ListViewAdapter(List<MyModel> dataset, Context mContext, DBHelper dbHelper) {
        this.dataset = dataset;
        this.mContext = mContext;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyModel item = dataset.get(position);

        holder.name.setText(item.getBookName());
        holder.author.setText(item.getAuthor());
        holder.release_date.setText(item.getReleaseDate());
        holder.publisher.setText(item.getPublisher());
        holder.price.setText(String.valueOf(item.getPrice()));

        holder.buttonEdit.setOnClickListener(view -> {
            Intent myIntent = new Intent(mContext, EditItemActivity.class);
            myIntent.putExtra("item_data", item);
            mContext.startActivity(myIntent);
        });

        holder.buttonDelete.setOnClickListener(view -> {
            dbHelper.deleteData(item);
            dataset = dbHelper.getAllData();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView author;

        public TextView release_date;

        public TextView publisher;

        public TextView price;
        public Button buttonEdit;
        public Button buttonDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            author = itemView.findViewById(R.id.item_author);
            release_date = itemView.findViewById(R.id.item_release_date);
            publisher = itemView.findViewById(R.id.item_publisher);
            price = itemView.findViewById(R.id.item_price);
            buttonEdit = itemView.findViewById(R.id.btn_edit);
            buttonDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
