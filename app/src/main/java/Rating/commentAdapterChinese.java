package Rating;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmac.WatBot.R;

import java.util.ArrayList;

public class commentAdapterChinese extends RecyclerView.Adapter<commentAdapterChinese.MyViewHolder> {

    private Context context;
    private ArrayList<String>  name,comment,date;
    private ArrayList<Float> rating;


    commentAdapterChinese(Context context, ArrayList comment, ArrayList name, ArrayList rating, ArrayList date){
        this.context = context;
        this.name = name;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_comment_row_c,parent,false);
        return new MyViewHolder(view);


    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {


        holder.comment.setText(String.valueOf(comment.get(position)));
        holder.name.setText(String.valueOf(name.get(position)));
        holder.ratingBar.setRating(rating.get(position));
        holder.date.setText(date.get(position));

    }

    @Override
    public int getItemCount() {
        return name.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, comment, date;
        RatingBar ratingBar;
        LinearLayout comment_Layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.c_comment);
            ratingBar = itemView.findViewById(R.id.c_ratingBar);
            name= itemView.findViewById(R.id.c_name);
            comment_Layout = itemView.findViewById(R.id.comment_Layout);
            date = itemView.findViewById(R.id.c_date);


        }
    }
}
