package com.merufureku.aromatica.collection_service.dto.responses;

import com.merufureku.aromatica.collection_service.dao.entity.Collections;

import java.util.List;

public record CollectionsResponse(List<FragranceDetails> fragrances) {


    public record FragranceDetails(Integer userId, Long fragranceId) {

        public FragranceDetails(Collections collections){
            this(collections.getUserId(), collections.getFragranceId());
        }
    }

}
