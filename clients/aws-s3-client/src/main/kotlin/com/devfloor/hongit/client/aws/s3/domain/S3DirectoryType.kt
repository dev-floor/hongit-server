package com.devfloor.hongit.client.aws.s3.domain

enum class S3DirectoryType(
    val text: String,
) {
    PROFILE("profile"),
    ARTICLE("article"),
    COMMENT("comment"),
    ETC("etc"),
    ;
}
