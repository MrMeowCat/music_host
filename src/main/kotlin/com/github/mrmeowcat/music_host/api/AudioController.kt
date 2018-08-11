package com.github.mrmeowcat.music_host.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class AudioController {

    @PostMapping("upload")
    fun upload(): Mono<ResponseEntity<String>> {
        return Mono.just(ResponseEntity.ok(""))
    }

}