package com.github.mrmeowcat.music_host.mapper

import com.github.mrmeowcat.music_host.document.AbstractDocument
import com.github.mrmeowcat.music_host.dto.AbstractDto

/**
 * Mapper from Document to DTO and vice versa.
 */
interface Doc2DtoMapper<Doc : AbstractDocument, Dto : AbstractDto> {

    /**
     * Maps Document to DTO.
     */
    fun mapToDto(doc: Doc?): Dto?

    /**
     * Maps DTO to Document. Not required to be implemented.
     */
    fun mapToDoc(dto: Dto?): Doc? {
        throw IllegalArgumentException("Method is not implemented.")
    }
}
