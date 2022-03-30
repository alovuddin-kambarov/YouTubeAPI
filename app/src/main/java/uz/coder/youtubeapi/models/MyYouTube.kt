package uz.coder.youtubeapi.models

data class MyYouTube(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val pageInfo: PageInfo,
    val regionCode: String
)