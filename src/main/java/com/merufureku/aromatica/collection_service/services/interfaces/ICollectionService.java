package com.merufureku.aromatica.collection_service.services.interfaces;

import com.merufureku.aromatica.collection_service.dto.params.BaseParam;
import com.merufureku.aromatica.collection_service.dto.responses.AddToCollectionResponse;
import com.merufureku.aromatica.collection_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.collection_service.dto.responses.CollectionsResponse;

public interface ICollectionService {

    BaseResponse<CollectionsResponse> getUserCollections(Integer userId, BaseParam baseParam);

    BaseResponse<AddToCollectionResponse> addToCollection(Integer userId, Long fragranceId, BaseParam baseParam);

    void removeFromCollection(Integer userId, Long fragranceId, BaseParam baseParam);
}
