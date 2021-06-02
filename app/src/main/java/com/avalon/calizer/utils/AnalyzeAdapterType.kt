package com.avalon.calizer.utils

enum class AnalyzeAdapterType(val type:Long) {
    STORY_VIEWS(0L),
    ALL_POST(1L),
    ALL_FOLLOWERS(2L),
    ALL_FOLLOWING(3L),
    NEW_FOLLOWERS(4L),
    NEW_FOLLOWING(5L),
    UNFOLLOWING(6L)
}