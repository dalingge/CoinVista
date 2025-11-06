package com.dalingge.coinvista.core.model.entity

import kotlinx.serialization.Serializable

@Serializable
data class MarketsCap(
    val marketCap: Long= 0,
    val volume: Long = 0,
    val btcDominance: Double = 0.0,
    val marketCapChange: Double = 0.0,
    val volumeChange: Double = 0.0,
    val btcDominanceChange: Double = 0.0,
)
