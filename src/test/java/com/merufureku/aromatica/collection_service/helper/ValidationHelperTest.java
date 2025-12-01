package com.merufureku.aromatica.collection_service.helper;

import com.merufureku.aromatica.collection_service.dao.repository.CollectionsRepository;
import com.merufureku.aromatica.collection_service.dao.repository.FragrancesRepository;
import com.merufureku.aromatica.collection_service.dao.repository.UsersRepository;
import com.merufureku.aromatica.collection_service.exceptions.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.merufureku.aromatica.collection_service.enums.CustomStatusEnums.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValidationHelperTest {

    @InjectMocks
    private ValidationHelper validationHelper;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private CollectionsRepository collectionsRepository;

    @Mock
    private FragrancesRepository fragrancesRepository;

    private static final Integer USER_ID = 1;
    private static final Long FRAGRANCE_ID = 1L;

    @Test
    public void testValidateUserAndFragranceIds_whenValid_thenNoException() {

        when(usersRepository.existsById(USER_ID)).thenReturn(true);
        when(fragrancesRepository.existsById(FRAGRANCE_ID)).thenReturn(true);

        assertDoesNotThrow(() -> validationHelper
                .validateUserAndFragranceIds(USER_ID, FRAGRANCE_ID));

        verify(usersRepository, times(1)).existsById(USER_ID);
        verify(fragrancesRepository, times(1)).existsById(FRAGRANCE_ID);
    }

    @Test
    public void testValidateUserAndFragranceIds_whenUserNotFound_thenThrowException() {

        when(usersRepository.existsById(USER_ID)).thenReturn(false);

        ServiceException exception = assertThrows(ServiceException.class, () ->
                validationHelper.validateUserAndFragranceIds(USER_ID, FRAGRANCE_ID));

        assertEquals(NO_USER_FOUND, exception.getCustomStatusEnums());
    }

    @Test
    public void testValidateUserAndFragranceIds_whenFragranceNotFound_thenThrowException() {

        when(usersRepository.existsById(USER_ID)).thenReturn(true);
        when(fragrancesRepository.existsById(FRAGRANCE_ID)).thenReturn(false);

        ServiceException exception = assertThrows(ServiceException.class, () ->
                validationHelper.validateUserAndFragranceIds(USER_ID, FRAGRANCE_ID));

        assertEquals(FRAGRANCE_NOT_FOUND, exception.getCustomStatusEnums());

        verify(usersRepository, times(1)).existsById(USER_ID);
        verify(fragrancesRepository, times(1)).existsById(FRAGRANCE_ID);
    }

    @Test
    public void testValidateUserId_whenUserExists_thenNoException() {

        when(usersRepository.existsById(USER_ID)).thenReturn(true);

        assertDoesNotThrow(() -> validationHelper.validateUserId(USER_ID));

        verify(usersRepository, times(1)).existsById(USER_ID);
    }

    @Test
    public void testValidateUserId_whenUserNotExists_thenThrowException() {

        when(usersRepository.existsById(USER_ID)).thenReturn(false);

        ServiceException exception = assertThrows(ServiceException.class, () ->
                validationHelper.validateUserId(USER_ID));

        assertEquals(NO_USER_FOUND, exception.getCustomStatusEnums());

        verify(usersRepository, times(1)).existsById(USER_ID);
    }

    @Test
    public void testValidateFragranceId_whenFragranceExists_thenNoException() {

        when(fragrancesRepository.existsById(FRAGRANCE_ID)).thenReturn(true);

        assertDoesNotThrow(() -> validationHelper.validateFragranceId(FRAGRANCE_ID));

        verify(fragrancesRepository, times(1)).existsById(FRAGRANCE_ID);
    }

    @Test
    public void testValidateFragranceId_whenFragranceNotExists_thenThrowException() {

        when(fragrancesRepository.existsById(FRAGRANCE_ID)).thenReturn(false);

        ServiceException exception = assertThrows(ServiceException.class, () ->
                validationHelper.validateFragranceId(FRAGRANCE_ID));

        assertEquals(FRAGRANCE_NOT_FOUND, exception.getCustomStatusEnums());

        verify(fragrancesRepository, times(1)).existsById(FRAGRANCE_ID);
    }

    @Test
    public void testValidateIfCollectionExists_whenCollectionNotExists_thenNoException() {

        when(collectionsRepository.existsByUserIdAndFragranceId(USER_ID, FRAGRANCE_ID))
                .thenReturn(false);

        assertDoesNotThrow(() -> validationHelper
                .validateIfCollectionExists(USER_ID, FRAGRANCE_ID));

        verify(collectionsRepository, times(1))
                .existsByUserIdAndFragranceId(USER_ID, FRAGRANCE_ID);
    }

    @Test
    public void testValidateIfCollectionExists_whenCollectionExists_thenThrowException() {

        when(collectionsRepository.existsByUserIdAndFragranceId(USER_ID, FRAGRANCE_ID))
                .thenReturn(true);

        ServiceException exception = assertThrows(ServiceException.class, () ->
                validationHelper.validateIfCollectionExists(USER_ID, FRAGRANCE_ID));

        assertEquals(FRAGRANCE_ALREADY_EXIST, exception.getCustomStatusEnums());

        verify(collectionsRepository, times(1))
                .existsByUserIdAndFragranceId(USER_ID, FRAGRANCE_ID);
    }
}
