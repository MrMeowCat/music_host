package com.github.mrmeowcat.music_host.api

import com.github.mrmeowcat.music_host.dto.Audio
import com.github.mrmeowcat.music_host.service.AudioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

/**
 * Controller for audios.
 */
@RestController
@RequestMapping("/api")
class AudioController {

    @Autowired
    private lateinit var audioService: AudioService

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

    @PostMapping("audio")
    fun upload(@RequestBody audio: Audio?): Mono<ResponseEntity<Audio>> {
        return audioService.save(audio).map { ResponseEntity.ok(it) }
    }

    @PutMapping("audio")
    fun update(@RequestBody audio: Audio?): Mono<ResponseEntity<Audio>> {
        return audioService.save(audio).map { ResponseEntity.ok(it) }
    }

    @DeleteMapping("audio/{id}")
    fun delete(@PathVariable("id") id: String = ""): Mono<Void> {
        return audioService.delete(id)
    }
}