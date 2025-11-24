package com.dalingge.coinvista.core.model.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class FearGreed(
    val name: String = "",
    val now: FearGreedCycle = FearGreedCycle(),
    val lastWeek: FearGreedCycle = FearGreedCycle(),
    val yesterday: FearGreedCycle = FearGreedCycle(),
)

@Serializable
data class FearGreedCycle(
    @SerialName("value_classification")
    val classification: String = "",
    val value: Int = 0,
    val timestamp: Long = 0,
)