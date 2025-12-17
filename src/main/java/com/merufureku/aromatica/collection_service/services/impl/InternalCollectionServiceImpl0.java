package com.merufureku.aromatica.collection_service.services.impl;

import com.merufureku.aromatica.collection_service.dto.params.BaseParam;
import com.merufureku.aromatica.collection_service.dto.params.GetFragranceBatchParam;
import com.merufureku.aromatica.collection_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.collection_service.dto.responses.CollectionsResponse;
import com.merufureku.aromatica.collection_service.dto.responses.UserCollectionsResponse;
import com.merufureku.aromatica.collection_service.services.interfaces.IInternalCollectionService;
import org.springframework.stereotype.Service;

@Service
public class InternalCollectionServiceImpl0 implements IInternalCollectionService {

    @Override
    public BaseResponse<CollectionsResponse> getCollections(Integer excludedUserId, GetFragranceBatchParam param, BaseParam baseParam) {
        return null;
    }

    @Override
    public BaseResponse<UserCollectionsResponse> getUserCollections(Integer userId, BaseParam baseParam) {
        return null;
    }
}
