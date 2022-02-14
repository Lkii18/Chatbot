package Rating;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmac.WatBot.R;

import java.util.ArrayList;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> id, name, rating,comment ,type,address;
    Activity activity;


    RatingAdapter(Activity activity,Context context,ArrayList id, ArrayList name, ArrayList rating, ArrayList comment,ArrayList type,ArrayList address){
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.comment = comment;
        this.type = type;
        this.address = address;
    }


    @NonNull
    @Override
    public RatingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_rating_row,parent,false);
        return new MyViewHolder(view);

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RatingAdapter.MyViewHolder holder,final int position) {

        holder.id_txt.setText(String.valueOf(id.get(position)));
        holder.name_txt.setText(String.valueOf(name.get(position)));
        holder.type_txt.setText(String.valueOf(type.get(position)));
        holder.ratingBar.setRating(Integer.parseInt(rating.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InformationActivity.class);
                intent.putExtra("name",String.valueOf(name.get(position)));
                intent.putExtra("rating",String.valueOf(rating.get(position)));
                intent.putExtra("type",String.valueOf(type.get(position)));
                intent.putExtra("comment",String.valueOf(comment.get(position)));
                intent.putExtra("address",String.valueOf(address.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id_txt, name_txt, rating_txt, type_txt;
        RatingBar ratingBar;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_txt = itemView.findViewById(R.id.id_txt);
            name_txt = itemView.findViewById(R.id.name_txt);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            type_txt = itemView.findViewById(R.id.type_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);


        }
    }
}
