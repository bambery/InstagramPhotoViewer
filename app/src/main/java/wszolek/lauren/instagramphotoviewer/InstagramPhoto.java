package wszolek.lauren.instagramphotoviewer;

public class InstagramPhoto {
    public String username;
    public String userProfilePictureUrl;
    public String caption;
    public String imageUrl;
    public String type;
    public int imageHeight;
    public int likesCount;
    public Long createdAt;
    //hacky comment stuff
    public int commentCount;
    //just the most recent comment
    public String lastCommentUsername;
    public String lastCommentText;
    // cheating, second to last comment also
    public String secondToLastCommentText;
    public String secondToLastCommentUsername;

    // if I ever wanted to handle all of the comments
    //public org.json.JSONArray recentComments;
}
