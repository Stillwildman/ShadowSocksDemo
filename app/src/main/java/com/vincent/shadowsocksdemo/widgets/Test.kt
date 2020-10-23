package com.vincent.shadowsocksdemo.widgets

/**
 * Created by Vincent on 2020/2/20.
 */
class Test {

    fun longestCommonPrefix(strs: Array<String>?): String? {
        if (strs.isNullOrEmpty()) return ""
        var pre = strs[0]
        var i = 1
        while (i < strs.size) {
            while (strs[i].indexOf(pre) != 0) {
                pre = pre.substring(0, pre.length - 1)
            }
            i++
        }
        return pre
    }


    fun removeDuplicates(nums: IntArray): Int {
        if (nums.size <= 1) return nums.size
        var i = 0

        for (j in 1 until nums.size) {
            if (nums[j] != nums[i]) {
                i++
                nums[i] = nums[j]
            }
        }
        return i + 1
    }
}