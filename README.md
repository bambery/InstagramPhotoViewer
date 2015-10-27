# Instagram Photo Viewer

This is a readonly Instagram photo viewer using live "popular photos" data from the [Instagram API.](https://instagram.com/developer/endpoints/media/#get_media_popular)

Time Spent: ~15 hours

## Completed User Stories:

* [x] User can scroll through current popular photos from Instagram
* [x] For each photo displayed, user can see the following details:
  * [x] Graphic, Caption, Username
  * [x] (Optional) relative timestamp, like count, user profile image
* [x] (Optional) *Advanced*: Add pull-to-refresh for popular stream with [SwipeRefreshLayout](http://guides.codepath.com/android/Implementing-Pull-to-Refresh-Guide)
* [x] (Optional) *Advanced*: Show latest comment for each photo (bonus: show last 2 comments)
* [x] (Optional) *Advanced*: Display each user profile image using a RoundedImageView
* [x] (Optional) *Advanced*: Display a nice default placeholder graphic for each image during loading (read more about Picasso)
* [x] (Optional) *Advanced*: Improve the user interface through styling and coloring

## Notes:

There's [a plugin for Android Studio](https://github.com/konifar/android-material-design-icon-generator-plugin) to handle icons from the Material Design library which makes life immensely easier. My relative layout started to get very large by the end and I wonder if there isn't a better way to organize it. I didn't have time to implement comments as a separate class. I could not figure out why the api does not return the actual dimensions of the photos so I can size my views properly... every photo is sent with height of 640 even though the actual dimensions vary since Instagram no longer enforces square photos. I never want to look at another photo of a Kardashian.

## Walkthrough of all user stories:

![instagram_photo_viewer](https://cloud.githubusercontent.com/assets/161639/10750280/dbcf0ba6-7c30-11e5-8b70-03da7cd7f833.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

