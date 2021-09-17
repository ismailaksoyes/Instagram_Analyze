package com.avalon.calizer.utils

import com.avalon.calizer.data.local.follow.FollowData
import com.avalon.calizer.data.local.follow.FollowersData
import com.avalon.calizer.data.local.follow.FollowingData


    fun List<FollowersData>.followersToFollowList() = map {
        FollowData(
            uid = it.uid,
            uniqueType = it.uniqueType,
            analyzeUserId = it.analyzeUserId,
            dsUserID = it.dsUserID,
            fullName = it.fullName,
            hasAnonymousProfilePicture = it.hasAnonymousProfilePicture,
            isPrivate = it.isPrivate,
            isVerified = it.isVerified,
            latestReelMedia = it.latestReelMedia,
            profilePicUrl = it.profilePicUrl,
            profilePicId = it.profilePicId,
            username = it.username
        )
    }
    fun FollowersData.followersToFollow() =
        FollowData(
            uid = uid,
            uniqueType = uniqueType,
            analyzeUserId = analyzeUserId,
            dsUserID = dsUserID,
            fullName = fullName,
            hasAnonymousProfilePicture = hasAnonymousProfilePicture,
            isPrivate = isPrivate,
            isVerified = isVerified,
            latestReelMedia = latestReelMedia,
            profilePicUrl = profilePicUrl,
            profilePicId = profilePicId,
            username = username
        )
    fun FollowingData.followingToFollow() =
        FollowData(
            uid = uid,
            uniqueType = uniqueType,
            analyzeUserId = analyzeUserId,
            dsUserID = dsUserID,
            fullName = fullName,
            hasAnonymousProfilePicture = hasAnonymousProfilePicture,
            isPrivate = isPrivate,
            isVerified = isVerified,
            latestReelMedia = latestReelMedia,
            profilePicUrl = profilePicUrl,
            profilePicId = profilePicId,
            username = username
        )

    fun List<FollowingData>.followingToFollowList() = map {
        FollowData(
            uid = it.uid,
            uniqueType = it.uniqueType,
            analyzeUserId = it.analyzeUserId,
            dsUserID = it.dsUserID,
            fullName = it.fullName,
            hasAnonymousProfilePicture = it.hasAnonymousProfilePicture,
            isPrivate = it.isPrivate,
            isVerified = it.isVerified,
            latestReelMedia = it.latestReelMedia,
            profilePicUrl = it.profilePicUrl,
            profilePicId = it.profilePicId,
            username = it.username
        )
    }
