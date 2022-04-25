package com.rebirth.veterinaryexample.app.services.dtos.teedy;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileCreate implements Serializable {

    private String status;
    private String id;
    private int size;

}
