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
import app.fatoumata.safarytravel.models.PhotoModel;


public class ChallengePhotoAdapter extends ArrayAdapter<PhotoModel> {

    public interface Listener {
        void onCountryClick(PhotoModel countryModel);
    }
    final Context context;

    private final Listener listener;
    public ChallengePhotoAdapter(@NonNull Context context, List<PhotoModel> list, Listener listener) {
        super(context, 0, list);
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
         View view = convertView;
         if(view == null){
             view = LayoutInflater.from(getContext()).inflate(R.layout.photo_challenge_cardview, parent,false);
         }
         PhotoModel photoModel = getItem(position);

         if(photoModel!=null){
             TextView countLikeView =  view.findViewById(R.id.countLike);
             ImageView photoView =  view.findViewById(R.id.photoUrl);
             countLikeView.setText(context.getString(R.string.count_like,photoModel.getCountLike()));
             Glide.with(view).load(photoModel.getUrl()).into(photoView);

             view.setOnClickListener(view1 -> {
                 listener.onCountryClick(photoModel);
             });
         }



        return  view;

    }
}
