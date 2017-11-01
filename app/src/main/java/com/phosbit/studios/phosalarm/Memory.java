package com.phosbit.studios.phosalarm;

import java.util.List;

/**
 * Created by Aaron on 10/31/2017.
 */

public class Memory
{
    private String content;

    Memory( String content )
    {
        this.content = content;
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

