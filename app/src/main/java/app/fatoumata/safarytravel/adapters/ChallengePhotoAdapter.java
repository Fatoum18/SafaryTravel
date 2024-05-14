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
        void onThumbClick(PhotoModel countryModel);
    }
    final Context context;

    private final Listener listener;
    private final String currentUserId;
    public ChallengePhotoAdapter(@NonNull Context context, List<PhotoModel> list, Listener listener, String currentUserId ) {
        super(context, 0, list);
        this.listener = listener;
        this.context = context;
        this.currentUserId = currentUserId;
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

             boolean isMe = photoModel.getUserId() != null && photoModel.getUserId().equals(currentUserId);
             TextView usernameView =  view.findViewById(R.id.username);
             TextView countLikeView =  view.findViewById(R.id.countLike);
             ImageView imageViewLike =  view.findViewById(R.id.buttonLike);
             ImageView photoView =  view.findViewById(R.id.photoUrl);
             countLikeView.setText(context.getResources().getQuantityString(R.plurals.count_like,photoModel.getCountLike(),photoModel.getCountLike()));
             usernameView.setText(photoModel.getName());
             imageViewLike.setVisibility(isMe ? View.GONE :  View.VISIBLE);
             Glide.with(view).load(photoModel.getUrl()).into(photoView);

             imageViewLike.setOnClickListener(view1 -> {
                 listener.onThumbClick(photoModel);
             });
         }



        return  view;

    }
}
