package main.service.paging;

import lombok.Getter;
import main.model.Post;

import java.util.List;
@Getter
public class Pagination {

    private int startPageNum = 1;
    private int curPageNum;
    private int lastPageNum;

    private int listStartNum;
    private int listEndNum;
    private final int displayPageBoxCnt = 5;
    private final int displayPostPerPage = 5;
    public static Pagination paging(int currentPage, int totalPosts) {
        Pagination page = new Pagination();
        page.curPageNum = currentPage;
        page.lastPageNum = (int) (Math.ceil(totalPosts / (double) page.displayPostPerPage));

        if(page.curPageNum > page.lastPageNum){
            page.curPageNum = page.lastPageNum;
        }

        if(page.curPageNum <= 0){
            page.curPageNum = 1;
        }

        if(page.curPageNum % page.displayPageBoxCnt == 0) {
            page.listStartNum = ((page.curPageNum / page.displayPageBoxCnt)-1) * page.displayPageBoxCnt + 1;
        }else {
            page.listStartNum = (page.curPageNum - 1 ) * page.displayPageBoxCnt + 1;
        }

        page.listEndNum = (page.curPageNum * page.displayPageBoxCnt);

        if(page.listEndNum > totalPosts) {
            page.listEndNum = totalPosts;
        }

        return page;
    }
}
