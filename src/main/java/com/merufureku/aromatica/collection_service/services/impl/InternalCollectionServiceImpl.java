package com.merufureku.aromatica.collection_service.services.impl;

import com.merufureku.aromatica.collection_service.dao.entity.Collections;
import com.merufureku.aromatica.collection_service.dao.repository.CollectionsRepository;
import com.merufureku.aromatica.collection_service.dao.repository.FragrancesRepository;
import com.merufureku.aromatica.collection_service.dto.params.BaseParam;
import com.merufureku.aromatica.collection_service.dto.params.GetFragranceBatchParam;
import com.merufureku.aromatica.collection_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.collection_service.dto.responses.CollectionsResponse;
import com.merufureku.aromatica.collection_service.dto.responses.UserCollectionsResponse;
import com.merufureku.aromatica.collection_service.helper.ValidationHelper;
import com.merufureku.aromatica.collection_service.services.interfaces.IInternalCollectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class InternalCollectionServiceImpl implements IInternalCollectionService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final CollectionsRepository collectionsRepository;
    private final FragrancesRepository fragrancesRepository;
    private final ValidationHelper validationHelper;

    public InternalCollectionServiceImpl(CollectionsRepository collectionsRepository, FragrancesRepository fragrancesRepository, ValidationHelper validationHelper) {
        this.collectionsRepository = collectionsRepository;
        this.fragrancesRepository = fragrancesRepository;
        this.validationHelper = validationHelper;
    }

    @Override
    public BaseResponse<CollectionsResponse> getCollections(Integer excludedUserId, GetFragranceBatchParam param, BaseParam baseParam) {

        logger.info("Fetching collections for fragrance IDs: {}", param.fragranceIds());

        var collections = collectionsRepository.findAllByFragranceIdInExcludingUser(param.fragranceIds(), excludedUserId);
        var userIds = collections.stream().map(Collections::getUserId).collect(Collectors.toSet());

        var allCollections = collectionsRepository.findAllByUserIdIn(userIds);

        var fragranceDetails = allCollections.stream()
                .map(CollectionsResponse.FragranceDetails::new).toList();

        var response = new CollectionsResponse(fragranceDetails);

        logger.info("Fetched {} collection entries for {} users", allCollections.size(), userIds.size());

        return new BaseResponse<>(HttpStatus.OK.value(), "Collections fetched successfully", response);
    }

    @Override
    public BaseResponse<UserCollectionsResponse> getUserCollections(Integer userId, BaseParam baseParam) {

        logger.info("Fetching collection for user ID: {}", userId);

        validationHelper.validateUserId(userId);

        var collections = collectionsRepository.findByUserId(userId);
        var collectionsFragranceIds = collections.stream()
                .map(Collections::getFragranceId)
                .toList();

        var fragranceList = fragrancesRepository.findAllById(collectionsFragranceIds);

        var fragranceDetailsList = fragranceList.stream()
                .map(fragrance -> new UserCollectionsResponse.FragranceDetails(
                        fragrance.getId(),
                        fragrance.getName(),
                        fragrance.getBrand(),
                        fragrance.getImageUrl()
                ))
                .toList();

        var response = new UserCollectionsResponse(userId, fragranceDetailsList);

        return new BaseResponse<>(HttpStatus.OK.value(), "User collection fetched successfully", response);
    }
}
