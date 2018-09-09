package com.github.mrmeowcat.music_host.document

import com.couchbase.client.java.repository.annotation.Field
import com.couchbase.client.java.repository.annotation.Id
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import kotlin.reflect.full.createInstance

/**
 * Base document entity.
 */
abstract class AbstractDocument {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    var id: String? = null

    @Field
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    var createdDate: Date = Date()

    @Field
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    var updatedDate: Date = Date()
}

/**
 * Dsl function which creates documents derived from AbstractDocument.
 */
inline fun <reified D : AbstractDocument> document(block: D.() -> Unit): D =
        D::class.createInstance().apply(block)
