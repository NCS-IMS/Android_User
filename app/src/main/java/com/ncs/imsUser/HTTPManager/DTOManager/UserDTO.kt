package com.ncs.imsUser.HTTPManager.DTOManager

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("message")
    var message : String,
    @SerializedName("result")
    var result : UserData

)
data class UserData(
        @SerializedName("kakaoid")
        var kakaoid : String,
        @SerializedName("name")
        var name : String,
        @SerializedName("gender")
        var gender : String,
        @SerializedName("phone")
        var phone : String,
        @SerializedName("address")
        var address : String,
        @SerializedName("bloodType")
        var bloodType : String,
        @SerializedName("imgSrc")
        var imgSrc : String,
        @SerializedName("email")
        var email : String,
        @SerializedName("age")
        var age : String,
        @SerializedName("birth")
        var birth : String,
)
