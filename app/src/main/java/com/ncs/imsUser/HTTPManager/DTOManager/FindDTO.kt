package com.ncs.imsUser.HTTPManager.DTOManager

import com.google.gson.annotations.SerializedName

data class FindDTO(
        @SerializedName("message")
        var message : String,
        @SerializedName("result")
        var result : List<FindData>
)
data class FindData(
        @SerializedName("address_name")
        var address_name : String,
        @SerializedName("road_address_name")
        var road_address_name : String,
        @SerializedName("phone")
        var phone : String,
        @SerializedName("place_name")
        var place_name : String,
        @SerializedName("distance")
        var distance : String,
        @SerializedName("x")
        var x : String,
        @SerializedName("y")
        var y : String,
)
