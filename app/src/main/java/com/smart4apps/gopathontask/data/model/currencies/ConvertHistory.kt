package com.smart4apps.gopathontask.data.model.currencies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "converthistory")
data class ConvertHistory (
    @PrimaryKey  val timestamp: Long,
    val date: String?=null,
    val from: String?=null,
    val to: String?=null,
    val amount: String?=null,
    val ratio: String?=null,
)