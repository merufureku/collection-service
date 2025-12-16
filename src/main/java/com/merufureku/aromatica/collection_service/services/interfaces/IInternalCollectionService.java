package com.merufureku.aromatica.collection_service.services.interfaces;

import com.merufureku.aromatica.collection_service.dto.params.BaseParam;
import com.merufureku.aromatica.collection_service.dto.params.GetFragranceBatchParam;
import com.merufureku.aromatica.collection_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.collection_service.dto.responses.CollectionsResponse;
import com.merufureku.aromatica.collection_service.dto.responses.UserCollectionsResponse;

public interface IInternalCollectionService {

    BaseResponse<CollectionsResponse> getCollections(Integer excludedUserId, GetFragranceBatchParam param, BaseParam baseParam);

    BaseResponse<UserCollectionsResponse> getUserCollections(Integer userId, BaseParam baseParam);

}
