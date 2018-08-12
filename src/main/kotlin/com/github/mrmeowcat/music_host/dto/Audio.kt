package com.github.mrmeowcat.music_host.dto

/**
 * DTO which represents audio.
 */
class Audio : AbstractDto() {

    var name: String? = ""

    var author: String? = ""

    var duration: Int = 0

    var lyrics: String? = ""

    var filePath: String? = ""

    var thumbnailPath: String? = ""
}
