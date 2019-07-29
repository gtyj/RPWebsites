package c346.rp.edu.rpwebsites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Spinner spn1;
    Spinner spn2;
    Button btnGo;
    ArrayList<String> alURLs;
    ArrayAdapter<String> aaURLs;
    String[] strURL;
    SharedPreferences prefs;
    SharedPreferences.Editor prefEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spn1 = findViewById(R.id.spinner1);
        spn2 = findViewById(R.id.spinner2);
        btnGo = findViewById(R.id.buttonGo);
        alURLs = new ArrayList<>();
        aaURLs = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alURLs);
        prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        prefEdit = prefs.edit();

        spn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                alURLs.clear();

                switch (position) {
                    case 0:
                        strURL = getResources().getStringArray(R.array.rpURL);
                        break;
                    case 1:
                        strURL = getResources().getStringArray(R.array.soiURL);
                        break;
                }
                alURLs.addAll(Arrays.asList(strURL));
                spn2.setAdapter(aaURLs);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prefEdit.putInt("spn1", spn1.getSelectedItemPosition());
                prefEdit.putInt("spn2", spn2.getSelectedItemPosition());

                prefEdit.commit();

                String[][] sites = {{"https://www.rp.edu.sg/", "https://www.rp.edu.sg/student-life"}, {"https://www.rp.edu.sg/soi/full-time-diplomas/details/r47", "https://www.rp.edu.sg/soi/full-time-diplomas/details/r12"}};
                String url = sites[spn1.getSelectedItemPosition()][spn2.getSelectedItemPosition()];

                Intent intent = new Intent(MainActivity.this, MyPage.class);
                intent.putExtra("URL", url);
                startActivity(intent);
            }
        });
    }
    public void onResume() {
        super.onResume();

        int spinner1 = prefs.getInt("spn1", 0);
        int spinner2 = prefs.getInt("spn2", 0);

        spn1.setSelection(spinner1);
        spn2.setSelection(spinner2);
    }
}
