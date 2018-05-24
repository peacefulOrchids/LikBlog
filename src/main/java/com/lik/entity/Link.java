package com.lik.entity;

public class Link {
    /**
     * 
     */
    protected Integer id;

    /**
     * 
     */
    protected String linkname;

    /**
     * 
     */
    protected String linkurl;

    /**
     * link.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * link.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * link.linkName
     */
    public String getLinkname() {
        return linkname;
    }

    /**
     * link.linkName
     */
    public void setLinkname(String linkname) {
        this.linkname = linkname == null ? null : linkname.trim();
    }

    /**
     * link.linkUrl
     */
    public String getLinkurl() {
        return linkurl;
    }

    /**
     * link.linkUrl
     */
    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl == null ? null : linkurl.trim();
    }
}