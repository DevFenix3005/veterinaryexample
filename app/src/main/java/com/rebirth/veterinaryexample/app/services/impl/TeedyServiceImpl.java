package com.rebirth.veterinaryexample.app.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebirth.veterinaryexample.app.domain.models.Pet;
import com.rebirth.veterinaryexample.app.services.TeedyService;
import com.rebirth.veterinaryexample.app.services.dtos.teedy.*;
import kong.unirest.ContentType;
import kong.unirest.Cookie;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestInstance;
import kong.unirest.jackson.JacksonObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TeedyServiceImpl implements TeedyService {

    private final ObjectMapper objectMapper;

    private final UnirestInstance unirestInstance;

    @Value("${teedy.host}")
    private String host;

    @Value("${teedy.username}")
    private String username;

    @Value("${teedy.password}")
    private String password;

    @Value("${teedy.codettop}")
    private String codettop;

    @PostConstruct
    @Override
    public void initTeedy() {
        log.info(this.host);
        unirestInstance.config()
                .defaultBaseUrl(host)
                .setObjectMapper(new JacksonObjectMapper(objectMapper));
        this.getTeedyAuthCookie();
    }

    @PreDestroy
    @Override
    public void tearDownTeedy() {
        HttpResponse<String> response = unirestInstance.post("/api/user/logout").asString();
        log.info("Response Code:{}", response.getStatus());
        log.info("Response Body:{}", response.getBody());
        if (response.getStatus() == 200) {
            log.info("Logout success!");
            unirestInstance.shutDown();
        } else {
            throw new RuntimeException("No se logro terminar la sesion");
        }
    }

    private void getTeedyAuthCookie() {
        HttpResponse<String> response = unirestInstance.post("/api/user/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .field("username", username)
                .field("password", password)
                .field("remember", "true")
                .asString();
        log.info("Response Code:{}", response.getStatus());
        log.info("Response Body:{}", response.getBody());
        if (response.getStatus() == 200) {
            log.info("Login success!");
            Cookie authToken = response.getCookies().getNamed("auth_token");
            unirestInstance.config().addDefaultCookie(authToken);
        } else {
            throw new RuntimeException("No se logro iniciar sesion");
        }
    }

    @Override
    public RawAndMeta getImage(String uuid, String size) {
        ListOfTeedyFiles versiones = getMetadataById(uuid);
        TeedyFile version = versiones.getFiles().get(0);
        HttpResponse<byte[]> response = unirestInstance.get("/api/file/{uuid}/data")
                .routeParam("uuid", uuid)
                .queryString("size", size)
                .asBytes();
        if (response.isSuccess()) return new RawAndMeta(response.getBody(), version);
        else return new RawAndMeta(new byte[0], version);
    }

    @Override
    public DocumentCreate createDocument(Pet pet) {
        String title = "Expediente de " + pet.getName();
        String description = "Expediente de la mascota " + pet.getName() +
                "\nraza: " + pet.getBreed().getName() +
                "\nsexo: " + pet.getGender().name();

        HttpResponse<DocumentCreate> response = unirestInstance.put("/api/document")
                .field("title", title)
                .field("description", description)
                .field("language", "spa")
                .asObject(DocumentCreate.class);
        if (response.isSuccess()) return response.getBody();
        else return null;
    }

    @Override
    public FileCreate appendImage2Document(DocumentCreate documentCreate, MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        HttpResponse<FileCreate> response = unirestInstance.put("/api/file")
                .field("id", documentCreate.getId())
                .field("file", inputStream, ContentType.create(file.getContentType()), file.getOriginalFilename())
                .asObject(FileCreate.class);
        if (response.isSuccess()) return response.getBody();
        else return null;
    }

    @Override
    public ListOfTeedyFiles getMetadataById(String uuid) {
        HttpResponse<ListOfTeedyFiles> response = unirestInstance.get("/api/file/{uuid}/versions")
                .routeParam("uuid", uuid)
                .asObject(ListOfTeedyFiles.class);
        if (response.isSuccess()) {
            return response.getBody();
        } else {
            throw new RuntimeException("No es accesible este endpoint");
        }
    }


}
