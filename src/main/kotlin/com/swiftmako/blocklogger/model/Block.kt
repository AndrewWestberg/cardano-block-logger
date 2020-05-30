package com.swiftmako.blocklogger.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Block(
    @Json(name = "slot") val slot: Long,
    @Json(name = "block hash") val hash: String
) {
    override fun toString(): String {
        return "Block(slot=$slot, hash='${hash.split(" ", "}").last { it.isNotBlank() }}')"
    }
}


//{"at":"2020-05-30T16:16:31.21Z","env":"1.12.0:044e8","ns":["cardano.node.Forge"],"data":{"kind":"TraceAdoptedBlock","slot":522991,"block hash":"HashHeader {unHashHeader = 921bee6c8d9a1d1fd4969891bfea17f6291789e997975b015505de7463b4d22f}"},"app":[],"msg":"","pid":"2444622","loc":null,"host":"papa","sev":"Info","thread":"49"}