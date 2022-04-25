package com.rebirth.veterinaryexample.app.services.annotations;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "id", ignore = true)
@Mapping(target = "createAt", ignore = true)
@Mapping(target = "updateAt", ignore = true)
@Mapping(target = "version", ignore = true)
@Mapping(target = "active", ignore = true)
public @interface ToEntity {
}

