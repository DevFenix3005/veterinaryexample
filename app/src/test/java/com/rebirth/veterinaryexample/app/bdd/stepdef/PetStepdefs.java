package com.rebirth.veterinaryexample.app.bdd.stepdef;

import com.rebirth.veterinaryexample.app.services.PetService;
import com.rebirth.veterinaryexample.app.services.dtos.pets.PetBase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Assertions;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class PetStepdefs {

    @Autowired
    private PetService petService;

    private UUID currentDogUUID;
    private String currentDogName;
    private String currentDogBreed;

    private AccessToken accessToken;

    @Given("The identifier of the dog that is {string}")
    public void theIdentifierOfTheDogThatIs(String uuid) {
        this.currentDogUUID = UUID.fromString(uuid);
        AccessToken.Access access = new AccessToken.Access().addRole("Member");
        this.accessToken = new AccessToken();
        this.accessToken.setSubject("086016ed-038e-4526-8d22-9787f88b713e");
        this.accessToken.setResourceAccess(Maps.newHashMap("springbootapplication", access));
    }

    @When("I check with the PetService")
    public void iCheckWithThePetService() {
        currentDogName = petService.read(currentDogUUID, this.accessToken)
                .map(PetBase::getName)
                .orElseThrow(() -> new RuntimeException("Haskell not found"));
    }

    @When("I check the breed with the PetService")
    public void iCheckTheBreedWithThePetService() {
        currentDogBreed = petService.read(this.currentDogUUID, this.accessToken)
                .map(PetBase.PetDto::getBreed)
                .orElseThrow(() -> new RuntimeException("Haskell not found"));
    }

    @Then("the dog that returns its name must be {string}.")
    public void theDogThatReturnsItsNameMustBe(String name) {
        Assertions.assertEquals(name, currentDogName);
    }

    @Then("ths breed of Haskell will be {string}")
    public void thsBreedOfHaskellWillBe(String breed) {
        Assertions.assertEquals(breed, currentDogBreed);
    }

    @Then("the dog that returns its name must not be {string}.")
    public void theDogThatReturnsItsNameMustNotBe(String name) {
        Assertions.assertNotEquals(name, currentDogName);
    }


}
