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

public class commentAdapter extends RecyclerView.Adapter<commentAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String>  name,comment;
    private ArrayList<Float> rating;


    commentAdapter(Context context, ArrayList comment, ArrayList name,ArrayList rating){
        this.context = context;
        this.name = name;
        this.rating = rating;
        this.comment = comment;
    }


    @NonNull
    @Override
    public commentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_comment_row,parent,false);
        return new MyViewHolder(view);


    }
    @Override
    public void onBindViewHolder(@NonNull commentAdapter.MyViewHolder holder,int position) {


        holder.comment.setText(String.valueOf(comment.get(position)));
        holder.name.setText(String.valueOf(name.get(position)));
        holder.ratingBar.setRating(rating.get(position));

    }

    @Override
    public int getItemCount() {
        return name.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, comment;
        RatingBar ratingBar;
        LinearLayout comment_Layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.c_comment);
            ratingBar = itemView.findViewById(R.id.c_ratingBar);
            name= itemView.findViewById(R.id.c_name);
            comment_Layout = itemView.findViewById(R.id.comment_Layout);


        }
    }
}
