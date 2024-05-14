package app.fatoumata.safarytravel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

import app.fatoumata.safarytravel.R;
import app.fatoumata.safarytravel.models.SafaryModel;


public class SafaryPhotoAdapter extends ArrayAdapter<SafaryModel> {

    final Context context;


    public SafaryPhotoAdapter(@NonNull Context context, List<SafaryModel> list) {
        super(context, 0, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
         View view = convertView;
         if(view == null){
             view = LayoutInflater.from(getContext()).inflate(R.layout.photo_safary_cardview, parent,false);
         }
         SafaryModel safaryModel = getItem(position);

         if(safaryModel!=null){


             TextView usernameView =  view.findViewById(R.id.username);
             TextView countLikeView =  view.findViewById(R.id.countLike);
             TextView safaryTitleView =  view.findViewById(R.id.safaryTitle);
             ImageView photoView =  view.findViewById(R.id.photoUrl);
             countLikeView.setText(context.getResources().getQuantityString(R.plurals.count_like, safaryModel.getCountLike(),safaryModel.getCountLike()));
             safaryTitleView.setText(safaryModel.getChallengeName());
             usernameView.setText(safaryModel.getName());


             Glide.with(view).load(safaryModel.getUrl()).into(photoView);


         }



        return  view;

    }
}
