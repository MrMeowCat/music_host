package com.github.mrmeowcat.music_host.service

import com.github.mrmeowcat.music_host.dto.Audio
import com.github.mrmeowcat.music_host.dto.dto
import org.apache.commons.io.FilenameUtils
import org.jaudiotagger.audio.AudioFile
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag
import org.jaudiotagger.tag.images.Artwork
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.util.*

/**
 * Implementation of AudioFileService.
 */
@Service
class AudioFileServiceImpl : AudioFileService {

    @Value("\${music_host.storage.path}")
    private lateinit var filePath: String

    override fun prepareAudioFile(part: FilePart): File {
        validateExtension(part.filename())
        val extension: String = FilenameUtils.getExtension(part.filename())
        val file = File("$filePath${File.separatorChar}${Date().time}.$extension")
        part.transferTo(file).then()
        return file
    }

    override fun parseAudioFile(file: File): Audio {
        val audioFile: AudioFile = AudioFileIO.read(file)
        val tag: Tag = audioFile.tag
        val coverArtName: String? = if (tag.firstArtwork != null)
            FilenameUtils.getBaseName(file.name) else null
        saveCoverArt(tag.firstArtwork, coverArtName)
        return dto {
            title = tag.getFirst(FieldKey.TITLE)
            author = tag.getFirst(FieldKey.ARTIST)
            duration = audioFile.audioHeader.trackLength
            lyrics = tag.getFirst(FieldKey.LYRICS)
            fileName = file.name
            this.coverArtName = coverArtName
        }
    }

    override fun getAudioFile(name: String?): File = File("$filePath${File.separatorChar}$name")

    override fun deleteAudioFile(name: String?) {
        validateExtension(name)
        val audioFile = File("$filePath${File.separatorChar}$name")
        val coverArtFile = File("$filePath${File.separatorChar}${FilenameUtils.getBaseName(name)}")
        Files.deleteIfExists(audioFile.toPath())
        Files.deleteIfExists(coverArtFile.toPath())
    }

    private fun validateExtension(fileName: String?) {
        if (FilenameUtils.getExtension(fileName).isNullOrEmpty()) {
            throw IllegalArgumentException("Audio file must have extension!")
        }
    }

    private fun saveCoverArt(art: Artwork?, name: String?) {
        art ?: return
        FileOutputStream("$filePath${File.separatorChar}$name").use {
            it.write(art.binaryData)
        }
    }
}
