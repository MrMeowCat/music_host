package com.github.mrmeowcat.music_host.document

import com.couchbase.client.java.repository.annotation.Field
import org.springframework.data.couchbase.core.mapping.Document

/**
 * Document which represents audio.
 */
@Document
class AudioDocument : AbstractDocument() {

    @Field
    var name: String? = null

    @Field
    var author: String? = null

    @Field
    var duration: Int = 0

    @Field
    var lyrics: String? = null

    @Field
    var filePath: String? = null

    @Field
    var thumbnailPath: String? = null
}
