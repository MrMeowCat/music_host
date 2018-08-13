package com.github.mrmeowcat.music_host.dto

/**
 * DTO which represents audio.
 */
class Audio : AbstractDto() {

    var title: String? = null

    var author: String? = null

    var duration: Int = 0

    var lyrics: String? = null

    var fileName: String? = null

    var coverArtName: String? = null
}
