package com.kkultrip.threego.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Page {

    /** 현재페이지 */
    private int pageIndex = 1;

    /** 한 페이지에 보여질 게시물 갯수 */
    private int indexSize = 10;

    /** 한페이지에 보여질 최대 페이지 갯수 */
    private int pageSize = 10;

    /** 게시물의 총 갯수*/
    private int indexCount = 1;

    /** 총 페이지 갯수 */
    private int pageCount = 1;

    /** 페이징 시작 번호 */
    private int startPage = 1;

    /** 페이징 끝 번호 */
    private int endPage = 1;

    /** 이전 페이지 유무*/
    private boolean isPrev = false;

    /** 다음 페이지 유무*/
    private boolean isNext = false;

    /** 검색 조건 */
    private String searchCondition = "";

    /** 검색 키워드 */
    private String searchKeyword = "";

    /** 검색 키워드 사용 유무 */
    private boolean isSearch = false;

    /**
     * 검색 키워드 없이 페이징 처리
     * @param pageIndex (현재 페이지)
     * @param indexSize (한 페이지 당 보여질 인덱스 수)
     * @param indexCount (총 인덱스 수)
     */
    public Page(int pageIndex, int indexSize, int indexCount) {
        this.indexSize = indexSize;
        this.indexCount = indexCount;
        this.pageCount = (int) Math.ceil(indexCount / (double) indexSize);
        this.pageIndex = pageIndex > this.pageCount || pageIndex < 1 ? 1 : pageIndex;
        this.startPage = this.indexCount == 0 ? 0 : ((this.pageIndex-1) / pageSize) * pageSize + 1;
        this.endPage = Math.min(startPage + pageSize - 1, pageCount);
        this.isPrev = this.pageIndex > 1;
        this.isNext = this.pageIndex < pageCount;
    }

    /**
     * 검색 키워드 포함 페이징 처리
     * @param pageIndex (현재 페이지)
     * @param indexSize (한 페이지 당 보여질 인덱스 수)
     * @param indexCount (총 인덱스 수)
     * @param searchCondition (검색 조건)
     * @param searchKeyword (검색 키워드)
     */

    public Page(int pageIndex, int indexSize, int indexCount, String searchCondition, String searchKeyword) {
        this.indexSize = indexSize;
        this.indexCount = indexCount;
        this.pageCount = (int) Math.ceil(indexCount / (double) indexSize);
        this.pageIndex = pageIndex > this.pageCount || pageIndex < 1 ? 1 : pageIndex;
        this.startPage = this.indexCount == 0 ? 0 : ((this.pageIndex-1) / pageSize) * pageSize + 1;
        this.endPage = Math.min(startPage + pageSize - 1, pageCount);
        this.isPrev = this.pageIndex > 1;
        this.isNext = this.pageIndex < pageCount;
        this.searchCondition = searchCondition;
        this.searchKeyword = searchKeyword;
        this.isSearch = true;
    }

}
