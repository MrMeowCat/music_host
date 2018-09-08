package com.github.mrmeowcat.music_host.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Generic reactive CRUD service.
 */
interface CrudService<Dto, ID> {

    /**
     * Finds all records.
     */
    fun findAll(): Flux<Dto>

    /**
     * Finds a record by id.
     */
    fun findOne(id: ID): Mono<Dto>

    /**
     * Saves or updates (if id is given) a record.
     */
    fun save(dto: Dto?): Mono<Dto>

    /**
     * Deletes a record by id.
     */
    fun delete(id: ID): Mono<Void>

    /**
     * Checks if a record exists by id.
     */
    fun exists(id: ID): Mono<Boolean>
}
