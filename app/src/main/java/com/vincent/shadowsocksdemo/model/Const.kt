package com.vincent.shadowsocksdemo.model

/**
 * Created by Vincent on 2020/1/6.
 */
interface Const {

    companion object {
        const val TOOLBAR_HOME : Int = -1
        const val DIALOG_FRAGMENT: String = "DialogFragment"

        // Bundle
        const val BUNDLE_PARCELABLE = "BundleParcelable"
        const val BUNDLE_TITLE = "BundleTitle"
        const val BUNDLE_HINT = "BundleHint"
        const val BUNDLE_TYPE = "BundleType"
        const val BUNDLE_IS_PASSWORD = "BundleIsPassword"

        // BackStackName
        const val BACK_HOME = "BackHome"
        const val BACK_PROFILE = "BackProfile"
        const val BACK_DIALOG = "BackDialog"
        const val BACK_LOGIN = "BackLogin"

        const val DEFAULT_SS_URL = "ss://YWVzLTI1Ni1jZmI6YWVzX3Bhc3N3b3Jk@34.84.14.190:8399#SSTest"

        // EventMessage
        const val EVENT_NETWORK_DONE = 1
    }

}