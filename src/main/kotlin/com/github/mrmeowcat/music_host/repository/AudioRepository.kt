package com.github.mrmeowcat.music_host.repository

import com.github.mrmeowcat.music_host.document.AudioDocument
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed
import org.springframework.data.couchbase.core.query.Query
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Repository for audio.
 */
@N1qlPrimaryIndexed
interface AudioRepository : ReactiveCouchbaseRepository<AudioDocument, String> {

    /**
     * Finds all records.
     */
    @Query("#{#n1ql.selectEntity}")
    override fun findAll(): Flux<AudioDocument>

    /**
     * Finds record by id.
     */
    @Query("#{#n1ql.selectEntity} WHERE meta().id = $1")
    override fun findById(id: String): Mono<AudioDocument>
}
