package dangthuongngo.com.baitaprenlyenmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;

import java.util.ArrayList;

import dangthuongngo.com.adapter.NhanVienAdapter;
import dangthuongngo.com.model.NhanVien;

public class MainActivity extends AppCompatActivity {
    ListView lvNhanVien;
    NhanVienAdapter nhanVienAdapter;
    NhanVien selectedNV = null;
    ArrayList<NhanVien> dsNguon = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvent();
    }

    private void addEvent() {
        lvNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedNV = nhanVienAdapter.getItem(position);
            }
        });
        lvNhanVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedNV = nhanVienAdapter.getItem(position);
                return false;
            }
        });
    }

    private void addControls() {
        lvNhanVien = findViewById(R.id.lvNhanVien);
        nhanVienAdapter = new NhanVienAdapter(MainActivity.this, R.layout.item);
        lvNhanVien.setAdapter(nhanVienAdapter);

        registerForContextMenu(lvNhanVien);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnuEdit) {
            hienThiManHinhEdit();
        } else if (item.getItemId() == R.id.mnuRemove)
            xuLiXoa();
        return super.onContextItemSelected(item);
    }

    private void hienThiManHinhEdit() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.detail_layout);

        final EditText edtMa = dialog.findViewById(R.id.edtMa);
        final EditText edtTen = dialog.findViewById(R.id.txtTen);
        RadioButton radNam = dialog.findViewById(R.id.radNam);
        final RadioButton radNu = dialog.findViewById(R.id.radNu);
        Button btnLuu = dialog.findViewById(R.id.btnLuu);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNV.setMa(edtMa.getText().toString());
                selectedNV.setTen(edtTen.getText().toString());
                selectedNV.setGioiTinh(radNu.isChecked());
                nhanVienAdapter.notifyDataSetChanged();
                dialog.dismiss();

                dsNguon.clear();
                for (int i = 0; i < nhanVienAdapter.getCount(); i++) {
                    dsNguon.add(nhanVienAdapter.getItem(i));
                }
            }
        });
        edtMa.setText(selectedNV.getMa());
        edtTen.setText(selectedNV.getTen());
        if (selectedNV.isGioiTinh())
            radNam.setChecked(true);
        else
            radNu.setChecked(true);
        dialog.show();
    }

    private void xuLiXoa() {
        nhanVienAdapter.remove(selectedNV);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        MenuItem mnuSearch = menu.findItem(R.id.mnuSearch);
        SearchView searchView = (SearchView) mnuSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    nhanVienAdapter.clear();
                    nhanVienAdapter.addAll(dsNguon);
                }
                else {
                    ArrayList<NhanVien> dsTim = new ArrayList<NhanVien>();
                    for (NhanVien nv : dsNguon) {
                        if (nv.getMa().contains(newText) || nv.getTen().contains(newText))
                            dsTim.add(nv);
                    }
                    nhanVienAdapter.clear();
                    nhanVienAdapter.addAll(dsTim);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuNew:
                hienTHiManHinhNhap();
                break;
            case R.id.mnuHelp:
                break;
            case R.id.mnuAbout:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hienTHiManHinhNhap() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.detail_layout);

        final EditText edtMa = dialog.findViewById(R.id.edtMa);
        final EditText edtTen = dialog.findViewById(R.id.txtTen);
        RadioButton radNam = dialog.findViewById(R.id.radNam);
        final RadioButton radNu = dialog.findViewById(R.id.radNu);
        Button btnLuu = dialog.findViewById(R.id.btnLuu);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NhanVien nv = new NhanVien();
                nv.setMa(edtMa.getText().toString());
                nv.setTen(edtTen.getText().toString());
                nv.setGioiTinh(radNu.isChecked());
                nhanVienAdapter.add(nv);
                dialog.dismiss();

                dsNguon.clear();
                for (int i = 0; i < nhanVienAdapter.getCount(); i++) {
                    dsNguon.add(nhanVienAdapter.getItem(i));
                }
            }
        });
        dialog.show();
    }
}
