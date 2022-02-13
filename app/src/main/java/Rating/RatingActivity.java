package Rating;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vmac.WatBot.R;

public class RatingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String[] types = { "Restaurant", "Attraction" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        RatingBar ratingBar = findViewById(R.id.rating_bar);
        Button ratingButton = findViewById(R.id.rating_submit);
        EditText etname = findViewById(R.id.name);
        EditText etcomment = findViewById(R.id.comment);
        EditText etaddress = findViewById(R.id.address);

        Database myDB = new Database(RatingActivity.this);
        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Your rating is " + ratingBar.getRating() , Toast.LENGTH_SHORT).show();
                myDB.rating(etname.getText().toString().trim(),etcomment.getText().toString().trim(),spin.getSelectedItem().toString().trim(),ratingBar.getRating(),etaddress.getText().toString().trim());

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}