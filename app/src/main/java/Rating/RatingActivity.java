package Rating;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        RatingBar ratingBar = findViewById(R.id.rating_bar);
        Button ratingButton = findViewById(R.id.rating_submit);
        EditText etname = findViewById(R.id.name);
        EditText etcomment = findViewById(R.id.comment);

        Database myDB = new Database(RatingActivity.this);
        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Your rating is " + ratingBar.getRating() , Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                myDB.rating(intent.getStringExtra("name"),etname.getText().toString().trim(),ratingBar.getRating(),etcomment.getText().toString().trim());
                Intent intent2 = new Intent(RatingActivity.this, ViewRatingActivity.class);
                startActivity(intent2);

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