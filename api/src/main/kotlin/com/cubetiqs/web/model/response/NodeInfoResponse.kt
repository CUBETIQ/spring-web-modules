package com.cubetiqs.web.model.response

import io.swagger.v3.oas.annotations.media.Schema
import org.apache.commons.lang3.SystemUtils
import java.util.*

@Schema(name = "NodeInfoResponse", description = "Node Info Response")
data class NodeInfoResponse(
    @Schema(name = "instance", description = "Instance ID of the node")
    val instance: String?,

    @Schema(name = "serverTime", description = "Server Time of the node")
    val serverTime: Date,
) : BaseRequestModel {
    companion object {
        val INFO get() = NodeInfoResponse(
            instance = SystemUtils.getHostName(),
            serverTime = Date(),
        )
    }
}
