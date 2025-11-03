package com.dalingge.coinvista.core.util.mmkv

import android.os.Parcelable
import com.tencent.mmkv.MMKV

fun mmkvInt(default: Int = 0) = MMKVValuable(MMKV::decodeInt, MMKV::encode, default)
fun mmkvLong(default: Long = 0L) = MMKVValuable(MMKV::decodeLong, MMKV::encode, default)
fun mmkvFloat(default: Float = 0f) = MMKVValuable(MMKV::decodeFloat, MMKV::encode, default)
fun mmkvDouble(default: Double = 0.0) = MMKVValuable(MMKV::decodeDouble, MMKV::encode, default)
fun mmkvBoolean(default: Boolean = false) = MMKVValuable(MMKV::decodeBool, MMKV::encode, default)

fun mmkvString(default: String = "") = MMKVObject(MMKV::decodeString, MMKV::encode, default)
fun mmkvStringSet(default: Set<String> = setOf()) = MMKVObject(MMKV::decodeStringSet, MMKV::encode, default)
fun mmkvBytes(default: ByteArray = byteArrayOf()) = MMKVObject(MMKV::decodeBytes, MMKV::encode, default)

fun mmkvNullableString(default: String? = null) = MMKVObject(MMKV::decodeString, MMKV::encode, default)
fun mmkvNullableStringSet(default: Set<String>? = null) = MMKVObject(MMKV::decodeStringSet, MMKV::encode, default)
fun mmkvNullableBytes(default: ByteArray? = null) = MMKVObject(MMKV::decodeBytes, MMKV::encode, default)

inline fun <reified T : Parcelable> mmkv(default: T) = MMKVParcelable(T::class.java, default)

private val defaultMMKV: MMKV by lazy { MMKV.defaultMMKV() }

internal fun mmkv(thisRef: Any): MMKV = when (thisRef) {
    is MMKVOwner -> thisRef.mmkv
    else -> defaultMMKV
}

//inline fun <reified T> mmkvJson(default: T): MMKVJson<T> {
//    return MMKVJson(typeOf<T>(), default)
//}

//class MMKVJson<T>(private val type: KType, private val default: T) : ReadWriteProperty<Any, T> {
//
//    override operator fun getValue(thisRef: Any, property: KProperty<*>): T {
//        val json = mmkv(thisRef).decodeString(property.name) ?: return default
//        val clazz = type.jvmErasure.java
//        val adapter = MoshiUtil.moshi.adapter<T>(clazz)
//        return adapter.fromJson(json) ?: default
//    }
//
//    override operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
//        val adapter = MoshiUtil.moshi.adapter<Any>(type.jvmErasure.java)
//        mmkv(thisRef).encode(property.name, adapter.toJson(value))
//    }
//}

//inline fun <reified T> mmkvList(default:  List<T>): MMKVList<T> {
//    return MMKVList(typeOf<T>(), default)
//}

//class MMKVList<T>(private val type: KType, private val default:  List<T>) : ReadWriteProperty<Any, List<T>> {
//
//    override fun getValue(thisRef: Any, property: KProperty<*>):  List<T> {
//        val jsonString =  mmkv(thisRef).decodeString(property.name) ?: return default
//        val jsonArray = JSONArray(jsonString)
//        val clazz = type.jvmErasure.java
//        val adapter = MoshiUtil.moshi.adapter<T>(clazz)
//        val result = mutableListOf<T>()
//        for (i in 0 until jsonArray.length()) {
//            val item = jsonArray.get(i)
//            val value = when (clazz) {
//                String::class.java -> item
//                Int::class.java -> (item as Number).toInt()
//                Long::class.java -> (item as Number).toLong()
//                Double::class.java -> (item as Number).toDouble()
//                Boolean::class.java -> item
//                else -> adapter.fromJsonValue(item) ?: continue
//            }
//            result.add(value as T)
//        }
//        return result
//    }
//
//    override fun setValue(thisRef: Any, property: KProperty<*>, value:  List<T>) {
//        val distinctList = value.toList()
//        val jsonArray = JSONArray()
//        distinctList.forEach {
//            jsonArray.put(it)
//        }
//        mmkv(thisRef).encode(property.name, jsonArray.toString())
//    }
//}





