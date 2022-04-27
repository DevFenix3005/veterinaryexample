package com.rebirth.veterinaryexample.app.bdd.stepdef;

import com.rebirth.veterinaryexample.app.services.BreedService;
import com.rebirth.veterinaryexample.app.services.dtos.breeds.BreedBase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class BreedStepdefs {

    @Autowired
    private BreedService breedService;
    private UUID breedUUID;
    private String breedName;

    @Given("I have Nordic dogs and I have their breed UUIDs which are {string}")
    public void iHaveNordicDogsAndIHaveTheirBreedUUIDsWhichAre(String uuid) {
        this.breedUUID = UUID.fromString(uuid);
    }

    @When("I am consulting in BreedService")
    public void iAmConsultingInBreedService() {
        this.breedName = breedService.read(breedUUID).map(BreedBase::getName)
                .orElseThrow(() -> new RuntimeException("This UUID not exists in the database " + breedUUID.toString()));
    }

    @Then("i should get the breed {string}")
    public void iShouldGetTheBreed(String breedName) {
        Assertions.assertEquals(breedName, this.breedName);
    }
}
