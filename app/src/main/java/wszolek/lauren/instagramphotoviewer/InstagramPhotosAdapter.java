package wszolek.lauren.instagramphotoviewer;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
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

        //profile photo
        ImageView ivProfilePicture = (ImageView) convertView.findViewById(R.id.ivProfilePicture);
        ivProfilePicture.setImageResource(0);
        // create transform to round the photo
        Transformation transformation = new RoundedTransformationBuilder()
                                                .borderColor(R.color.pale_grey)
                                                .borderWidthDp(1)
                                                .cornerRadiusDp(30)
                                                .oval(false)
                                                .build();
        // insert profile photo with transform
        Picasso.with(getContext())
                .load(photo.userProfilePictureUrl)
                .fit()
                .transform(transformation)
                .into(ivProfilePicture);

        // username
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        tvUsername.setText(photo.username);

        // clock icon and set the color
        ImageView ivClockIcon = (ImageView) convertView.findViewById(R.id.ivClockIcon);
        ivClockIcon.setImageResource(0);
        ivClockIcon.setColorFilter(ContextCompat.getColor(getContext(), R.color.pale_grey));
        ivClockIcon.setImageResource(R.drawable.ic_action_clock);

        // time posted
        TextView tvCreatedAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);
        tvCreatedAt.setText(getRelativeTime(photo.createdAt));

        // like count icon & set color
        ImageView ivLikeIcon = (ImageView) convertView.findViewById(R.id.ivLikeIcon);
        ivLikeIcon.setImageResource(0);
        ivLikeIcon.setColorFilter(ContextCompat.getColor(getContext(), R.color.dark_navy));
        ivLikeIcon.setImageResource(R.drawable.ic_action_heart);

        // like count
        TextView tvLikeCount = (TextView) convertView.findViewById(R.id.tvLikeCount);
        String likeString = getLikesString(photo);
        tvLikeCount.setText(likeString);

        // photo
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        // clear out the image view (if the view is recycled for the photo)
        ivPhoto.setImageResource(0);
        //insert the image using picasso
        Picasso.with(getContext())
                .load(photo.imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(ivPhoto);

        //image caption
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        String captionText = getCaptionText(photo.username, photo.caption);
        tvCaption.setText(Html.fromHtml(captionText));

        // comment count
        TextView tvCommentCount = (TextView) convertView.findViewById(R.id.tvCommentCount);
        String commentCountText = getCommentCountText(photo.commentCount);
        tvCommentCount.setText(commentCountText);

        //last comment
        TextView tvLastComment = (TextView) convertView.findViewById(R.id.tvLastComment);
        String lastCommentText = getCaptionText(photo.lastCommentUsername, photo.lastCommentText);
        tvLastComment.setText(Html.fromHtml(lastCommentText));

        //cheating - second to last comment
        TextView tvSecondToLastComment = (TextView) convertView.findViewById(R.id.tvSecondToLastComment);
        String secondToLastCommentText = getCaptionText(photo.secondToLastCommentUsername, photo.secondToLastCommentText);
        tvSecondToLastComment.setText(Html.fromHtml(secondToLastCommentText));

        //return the created item as a view
        return convertView;
    }

    // no this is not ideal
    private String getLikesString(InstagramPhoto photo) {
        if(photo.likesCount == 1){
            return Integer.toString(photo.likesCount) + "like";
        } else {
            return Integer.toString(photo.likesCount) + " likes";
        }
    }

    private String getCaptionText(String username, String text) {
        return "<b><font color = "
                       + ContextCompat.getColor(getContext(), R.color.dark_navy)
                       + ">" + username
                       + "</b></font> "
                       + text;
    }
//        if(likeCount == 0){
//            return "";
//        }
//        return null;
//    }
    private String getCommentCountText(int commentCount){
        return "View all " + Integer.toString(commentCount) + " comments";
    }

    private String getRelativeTime(Long timeSince) {
        return DateUtils.getRelativeTimeSpanString(timeSince * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }
}
