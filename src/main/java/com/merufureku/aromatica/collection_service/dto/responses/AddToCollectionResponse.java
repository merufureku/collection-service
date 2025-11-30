package com.merufureku.aromatica.collection_service.dto.responses;

import java.time.LocalDate;

public record AddToCollectionResponse(Integer collectionId, Integer userId, LocalDate insertedDate) {}
