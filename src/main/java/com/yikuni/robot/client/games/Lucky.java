package com.yikuni.robot.client.games;

import java.util.Date;

public class Lucky {
    private int status; // 幸运度
    private Date date;  // 哪一天


    public Lucky(int status, Date date) {
        this.status = status;
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
