package com.devfloor.hongit.api.article.domain

/**
 * 게시글 목록 정렬 유형을 관리하는 enum
 *
 * @property text 옵션의 유형
 */
enum class ArticleSortType {
    CREATED,
    VIEW_COUNT,
    FAVORITE,
}
