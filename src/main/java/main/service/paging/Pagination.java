package main.service.paging;

import lombok.Getter;

@Getter
public class Pagination {

    private int startPageNum;
    private int curPageNum;
    private int endPageNum;

    private int listStartNum;
    private int listEndNum;

    private int totalPageCnt;
    private static final int displayCnt = 10;
    public static Pagination paging(int currentPage, int totalPosts) {
        Pagination page = new Pagination();

        page.curPageNum = currentPage;
        page.startPageNum = ((currentPage - 1) / 5 + 1);
        page.endPageNum = ((currentPage - 1) / 5 + 1) * 5;

        page.totalPageCnt = totalPosts / displayCnt;
        if ((totalPosts % 10) != 0){
            page.totalPageCnt++;
        }
        if (page.endPageNum > page.totalPageCnt) {
            page.endPageNum = page.totalPageCnt;
        }

        page.listStartNum = ((currentPage - 1) * displayCnt) + 1;
        page.listEndNum = currentPage * displayCnt;

        if (page.listEndNum > totalPosts) {
            page.listEndNum = totalPosts;
        }

        return page;
    }
}
