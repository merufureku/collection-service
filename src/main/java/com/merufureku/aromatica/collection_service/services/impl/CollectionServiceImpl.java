package com.merufureku.aromatica.collection_service.services.impl;

import com.merufureku.aromatica.collection_service.dao.entity.Collections;
import com.merufureku.aromatica.collection_service.dao.repository.CollectionsRepository;
import com.merufureku.aromatica.collection_service.dao.repository.FragrancesRepository;
import com.merufureku.aromatica.collection_service.dto.params.BaseParam;
import com.merufureku.aromatica.collection_service.dto.responses.AddToCollectionResponse;
import com.merufureku.aromatica.collection_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.collection_service.dto.responses.CollectionsResponse;
import com.merufureku.aromatica.collection_service.helper.ValidationHelper;
import com.merufureku.aromatica.collection_service.services.interfaces.ICollectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CollectionServiceImpl implements ICollectionService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final CollectionsRepository collectionsRepository;
    private final FragrancesRepository fragrancesRepository;
    private final ValidationHelper validationHelper;

    public CollectionServiceImpl(CollectionsRepository collectionsRepository, FragrancesRepository fragrancesRepository, ValidationHelper validationHelper) {
        this.collectionsRepository = collectionsRepository;
        this.fragrancesRepository = fragrancesRepository;
        this.validationHelper = validationHelper;
    }

    @Override
    public BaseResponse<CollectionsResponse> getUserCollections(Integer userId, BaseParam baseParam) {

        logger.info("Fetching collection for user ID: {}", userId);

        validationHelper.validateUserId(userId);

        var collections = collectionsRepository.findByUserId(userId);
        var collectionsFragranceIds = collections.stream()
                .map(Collections::getFragranceId)
                .toList();

        var fragranceList = fragrancesRepository.findAllById(collectionsFragranceIds);

        List<CollectionsResponse.FragranceDetails> fragranceDetailsList = fragranceList.stream()
                .map(fragrance -> new CollectionsResponse.FragranceDetails(
                        fragrance.getId(),
                        fragrance.getName(),
                        fragrance.getBrand(),
                        fragrance.getImageUrl()
                ))
                .toList();

        var response = new CollectionsResponse(userId, fragranceDetailsList);

        return new BaseResponse<>(HttpStatus.OK.value(), "User collection fetched successfully", response);
    }

    @Override
    public BaseResponse<AddToCollectionResponse> addToCollection(Integer userId, Long fragranceId, BaseParam baseParam) {

        logger.info("Adding fragrance ID: {} to user ID: {} collection", fragranceId, userId);

        validationHelper.validateUserAndFragranceIds(userId, fragranceId);
        validationHelper.validateIfCollectionExists(userId, fragranceId);

        var newCollection = Collections.builder()
                .userId(userId)
                .fragranceId(fragranceId)
                .addedAt(LocalDate.now())
                .build();

        var savedCollection = collectionsRepository.save(newCollection);

        return new BaseResponse<>(HttpStatus.CREATED.value(), "Fragrance added to collection successfully",
                new AddToCollectionResponse(savedCollection.getId(), userId, savedCollection.getAddedAt()));
    }

    @Override
    public void removeFromCollection(Integer userId, Long fragranceId, BaseParam baseParam) {

        logger.info("Removing fragrance ID: {} from user ID: {} collection", fragranceId, userId);

        validationHelper.validateUserAndFragranceIds(userId, fragranceId);

        collectionsRepository.deleteByUserIdAndFragranceId(userId, fragranceId);

        logger.info("Fragrance with ID: {} removed from user ID: {} collection", fragranceId, userId);
    }
}
