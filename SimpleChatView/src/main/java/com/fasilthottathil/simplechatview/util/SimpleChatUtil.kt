package com.fasilthottathil.simplechatview.util

class SimpleChatUtil {

    companion object{
        private const val secondMillis = 1000
        private const val minuteMillis = 60 * secondMillis
        private const val hourMillis = 60 * minuteMillis
        private const val daysMillis = 24 * hourMillis

        fun getTimeAgo(timeStamp: Long): CharSequence? {
            var time = timeStamp
            if (time < 1000000000000L) {
                time *= 1000
            }

            val now = System.currentTimeMillis()
            if (time > now || time <= 0) {
                return null
            }

            val diff: Long = now - time
            return when {
                diff < minuteMillis -> {
                    "just now"
                }
                diff < 2 * minuteMillis -> {
                    "1 min ago"
                }
                diff < 50 * minuteMillis -> {
                    " ${diff / minuteMillis} min ago"
                }
                diff < 90 * minuteMillis -> {
                    "1 hr ago"
                }
                diff < 24 * hourMillis -> {
                    " ${diff / hourMillis} hr ago"
                }
                diff < 48 * hourMillis -> {
                    "yesterday"
                }
                else -> {
                    " ${diff / daysMillis} d ago"
                }
            }
        }

    }
}