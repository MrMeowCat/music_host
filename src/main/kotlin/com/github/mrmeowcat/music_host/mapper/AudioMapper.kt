package com.github.mrmeowcat.music_host.mapper

import com.github.mrmeowcat.music_host.document.AudioDocument
import com.github.mrmeowcat.music_host.document.document
import com.github.mrmeowcat.music_host.dto.Audio
import com.github.mrmeowcat.music_host.dto.dto
import org.springframework.stereotype.Component
import java.util.*

/**
 * Mapper for audio.
 */
@Component
class AudioMapper : Doc2DtoMapper<AudioDocument, Audio> {

    override fun mapToDto(doc: AudioDocument?): Audio? {
        doc ?: return null
        return dto {
            id = doc.id
            createdDate = doc.createdDate.time
            updatedDate = doc.updatedDate.time
            title = doc.title
            author = doc.author
            duration = doc.duration
            lyrics = doc.lyrics
            fileName = doc.fileName
            coverArtName = doc.coverArtName
        }
    }

    override fun mapToDoc(dto: Audio?): AudioDocument? {
        dto ?: return null
        return document {
            id = dto.id
            createdDate = Date(dto.createdDate)
            updatedDate = Date(dto.updatedDate)
            title = dto.title
            author = dto.author
            duration = dto.duration
            lyrics = dto.lyrics
            fileName = dto.fileName
            coverArtName = dto.coverArtName
        }
    }
}
