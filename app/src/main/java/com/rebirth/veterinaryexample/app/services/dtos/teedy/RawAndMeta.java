package com.rebirth.veterinaryexample.app.services.dtos.teedy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RawAndMeta {

    private byte[] content;
    private TeedyFile metadata;
}
