package com.dalingge.coinvista.core.util.mmkv

import com.tencent.mmkv.MMKV

interface MMKVOwner {
    val mmkv: MMKV
}