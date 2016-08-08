package com.wikipediaclient.entities.json.wiki.article;

/**
 * Created by Arman on 2016-08-09.
 */
public class ArticleDetails {
    private String title;
    private String content;

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
    public String getContent()
    {
        return this.content;
    }


}
