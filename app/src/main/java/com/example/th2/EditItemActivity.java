package com.example.th2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.th2.data.DBHelper;
import com.example.th2.model.MyModel;

import java.util.Calendar;

public class EditItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    MyModel itemData;
    DBHelper dbHelper;
    Button buttonUpdate;

    EditText edtEditName,
            edtEditAuthor,
            editUpdateTextSelectTime,
            edtUpdatePrice;
    Button btnConfirm;
    Calendar date;
    String pickedPublisher;

    void initView() {
        dbHelper = new DBHelper(this);
        itemData = (MyModel) getIntent().getSerializableExtra("item_data");
        edtEditName = findViewById(R.id.edt_update_name);
        edtEditAuthor = findViewById(R.id.edt_update_author);
        editUpdateTextSelectTime = findViewById(R.id.edt_update_time);
        edtUpdatePrice = findViewById(R.id.edt_update_price);
        buttonUpdate = findViewById(R.id.btn_update);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        initView();

        Spinner spinner = findViewById(R.id.update_ps);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.publisher_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        edtEditName.setText(itemData.getBookName());
        edtEditAuthor.setText(itemData.getAuthor());
        editUpdateTextSelectTime.setText(itemData.getReleaseDate());
        edtUpdatePrice.setText(String.valueOf(itemData.getPrice()));

        buttonUpdate.setOnClickListener(v -> {
            if (isValid()) {
                dbHelper.updateData(new MyModel(
                        itemData.getId(),
                        edtEditName.getText().toString(),
                        edtEditAuthor.getText().toString(),
                        editUpdateTextSelectTime.getText().toString(),
                        pickedPublisher,
                        Double.parseDouble(edtUpdatePrice.getText().toString())
                ));
                finish();
            } else {
                Toast.makeText(this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        editUpdateTextSelectTime.setOnClickListener(v -> {
            showDateTimePicker();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spinnerResult = String.valueOf(parent.getItemAtPosition(position));
        pickedPublisher = spinnerResult;
        System.out.println(spinnerResult);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            date.set(year, month, dayOfMonth);
            new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                date.set(Calendar.MINUTE, minute);
                System.out.println("The choosen one " +
                        date.get(Calendar.DAY_OF_MONTH) + "/"
                        + date.get(Calendar.MONTH) + "/"
                        + date.get(Calendar.YEAR)
                );
                String pickedDate = date.get(Calendar.DAY_OF_MONTH) + "/"
                        + date.get(Calendar.MONTH) + "/"
                        + date.get(Calendar.YEAR);

                editUpdateTextSelectTime.setText(pickedDate);
            },
                    currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
        },
                currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    boolean isValid() {
        if (!edtEditName.getText().toString().isEmpty() &&
                !edtEditAuthor.getText().toString().isEmpty() &&
                !edtUpdatePrice.getText().toString().isEmpty()) {
            return Double.parseDouble(edtUpdatePrice.getText().toString()) > 0;
        }
        return false;
    }
}