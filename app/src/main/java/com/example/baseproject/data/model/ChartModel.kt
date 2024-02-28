package com.example.baseproject.data.model

import com.example.baseproject.ui.home.hometab.ChildItem
import com.example.baseproject.ui.home.hometab.ParentItem

class Album(
    val id: String,
    val title: String,
    val cover: String,
    val artist: Artist,
    val tracks: TrackData,
) {
    fun getChildItem(): ChildItem {
        return ChildItem(
            childItemTitle = title + "\n" + artist.name,
            childItemImage = cover,
            data = id
        )
    }
}

class Artist(
    val id: String,
    val name: String,
    val picture: String,
    val tracklist: String,
) {
    fun getChildItem(): ChildItem {
        return ChildItem(
            childItemTitle = name,
            childItemImage = picture,
            data = tracklist
        )
    }
}

class Tracks(
    val id: String,
    val title: String,
    val album: Album,
    val artist: Artist,
    val duration: Int,
    val preview: String,
    val md5_image: String
) {
    fun getChildItem(): ChildItem {
        return ChildItem(
            childItemTitle = title + "\n" + artist.name,
            childItemImage = imageFromMD5(md5_image),
            data = preview
        )
    }
}

class Playlist(
    val id: String,
    val title: String,
    val picture: String,
    val tracklist: String,
    val user: User
) {
    fun getChildItem(): ChildItem {
        return ChildItem(
            childItemTitle = title + "\n" + user.name,
            childItemImage = picture,
            data = id
        )
    }
}

class Podcast(
    val id: String,
    val title: String,
    val picture: String,
    val link: String
) {
    fun getChildItem(): ChildItem {
        return ChildItem(
            childItemTitle = title,
            childItemImage = picture,
            data = link

        )
    }
}

class TrackData(
    val data: List<Tracks>
) {
    fun getParentItem(): ParentItem {
        return ParentItem(
            parentItemTitle = "Tracks",
            childItemList = data.map { it.getChildItem() }
        )
    }
}

class AlbumData(
    val data: List<Album>
) {
    fun getParentItem(): ParentItem {
        return ParentItem(
            parentItemTitle = "Albums",
            childItemList = data.map { it.getChildItem() }
        )
    }
}

class ArtistData(
    val data: List<Artist>
) {
    fun getParentItem(): ParentItem {
        return ParentItem(
            parentItemTitle = "Artists",
            childItemList = data.map { it.getChildItem() }
        )
    }
}

class PlaylistData(
    val data: List<Playlist>
) {
    fun getParentItem(): ParentItem {
        return ParentItem(
            parentItemTitle = "Playlists",
            childItemList = data.map { it.getChildItem() }
        )
    }
}

class PodcastData(
    val data: List<Podcast>
) {
    fun getParentItem(): ParentItem {
        return ParentItem(
            parentItemTitle = "Podcasts",
            childItemList = data.map { it.getChildItem() }
        )
    }
}

class ChartModel {
    val tracks: TrackData? = null
    val albums: AlbumData? = null
    val artists: ArtistData? = null
    val playlists: PlaylistData? = null
    val podcasts: PodcastData? = null
}


private fun imageFromMD5(md5: String): String {
    return "https://e-cdn-images.dzcdn.net/images/cover/$md5/500x500.jpg"
}

