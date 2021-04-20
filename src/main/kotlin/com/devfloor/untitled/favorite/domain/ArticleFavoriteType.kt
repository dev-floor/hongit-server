package com.devfloor.untitled.favorite.domain

/**
 * 게시글에 대한 좋아요의 유형을 관리하는 enum
 *
 * @property FAVORITE 좋아요
 * @property WONDER 나도 궁금해요
 * @property CLIP 스크랩
 */
enum class ArticleFavoriteType(val text: String) {
    FAVORITE("좋아요"),
    WONDER("나도 궁금해요"),
    CLIP("스크랩");

    fun isFavorite(): Boolean = this == FAVORITE

    fun isWonder(): Boolean = this == WONDER

    fun isClip(): Boolean = this == CLIP
}
