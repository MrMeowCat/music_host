package com.github.mrmeowcat.music_host

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MusicHostApplication

fun main(args: Array<String>) {
    runApplication<MusicHostApplication>(*args)
}
