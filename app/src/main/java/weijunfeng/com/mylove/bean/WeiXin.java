package weijunfeng.com.mylove.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

/**
 * Created by hexin on 2016/9/19.
 */

public class WeiXin {
    @JSONField(name = "totalPage")
    private String totalPage;
    @JSONField(name = "ps")
    private String ps;
    @JSONField(name = "pno")
    private String pno;
    @JSONField(name = "list")
    private ArrayList<Item> items;

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public static class Item {
        @JSONField(name = "id")
        private String id;
        @JSONField(name = "title")
        private String title;
        @JSONField(name = "source")
        private String source;
        @JSONField(name = "firstImg")
        private String firstImg;
        @JSONField(name = "mark")
        private String mark;
        @JSONField(name = "url")
        private String url;

        public String getFirstImg() {
            return firstImg;
        }

        public void setFirstImg(String firstImg) {
            this.firstImg = firstImg;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
