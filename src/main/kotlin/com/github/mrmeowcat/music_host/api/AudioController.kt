package com.github.mrmeowcat.music_host.api

import com.github.mrmeowcat.music_host.dto.Audio
import com.github.mrmeowcat.music_host.service.AudioFileService
import com.github.mrmeowcat.music_host.service.AudioService
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.io.File

/**
 * Controller for audios.
 */
@RestController
@RequestMapping("/api")
class AudioController(private val audioService: AudioService,
                      private val audioFileService: AudioFileService) {

    @GetMapping("audio")
    fun getAll(): Mono<ResponseEntity<List<Audio>>> {
        return audioService
                .findAll()
                .collectList()
                .toMono()
                .map { ResponseEntity.ok(it) }
    }

    @GetMapping("audio/{id}")
    fun getOne(@PathVariable("id") id: String = ""): Mono<ResponseEntity<Audio>> {
        return audioService.findOne(id).map { ResponseEntity.ok(it) }
    }

    @GetMapping("audio/file/{fileName}", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun streamFile(@PathVariable("fileName") fileName: String = ""): Flux<Resource> {
        val file: File = audioFileService.getFile(fileName)
        if (!file.exists()) {
            return Flux.empty()
        }
        return Flux.just(FileSystemResource(file))
    }

    @GetMapping("audio/cover/{coverName}")
    fun getCoverArt(@PathVariable("coverName") coverName: String = ""): Flux<Resource> {
        val file: File = audioFileService.getFile(coverName)
        if (!file.exists()) {
            return Flux.empty()
        }
        return Flux.just(FileSystemResource(file))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("audio", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(@RequestPart("file") fileMono: Mono<FilePart>): Mono<ResponseEntity<Audio>> {
        var fileName = ""
        return fileMono
                .map {
                    val file: File = audioFileService.prepareFile(it)
                    fileName = file.name
                    file
                }
                .map { audioFileService.parseFile(it) }
                .flatMap { audioService.save(it) }
                .doOnError { audioFileService.deleteFile(fileName) }
                .map { ResponseEntity.ok(it) }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("audio")
    fun update(@RequestBody audio: Audio?): Mono<ResponseEntity<Audio>> {
        return audioService.save(audio).map { ResponseEntity.ok(it) }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("audio/{id}")
    fun delete(@PathVariable("id") id: String = ""): Mono<Void> {
        return audioService.findOne(id)
                .map { audioFileService.deleteFile(it.fileName) }
                .flatMap { audioService.delete(id) }
    }
}
