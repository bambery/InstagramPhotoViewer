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

    private static class ViewHolder {
        TextView username;
        ImageView profilePicture;
        ImageView clockIcon;
        TextView createdAt;
        ImageView likeIcon;
        TextView likeCount;
        ImageView instaPhoto;
        TextView caption;
        TextView commentCount;
        TextView lastComment;
        TextView secondToLastComment;
    }
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    //use the template to display each photo

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get data item for this position
        InstagramPhoto photo = getItem(position);
        // check if we are using a recycled view, if not, need to inflate
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);

            // profile photo
            viewHolder.profilePicture = (ImageView) convertView.findViewById(R.id.ivProfilePicture);

            // username
            viewHolder.username = (TextView) convertView.findViewById(R.id.tvUsername);

            // clock icon
            viewHolder.clockIcon = (ImageView) convertView.findViewById(R.id.ivClockIcon);

            // time since posted
            viewHolder.createdAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);

            // like count icon
            viewHolder.likeIcon = (ImageView) convertView.findViewById(R.id.ivLikeIcon);

            //like count
            viewHolder.likeCount = (TextView) convertView.findViewById(R.id.tvLikeCount);

            // the photo
            viewHolder.instaPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);

            // image caption
            viewHolder.caption = (TextView) convertView.findViewById(R.id.tvCaption);

            //comment count
            viewHolder.commentCount = (TextView) convertView.findViewById(R.id.tvCommentCount);

            //last comment
            viewHolder.lastComment = (TextView) convertView.findViewById(R.id.tvLastComment);

            //second to last comment
            viewHolder.secondToLastComment = (TextView) convertView.findViewById(R.id.tvSecondToLastComment);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // populate data

        //profile photo
        viewHolder.profilePicture.setImageResource(0);
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
                .into(viewHolder.profilePicture);

        // username
        viewHolder.username.setText(photo.username);

        // clock icon and set the color
        viewHolder.clockIcon.setImageResource(0);
        viewHolder.clockIcon.setColorFilter(ContextCompat.getColor(getContext(), R.color.pale_grey));
        viewHolder.clockIcon.setImageResource(R.drawable.ic_action_clock);

        // time since posted
        viewHolder.createdAt.setText(getRelativeTime(photo.createdAt));

        // like count icon & set color
        viewHolder.likeIcon.setImageResource(0);
        viewHolder.likeIcon.setColorFilter(ContextCompat.getColor(getContext(), R.color.dark_navy));
        viewHolder.likeIcon.setImageResource(R.drawable.ic_action_heart);

        // like count
        viewHolder.likeCount.setText(getLikesString(photo));

        // photo

        // clear out the image view (if the view is recycled for the photo)
        viewHolder.instaPhoto.setImageResource(0);
        //insert the image using picasso
        Picasso.with(getContext())
                .load(photo.imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.instaPhoto);

        //image caption
        String captionText = getCaptionText(photo.username, photo.caption);
        viewHolder.caption.setText(Html.fromHtml(captionText));

        // comment count
        viewHolder.commentCount.setText(getCommentCountText(photo.commentCount));

        //last comment
        String lastCommentText = getCaptionText(photo.lastCommentUsername, photo.lastCommentText);
        viewHolder.lastComment.setText(Html.fromHtml(lastCommentText));

        //cheating - second to last comment

        String secondToLastCommentText = getCaptionText(photo.secondToLastCommentUsername, photo.secondToLastCommentText);
        viewHolder.secondToLastComment.setText(Html.fromHtml(secondToLastCommentText));

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

    private String getCommentCountText(int commentCount){
        return "View all " + Integer.toString(commentCount) + " comments";
    }

    private String getRelativeTime(Long timeSince) {
        return DateUtils.getRelativeTimeSpanString(timeSince * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }
}
