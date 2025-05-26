package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listViewMahasiswa;
    private Button btnTambah;

    private ArrayList<Mahasiswa> listMahasiswa = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ArrayList<String> namaList = new ArrayList<>();

    private final int REQUEST_CODE_ADD = 1;
    private final int REQUEST_CODE_EDIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewMahasiswa = findViewById(R.id.listViewMahasiswa);
        btnTambah = findViewById(R.id.btnTambah);

        // Contoh data awal
        listMahasiswa.add(new Mahasiswa(1, "Andi", "123", "Teknik Informatika"));
        listMahasiswa.add(new Mahasiswa(2, "Budi", "456", "Sistem Informasi"));
        refreshNamaList();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, namaList);
        listViewMahasiswa.setAdapter(adapter);

        listViewMahasiswa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Mahasiswa mhs = listMahasiswa.get(position);
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra("mode", "edit");
                intent.putExtra("id", mhs.getId());
                intent.putExtra("nama", mhs.getNama());
                intent.putExtra("nim", mhs.getNim());
                intent.putExtra("jurusan", mhs.getJurusan());
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra("mode", "add");
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });
    }

    private void refreshNamaList() {
        namaList.clear();
        for (Mahasiswa mhs : listMahasiswa) {
            namaList.add(mhs.getNama());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String mode = data.getStringExtra("mode");
            int id = data.getIntExtra("id", -1);
            String nama = data.getStringExtra("nama");
            String nim = data.getStringExtra("nim");
            String jurusan = data.getStringExtra("jurusan");

            if (mode.equals("add")) {
                int newId = listMahasiswa.size() > 0 ? listMahasiswa.get(listMahasiswa.size() - 1).getId() + 1 : 1;
                listMahasiswa.add(new Mahasiswa(newId, nama, nim, jurusan));
            } else if (mode.equals("edit")) {
                // Cari mahasiswa dengan id dan update
                for (int i = 0; i < listMahasiswa.size(); i++) {
                    if (listMahasiswa.get(i).getId() == id) {
                        if (data.getBooleanExtra("delete", false)) {
                            listMahasiswa.remove(i);
                        } else {
                            listMahasiswa.get(i).setNama(nama);
                            listMahasiswa.get(i).setNim(nim);
                            listMahasiswa.get(i).setJurusan(jurusan);
                        }
                        break;
                    }
                }
            }
            refreshNamaList();
            adapter.notifyDataSetChanged();
        }
    }
}
