package com.merufureku.aromatica.collection_service.helper;

import com.merufureku.aromatica.collection_service.dao.repository.CollectionsRepository;
import com.merufureku.aromatica.collection_service.dao.repository.FragrancesRepository;
import com.merufureku.aromatica.collection_service.dao.repository.UsersRepository;
import com.merufureku.aromatica.collection_service.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import static com.merufureku.aromatica.collection_service.enums.CustomStatusEnums.*;

@Component
public class ValidationHelper {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final UsersRepository usersRepository;
    private final CollectionsRepository collectionsRepository;
    private final FragrancesRepository fragrancesRepository;


    public ValidationHelper(UsersRepository usersRepository, CollectionsRepository collectionsRepository, FragrancesRepository fragrancesRepository) {
        this.usersRepository = usersRepository;
        this.collectionsRepository = collectionsRepository;
        this.fragrancesRepository = fragrancesRepository;
    }

    public void validateUserAndFragranceIds(Integer userId, Long fragranceId) {

        logger.info("Validating user ID: {} and fragrance ID: {}", userId, fragranceId);

        validateUserId(userId);
        validateFragranceId(fragranceId);
    }

    public void validateUserId(Integer userId) {
        if (!usersRepository.existsById(userId)) {
            logger.error("User with ID: {} not found", userId);
            throw new ServiceException(NO_USER_FOUND);
        }
    }

    public void validateFragranceId(Long fragranceId) {
        if (!fragrancesRepository.existsById(fragranceId)) {
            logger.error("Fragrance with ID: {} not found", fragranceId);
            throw new ServiceException(FRAGRANCE_NOT_FOUND);
        }
    }

    public void validateIfCollectionExists(Integer userId, Long fragranceId) {
        if (collectionsRepository.existsByUserIdAndFragranceId(userId, fragranceId)){
            logger.error("Fragrance with ID: {} already exists in user ID: {} collection", fragranceId, userId);
            throw new ServiceException(FRAGRANCE_ALREADY_EXIST);
        }
    }
}
