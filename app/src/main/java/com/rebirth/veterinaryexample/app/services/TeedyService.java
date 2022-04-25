package com.rebirth.veterinaryexample.app.services;

import com.rebirth.veterinaryexample.app.domain.models.Pet;
import com.rebirth.veterinaryexample.app.services.dtos.teedy.DocumentCreate;
import com.rebirth.veterinaryexample.app.services.dtos.teedy.FileCreate;
import com.rebirth.veterinaryexample.app.services.dtos.teedy.ListOfTeedyFiles;
import com.rebirth.veterinaryexample.app.services.dtos.teedy.RawAndMeta;
import org.springframework.web.multipart.MultipartFile;

public interface TeedyService {

    void initTeedy();

    void tearDownTeedy();

    RawAndMeta getImage(String uuid, String size);

    DocumentCreate createDocument(Pet pet);

    FileCreate appendImage2Document(DocumentCreate documentCreate, MultipartFile file) throws Exception;

    ListOfTeedyFiles getMetadataById(String uuid);

}
