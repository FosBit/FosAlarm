package com.phosbit.studios.phosalarm;

import java.util.List;

/**
 * Created by Aaron on 10/31/2017.
 */

public class Memory
{
    private String title;
    private String content;

    Memory( String title, String content )
    {
        setTitle( title );
        setContent( content );
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setContent( String content )
    {
        this.content = content;
    }

    public String getContent()
    {
        return this.content;
    }
}

