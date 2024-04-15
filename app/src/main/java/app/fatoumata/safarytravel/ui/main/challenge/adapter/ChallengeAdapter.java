package app.fatoumata.safarytravel.ui.main.challenge.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

import app.fatoumata.safarytravel.R;


public class ChallengeAdapter extends ArrayAdapter<ChallengeAdapter.ChallengeModel> {


    public static class ChallengeModel{

        private String id;
        private  String name;
        private Date createAt;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getCreateAt() {
            return createAt;
        }

        public void setCreateAt(Date createAt) {
            this.createAt = createAt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public interface Listener {
        void onChallengeClick(ChallengeModel challengeModel);
    }

    private final Listener listener;
    private  Context context;
    public ChallengeAdapter(@NonNull Context context, List<ChallengeModel> list, Listener listener) {
        super(context, 0, list);
        this.context =  context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
         View view = convertView;
         if(view == null){
             view = LayoutInflater.from(getContext()).inflate(R.layout.challenge_item_view, parent,false);
         }
        ChallengeModel challengeModel = getItem(position);

         if(challengeModel!=null){
             TextView challengeName =  view.findViewById(R.id.challenge_name);
             challengeName.setText(challengeModel.getName());
             Date now = challengeModel.getCreateAt();
             String str = (String) DateUtils.getRelativeDateTimeString(
                     context,
                     now.getTime(),
                     DateUtils.MINUTE_IN_MILLIS,
                     DateUtils.WEEK_IN_MILLIS,
                     0); // Eventual flags
             TextView challengeDate =  view.findViewById(R.id.challenge_date);
             challengeDate.setText(str);
             view.setOnClickListener(view1 -> {
                 listener.onChallengeClick(challengeModel);
             });
         }


        return  view;

    }
}
