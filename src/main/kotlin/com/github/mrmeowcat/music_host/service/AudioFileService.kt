package com.github.mrmeowcat.music_host.service

import com.github.mrmeowcat.music_host.dto.Audio
import org.springframework.http.codec.multipart.FilePart
import java.io.File

/**
 * Interface for parsing and i/o audio files.
 */
interface AudioFileService {

    /**
     * Saves multipart as file on filesystem.
     */
    fun prepareFile(part: FilePart): File

    /**
     * Parses audio file and saves cover art if present.
     */
    fun parseAudioFile(file: File): Audio

    /**
     * Deletes audio and cover art if present.
     */
    fun deleteAudioFile(name: String?)
}
