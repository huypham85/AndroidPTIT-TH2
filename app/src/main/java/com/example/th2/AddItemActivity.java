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

public class AddItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edtAddName, edtAddAuthor, editTextSelectTime, edtPrice;
    Button btnConfirm;
    DBHelper dbHelper;
    Calendar date;

    String pickedPublisher;

    void initView() {
        dbHelper = new DBHelper(this);
        btnConfirm = findViewById(R.id.btn_confirm);
        edtAddName = findViewById(R.id.edt_add_name);
        edtAddAuthor = findViewById(R.id.edt_author);
        editTextSelectTime = findViewById(R.id.edt_select_time);
        edtPrice = findViewById(R.id.edt_price);

        Spinner spinner = findViewById(R.id.publisher_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.publisher_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    boolean isValid() {
        if (!edtAddName.getText().toString().isEmpty() &&
                !edtAddAuthor.getText().toString().isEmpty() &&
                !edtPrice.getText().toString().isEmpty()) {

            return Double.parseDouble(edtPrice.getText().toString()) > 0;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        initView();

        btnConfirm.setOnClickListener(v -> {
            if (isValid()) {
                MyModel newItem = new MyModel();
                newItem.setId(0);
                newItem.setBookName(edtAddName.getText().toString());
                newItem.setAuthor(edtAddAuthor.getText().toString());
                newItem.setReleaseDate(editTextSelectTime.getText().toString());
                newItem.setPublisher(pickedPublisher);
                newItem.setPrice(Double.parseDouble(edtPrice.getText().toString()));
                dbHelper.addData(newItem);
                finish();
            } else {
                Toast.makeText(this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        editTextSelectTime.setOnClickListener(v -> {
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

                editTextSelectTime.setText(pickedDate);
            },
                    currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
        },
                currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }
}