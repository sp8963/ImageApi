package com.ping.imageapi

import org.json.JSONObject

interface ApiCallback {
    /**
     * API回傳成功，處理回傳JSON資料
     */
    fun onSuccess(resp: String)

    /**
     * API回傳失敗，顯示錯誤訊息
     */
    fun onError(resp: String)
}