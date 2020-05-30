package com.swiftmako.blocklogger.moshi.adapters

import com.squareup.moshi.*
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat


class JodaDateTimeAdapter : JsonAdapter<DateTime>() {
    @FromJson
    override fun fromJson(reader: JsonReader): DateTime? {
        if (reader.peek() === JsonReader.Token.NULL) {
            return reader.nextNull()
        }
        val dateString = reader.nextString()
        return DateTime.parse(dateString)
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: DateTime?) {
        if (value == null) {
            writer.nullValue()
        } else {
            writer.value(ISODateTimeFormat.basicDateTime().print(value))
        }
    }
}