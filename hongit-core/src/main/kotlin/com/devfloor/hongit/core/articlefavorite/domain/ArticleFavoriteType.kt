package com.devfloor.hongit.core.articlefavorite.domain

import com.devfloor.hongit.core.common.domain.BaseEnum

/**
 * 게시글에 대한 좋아요의 유형을 관리하는 enum
 *
 * @property text 좋아요 유형
 */
enum class ArticleFavoriteType(
    override val text: String,
) : BaseEnum {
    FAVORITE("좋아요"),
    WONDER("나도 궁금해요"),
    CLIP("스크랩"),
    ;

    override val id: String
        get() = this.name

}
