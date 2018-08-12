package com.github.mrmeowcat.music_host.dto

import kotlin.reflect.full.createInstance

/**
 * Base DTO object.
 */
abstract class AbstractDto {

    var id: String? = null

    var createdDate: Long = 0

    var updatedDate: Long = 0
}

/**
 * Dsl function which creates DTOs derived from AbstractDto.
 */
inline fun <reified D : AbstractDto> dto(block: D.() -> Unit): D =
        D::class.createInstance().apply(block)
