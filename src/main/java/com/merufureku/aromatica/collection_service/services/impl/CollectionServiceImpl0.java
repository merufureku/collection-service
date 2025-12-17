package com.merufureku.aromatica.collection_service.services.impl;

import com.merufureku.aromatica.collection_service.dto.params.BaseParam;
import com.merufureku.aromatica.collection_service.dto.responses.AddToCollectionResponse;
import com.merufureku.aromatica.collection_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.collection_service.dto.responses.UserCollectionsResponse;
import com.merufureku.aromatica.collection_service.services.interfaces.ICollectionService;
import org.springframework.stereotype.Service;

@Service
public class CollectionServiceImpl0 implements ICollectionService {

    @Override
    public BaseResponse<UserCollectionsResponse> getUserCollections(Integer userId, BaseParam baseParam) {
        return null;
    }

    @Override
    public BaseResponse<AddToCollectionResponse> addToCollection(Integer userId, Long fragranceId, BaseParam baseParam) {
        return null;
    }

    @Override
    public void removeFromCollection(Integer userId, Long fragranceId, BaseParam baseParam) {

    }
}
