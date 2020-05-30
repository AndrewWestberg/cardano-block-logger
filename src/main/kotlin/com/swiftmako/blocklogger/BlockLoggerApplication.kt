package com.swiftmako.blocklogger

import com.squareup.moshi.Moshi
import com.swiftmako.blocklogger.model.TraceAdoptedBlock
import com.swiftmako.blocklogger.moshi.adapters.JodaDateTimeAdapter
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.time.delay
import org.apache.commons.io.input.Tailer
import org.apache.commons.io.input.TailerListenerAdapter
import java.io.File
import java.time.Duration

class BlockLoggerApplication

fun main(args: Array<String>) = runBlocking {
    if (args.size != 1) {
        println("Usage: java -jar cardano-block-logger.jar <path to log file>")
        return@runBlocking
    }

    val moshi = Moshi.Builder().add(JodaDateTimeAdapter()).build()

    val tailer = Tailer.create(File(args[0]), object : TailerListenerAdapter() {
        override fun handle(line: String?) {
            if (line?.contains("TraceAdoptedBlock") == true) {
                val adoptedBlock = moshi.adapter(TraceAdoptedBlock::class.java).fromJson(line)
                println(adoptedBlock)
            }
        }

        override fun handle(ex: Exception?) {
            ex?.printStackTrace()
        }
    })

    delay(Duration.ofDays(65535L))
}