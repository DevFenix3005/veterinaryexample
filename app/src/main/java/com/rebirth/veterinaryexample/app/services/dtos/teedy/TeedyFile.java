package com.rebirth.veterinaryexample.app.services.dtos.teedy;

import lombok.Data;

import java.util.Date;

@Data
public class TeedyFile {

    private String id;
    private String name;
    private int version;
    private String mimetype;
    private Date create_date;
}
