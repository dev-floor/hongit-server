package com.devfloor.hongit.api.article.domain

import com.devfloor.hongit.core.common.domain.BaseEnum

/**
 * 게시글 목록 정렬 유형을 관리하는 enum
 *
 * @property text 옵션의 유형
 */
enum class ArticleSortType(
    override val text: String,
) : BaseEnum {
    CREATED("최신순"),
    VIEW_COUNT("조회순"),
    FAVORITE("좋아요순")
    ;

    override val id: String
        get() = this.name
}
