package com.github.mrmeowcat.music_host.service

import com.github.mrmeowcat.music_host.dto.Audio
import com.github.mrmeowcat.music_host.mapper.AudioMapper
import com.github.mrmeowcat.music_host.repository.AudioRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 * Implementation of AudioService.
 */
@Service
class AudioServiceImpl(private val audioRepository: AudioRepository,
                       private val audioMapper: AudioMapper) : AudioService {

    override fun findAll(): Flux<Audio> =
            audioRepository.findAll().map { audioMapper.mapToDto(it) }

    override fun findOne(id: String): Mono<Audio> =
            audioRepository.findById(id).map { audioMapper.mapToDto(it) }

    override fun save(dto: Audio?): Mono<Audio> {
        dto ?: throw IllegalArgumentException("Cannot save null")
        dto.updatedDate = Date().time
        return audioRepository
                .save(audioMapper.mapToDoc(dto)!!)
                .map { audioMapper.mapToDto(it) }
    }

    override fun delete(id: String): Mono<Void> =
            audioRepository.deleteById(id)

    override fun exists(id: String): Mono<Boolean> =
            audioRepository.existsById(id)
}
