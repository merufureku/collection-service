package com.merufureku.aromatica.collection_service.dto.responses;

import java.util.List;

public record CollectionsResponse(Integer userId, List<FragranceDetails> items) {


    public record FragranceDetails(Long fragranceId, String name, String brand,
                                   String imageUrl) {}

}
