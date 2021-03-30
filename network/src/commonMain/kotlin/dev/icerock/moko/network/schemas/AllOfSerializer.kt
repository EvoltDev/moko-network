/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.network.schemas

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject

abstract class AllOfSerializer<T>(serialName: String) : KSerializer<T> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): T {
        decoder as JsonDecoder

        val jsonElement = decoder.decodeSerializableValue(JsonElement.serializer())

        val json = Json(from = decoder.json) {
            ignoreUnknownKeys = true
        }

        return decodeJson(json, jsonElement)
    }

    override fun serialize(encoder: Encoder, value: T) {
        encoder as JsonEncoder

        val jsonObjects = encodeJson(encoder.json, value)

        val outputObject = buildJsonObject {
            jsonObjects.forEach { jsonObject ->
                jsonObject.entries.forEach { (key, value) ->
                    put(key, value)
                }
            }
        }

        encoder.encodeJsonElement(outputObject)
    }

    abstract fun decodeJson(json: Json, element: JsonElement): T

    abstract fun encodeJson(json: Json, value: T): List<JsonObject>
}
