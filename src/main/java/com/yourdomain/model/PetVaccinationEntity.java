package com.yourdomain.model;

/**
 * Java Record (5 attributes).
 * species: DOG, CAT or BIRD
 * vaccine: RABIES, DHPP or FVRCP
 */
public record PetVaccinationEntity(int id, String petName, String species, String vaccine, double fee) {
}
