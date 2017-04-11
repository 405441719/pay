package com.payinxl.common.persistence;

import java.util.List;

/**
 * Created by barry
 * Date:2017/2/21
 */
public class PageContent<T> {
    private int prev;// 上一页索引
    private int next;// 下一页索引
    private int curPage;
    private int coutpage;
    private String href;
    private int pageSize;
    private List<T> list;

    public PageContent() {
        this.prev = 1;
        this.next = 2;
        this.curPage = 1;
        this.coutpage = 1;
        this.pageSize = 8;
    }
    public String getPagehtml() {
        StringBuilder sb = new StringBuilder();
        if(curPage>coutpage){
            curPage= coutpage;
        }
        if(curPage>1){
            prev=curPage-1;
        }else{
            prev=1;
        }
        if(next>coutpage){
            next=coutpage;
        }else{
            next=curPage+1;
        }
        sb.append("<a class='prevPage iconfont pagea' onclick=\"page('"+prev+"');\"  href='javascript:'>&#xe613;</a>" +
                "<span>"+curPage+"&nbsp;/&nbsp;"+coutpage+"</span>" +
                "<a class='nextPage iconfont pagea' onclick=\"page('" + next + "');\"  href='javascript:' title='下一页' >&#xe612;</a><span> 跳转至</span>" +
                "<input type=\"number\" id=\"gopagenum\" class=\"goPageInput pageNum\" name=\"page\"/" );
        sb.append( " value=\""+curPage+"\" action='"+href+"' max=\""+coutpage+"\" min = '1'/>" +
                "<button type=\"button\"  id=\"okSearch\"  class=\"centerBtn\">确定</button>");
        return sb.toString();
    }
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPrev() {
        return prev;
    }

    public void setPrev(int prev) {
        this.prev = prev;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getCoutpage() {
        return coutpage;
    }

    public void setCoutpage(int coutpage) {
        this.coutpage = coutpage;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
