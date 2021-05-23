package com.namnguyen.data.cache

interface AppSharePref {
    var appId: String
    var geocodeKey: String
    var jsonHistoryName: String
}

private const val APP_ID = "SP_APP_ID"
private const val GEO_KEY = "SP_GEO_KEY"
private const val JSON_CITY_NAME = "SP_JSON_CITY_NAME"

class AppSharePrefImpl(
    private val pref: CoreSharedPref
) : AppSharePref {

    override var appId: String
        get() = pref.getString(APP_ID) ?: "2ed34880c948e7e9434e5f951f96e18b"
        set(value) = pref.saveString(APP_ID, value)

    override var geocodeKey: String
        get() = pref.getString(GEO_KEY) ?: "64b4adc491c8476e93772284769a4005"
        set(value) = pref.saveString(GEO_KEY, value)

    override var jsonHistoryName: String
        get() = pref.getString(JSON_CITY_NAME) ?: ""
        set(value) = pref.saveString(JSON_CITY_NAME, value)
}
