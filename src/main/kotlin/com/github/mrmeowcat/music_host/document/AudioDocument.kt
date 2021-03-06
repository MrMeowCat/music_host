package com.github.mrmeowcat.music_host.document

import com.couchbase.client.java.repository.annotation.Field
import org.springframework.data.couchbase.core.mapping.Document

/**
 * Document which represents audio.
 */
@Document
class AudioDocument : AbstractDocument() {

    @Field
    var title: String = ""

    @Field
    var author: String = ""

    @Field
    var duration: Int = 0

    @Field
    var lyrics: String = ""

    @Field
    var fileName: String = ""

    @Field
    var coverArtName: String = ""
}
