package Rating;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.vmac.WatBot.R;

public class InformationActivity extends AppCompatActivity {

    TextView name, comment, type, address;
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
      //
        name = findViewById(R.id.name_txt2);
        ratingBar = findViewById(R.id.ratingBar2);
        comment = findViewById(R.id.comment_txt2);
        type = findViewById(R.id.type_txt2);
        address = findViewById(R.id.address_txt2);

        Intent intent = getIntent();
        System.out.println("test" + intent.getStringExtra("name"));
        name.setText(intent.getStringExtra("name"));
        ratingBar.setRating(Integer.parseInt(intent.getStringExtra("rating")));
        comment.setText(intent.getStringExtra("comment"));
        type.setText(intent.getStringExtra("type"));
        address.setText(intent.getStringExtra("address"));
    }
}