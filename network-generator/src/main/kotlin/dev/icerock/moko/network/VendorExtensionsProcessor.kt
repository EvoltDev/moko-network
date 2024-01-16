/*
 * Copyright 2024 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.network

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.media.Schema

object VendorExtensionsProcessor : OpenApiSchemaProcessor {
    override fun process(openApi: OpenAPI, schema: Schema<*>, context: SchemaContext): Schema<*> {
        if (schema.extensions == null) {
            schema.addExtension("x-interfaces", LinkedHashSet(listOf("Parcelable")))
        }
        println(schema.extensions)
        val interfaces =
            schema.extensions.getOrPut("x-interfaces") { LinkedHashSet(listOf("Parcelable")) } as LinkedHashSet<String>
        if (interfaces.isEmpty()) {
            interfaces.add("Parcelable")
        } else {
            schema.extensions["x-interfaces"] = LinkedHashSet(interfaces.mapIndexed { index, it ->
                if (index < interfaces.size - 1) {
                    "$it, "
                } else {
                    it
                }
            })
        }
        return schema
    }
}