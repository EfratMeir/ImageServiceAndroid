package com.example.efrat.myapplication;

public class ProgressBar {

    private int transfered = 0;
    private int limit = 0;
    boolean finished = false;

    /**
     * constructor
     */
    public ProgressBar(int numOfPics)
    {
        this.limit = numOfPics;
    }

    public int getLimit()
    {
        return this.limit;
    }


    public int getTransferd()
    {
        return this.transfered;
    }

    public boolean isFinished()
    {
        return this.finished;
    }

    public void Inc()
    {
        ++this.transfered ;
        if(transfered == limit)
            finished = true;
    }
}