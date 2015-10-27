package wszolek.lauren.instagramphotoviewer;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    // what data do we need from the activity?
    // inputs: context and data source
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    //use the template to display each photo

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get data item for this position
        InstagramPhoto photo = getItem(position);
        // check if we are using a recycled view, if not, need to inflate
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        // lookup views for populating in data (image, caption)
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivProfilePicture = (ImageView) convertView.findViewById(R.id.ivProfilePicture);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvCreatedAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);
        // add clock icon for time posted
        ImageView ivClockIcon = (ImageView) convertView.findViewById(R.id.ivClockIcon);
        ivClockIcon.setColorFilter(R.color.pale_grey);
        ivClockIcon.setImageResource(R.drawable.ic_action_clock);
        //insert the model data into each of the view items
        tvCreatedAt.setText(getRelativeTime(photo.createdAt));
        tvCaption.setText(photo.caption);
        //clear out the image view (if the view is recycled)
        ivPhoto.setImageResource(0);
        tvUsername.setText(photo.username);
        //insert the image using picasso
        Picasso.with(getContext())
                .load(photo.imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(ivPhoto);
        // insert profile photo with picasso and round it
        Transformation transformation = new RoundedTransformationBuilder()
                                                .borderColor(R.color.pale_grey)
                                                .borderWidthDp(1)
                                                .cornerRadiusDp(30)
                                                .oval(false)
                                                .build();
        ivProfilePicture.setImageResource(0);
        Picasso.with(getContext())
                .load(photo.userProfilePictureUrl)
                .fit()
                .transform(transformation)
                .into(ivProfilePicture);

        //return the created item as a view
        return convertView;
    }

    private String getRelativeTime(Long timeSince) {
        return DateUtils.getRelativeTimeSpanString(timeSince * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }
}
