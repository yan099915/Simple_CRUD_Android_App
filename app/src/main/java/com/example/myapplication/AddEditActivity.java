package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditActivity extends AppCompatActivity {

    private EditText editNama, editNim, editJurusan;
    private Button btnSimpan, btnHapus;

    private String mode;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        editNama = findViewById(R.id.editNama);
        editNim = findViewById(R.id.editNim);
        editJurusan = findViewById(R.id.editJurusan);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnHapus = findViewById(R.id.btnHapus);

        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");

        if (mode.equals("edit")) {
            id = intent.getIntExtra("id", -1);
            editNama.setText(intent.getStringExtra("nama"));
            editNim.setText(intent.getStringExtra("nim"));
            editJurusan.setText(intent.getStringExtra("jurusan"));
            btnHapus.setVisibility(View.VISIBLE);
        } else {
            btnHapus.setVisibility(View.GONE);
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = editNama.getText().toString();
                String nim = editNim.getText().toString();
                String jurusan = editJurusan.getText().toString();

                if (nama.isEmpty() || nim.isEmpty() || jurusan.isEmpty()) {
                    // Bisa kasih Toast atau validasi lain
                    return;
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("mode", mode);
                resultIntent.putExtra("id", id);
                resultIntent.putExtra("nama", nama);
                resultIntent.putExtra("nim", nim);
                resultIntent.putExtra("jurusan", jurusan);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("mode", mode);
                resultIntent.putExtra("id", id);
                resultIntent.putExtra("delete", true);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
