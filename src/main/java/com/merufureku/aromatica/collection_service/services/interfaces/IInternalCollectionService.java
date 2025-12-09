package com.merufureku.aromatica.collection_service.services.interfaces;

import com.merufureku.aromatica.collection_service.dto.params.BaseParam;
import com.merufureku.aromatica.collection_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.collection_service.dto.responses.CollectionsResponse;

public interface IInternalCollectionService {

    BaseResponse<CollectionsResponse> getUserCollections(Integer userId, BaseParam baseParam);

}
