package com.github.mrmeowcat.music_host.dto

/**
 * DTO which represents audio.
 */
class Audio : AbstractDto() {

    var title: String = ""

    var author: String = ""

    var duration: Int = 0

    var lyrics: String = ""

    var fileName: String = ""

    var coverArtName: String = ""
}
