package com.ncs.imsUser.HTTPManager.DTOManager

import com.google.gson.annotations.SerializedName

data class PubicDTO(
    @SerializedName("message")
    var message : String
)
