package Rating;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.vmac.WatBot.MainActivity;
import com.example.vmac.WatBot.R;

import java.util.ArrayList;

public class ViewRatingActivity extends AppCompatActivity {

    Database db;
    ArrayList<String> id, name, rating, comment, type, address;
    RatingAdapter ratingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rating);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        db = new Database(ViewRatingActivity.this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        rating = new ArrayList<>();
        comment = new ArrayList<>();
        type = new ArrayList<>();
        address = new ArrayList<>();

        storeDataInArray();
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewRatingActivity.this));
        ratingAdapter = new RatingAdapter(ViewRatingActivity.this,this,id,name,rating,comment,type,address);
        recyclerView.setAdapter(ratingAdapter);

    }

    void storeDataInArray(){
        Cursor cursor = db.readAllData();
        if(cursor.getCount()==0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                rating.add(cursor.getString(2));
                comment.add(cursor.getString(3));
                type.add(cursor.getString(4));
                address.add(cursor.getString(5));
            }
        }
    }
}