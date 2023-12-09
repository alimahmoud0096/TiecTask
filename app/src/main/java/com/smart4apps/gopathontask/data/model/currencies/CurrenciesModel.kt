package com.smart4apps.gopathontask.data.model.currencies


import com.google.gson.annotations.SerializedName


data class CurrenciesModel(
    @SerializedName("success")
    val success: Boolean?=null,
    @SerializedName("historical")
    val historical: Boolean?=null,
    @SerializedName("rates")
    val rateModel: Map<String, Double>?=null,
    @SerializedName("base")
    val base: String?=null,
    @SerializedName("date")
    val date: String?=null,
    @SerializedName("timestamp")
    val timestamp: Long
)
