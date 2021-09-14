data class Video(
    val description: String,
    val sources: List<String>,
    val subtitle: String,
    val title: String,
    val thumb: String
)


data class VideoList(
    val videos: List<Video>
)