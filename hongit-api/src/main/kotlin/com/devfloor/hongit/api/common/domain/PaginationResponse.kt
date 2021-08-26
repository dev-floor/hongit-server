package com.devfloor.hongit.api.common.domain

import kotlin.math.ceil

data class PaginationResponse(
    val page: Int,
    val range: Int, // 각 페이지 범위 시작번호
    val listCnt: Int, // 총 게시물 개수
    val listSize: Int,
    val rangeSize: Int,
    val pageCnt: Int,
    val startPage: Int,
    val startList: Int,
    val endPage: Int,
    val prev: Boolean,
    val next: Boolean,
) {
    constructor(
        page: Int,
        range: Int,
        listCnt: Int
    ) : this(
        page = page,
        range = range,
        listCnt = listCnt,
        listSize = 10, // 초기값으로 목록개수를 10으로 셋팅
        rangeSize = 10, // 초기값으로 페이지범위를 10으로 셋팅
        pageCnt = ceil((listCnt / 10).toDouble()).toInt(),
        startPage = (range - 1) * 10 + 1,
        startList = (page - 1) * 10,
        endPage = range * 10,
        prev = range != 1,
        next = range * 10 <= ceil((listCnt / 10).toDouble()).toInt(),
    )
}
