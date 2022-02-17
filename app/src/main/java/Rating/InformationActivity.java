package Rating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vmac.WatBot.MainActivity;
import com.example.vmac.WatBot.R;

import java.util.ArrayList;

public class InformationActivity extends AppCompatActivity {

    TextView name, type, address;
    RatingBar ratingBar;
    Button button;
    Database db;
    RecyclerView recyclerView;
    ArrayList<String> name2,comment, rating;
    commentAdapter commentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
      //
        name = findViewById(R.id.name_txt2);
        ratingBar = findViewById(R.id.ratingBar2);
        type = findViewById(R.id.type_txt2);
        address = findViewById(R.id.address_txt2);
        button = findViewById(R.id.add_button);
        recyclerView = findViewById(R.id.comment_txt2);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        ratingBar.setRating(Integer.parseInt(intent.getStringExtra("rating")));
        type.setText(intent.getStringExtra("type"));
        address.setText(intent.getStringExtra("address"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformationActivity.this, RatingActivity.class);
                intent.putExtra("name",name.getText().toString());
                startActivity(intent);
            }
        });
        db = new Database(InformationActivity.this);

        comment = new ArrayList<>();
        name2 = new ArrayList<>();
        rating = new ArrayList<>();
        storeDataInArray();
        recyclerView.setLayoutManager(new LinearLayoutManager(InformationActivity.this));
        commentAdapter = new commentAdapter(InformationActivity.this,comment,name2,rating);
        recyclerView.setAdapter(commentAdapter);

    }

    void storeDataInArray(){
        Intent intent = getIntent();
        Cursor c = db.readComment(intent.getStringExtra("name"));

        if(c.getCount()==0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else{
            while (c.moveToNext()){
                comment.add(c.getString(1));
                name2.add(c.getString(4));
                rating.add(c.getString(3));
            }
        }
    }
}