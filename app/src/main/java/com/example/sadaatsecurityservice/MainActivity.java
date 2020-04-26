package com.example.sadaatsecurityservice;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Invoice> invoices=new ArrayList<>();
    Spinner spinnerQty;
    Integer[] qtyArray = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    Button billMonth, periodStartDate, periodEndDate;
    DatePickerDialog picker;
    EditText toEdt, rateEdt, tAmountEdt, guardNameEdt;
    TextView issueDate;
    Spinner qtySpinner;
    Bitmap bitmap, scaledBitmap;
    Invoice invoice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.save);
        spinnerQty=findViewById(R.id.quantity);
        issueDate=findViewById(R.id.issue_date);
        invoice=new Invoice();
       // Date date=Calendar.getInstance().getTime();
        //String d=""+date.getMonth()+" "+date.getDay()+", "+date.getYear();
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        issueDate.setText(currentDate);

        ArrayAdapter<Integer> qtyAdapter=new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, qtyArray);
       spinnerQty.setAdapter(qtyAdapter);

        toEdt=findViewById(R.id.to_edt);
        guardNameEdt=findViewById(R.id.guard_name);
        rateEdt=findViewById(R.id.rate);
        tAmountEdt=findViewById(R.id.total_amount);
        qtySpinner=findViewById(R.id.quantity);


        billMonth=findViewById(R.id.bill_of_month_btn);
        periodEndDate=findViewById(R.id.period_end_date);
        periodStartDate=findViewById(R.id.period_start_date);
        issueDate=findViewById(R.id.issue_date);

        billMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        final Calendar cldr = Calendar.getInstance();
                        int day = cldr.get(Calendar.DAY_OF_MONTH);
                        int month = cldr.get(Calendar.MONTH);
                        int year = cldr.get(Calendar.YEAR);
                        // date picker dialog
                        picker = new DatePickerDialog(MainActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        billMonth.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    }
                                }, year, month, day);
                        picker.show();
            }
        });


        periodStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                periodStartDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        periodEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                periodEndDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoice.setSendTo(toEdt.getText().toString().trim());
                Log.d("save", "sendTo: "+invoice.getSendTo());
                invoice.setGuardName(guardNameEdt.getText().toString().trim());
                Log.d("save", "guardName: "+invoice.getGuardName());
                invoice.setRate(Integer.parseInt(rateEdt.getText().toString().trim()));
                Log.d("save", "rate: "+invoice.getRate());
                invoice.setTotalAmount(Double.parseDouble(tAmountEdt.getText().toString().trim()));
                Log.d("save", "tamount: "+invoice.getTotalAmount());
                invoice.setBillOfMonth(billMonth.getText().toString().trim());
                Log.d("save", "billMonth: "+billMonth.getText().toString().trim());
                invoice.setStartPeriodDate(periodStartDate.getText().toString().trim());
                Log.d("save", "startD: "+invoice.getStartPeriodDate());
                invoice.setEndPeriodDate(periodEndDate.getText().toString().trim());
                Log.d("save", "endD: "+invoice.getEndPeriodDate());
                invoice.setIssueDate(issueDate.getText().toString().trim());
                Log.d("save", "issueD: "+invoice.getIssueDate());
                saveToPDF();
            }
        });

    }
    private void saveToPDF() {
       PdfDocument pdfDocument=new PdfDocument();
        Paint paint=new Paint();
        PdfDocument.PageInfo myPageInfo1=new PdfDocument.PageInfo.Builder(400, 600, 1).create();
        PdfDocument.Page page1=pdfDocument.startPage(myPageInfo1);
        Canvas canvas=page1.getCanvas();
        bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        scaledBitmap=Bitmap.createScaledBitmap(bitmap, 150, 150, false);
        canvas.drawBitmap(scaledBitmap, 50, 50, paint);
        canvas.drawText("First line ",40, 50, paint);
        canvas.drawText(invoice.getBillOfMonth(), 40, 50, paint);
        pdfDocument.finishPage(page1);
        String directory_path = Environment.getDataDirectory().getPath() + "/mypdf/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+"test-2.pdf";
        File filePath = new File(targetPdf);
       // File file=new File(getFilesDir(), "/hello.pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(filePath));

            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
            Log.d("try", "saveToPDF: ");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "saveToPDF: "+e.getMessage());
            Toast.makeText(this, "Exception"+e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
            pdfDocument.close();
            Log.d("finally", "saveToPDF: ");
        }
    }

}
