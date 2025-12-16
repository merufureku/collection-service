package com.merufureku.aromatica.collection_service.services;

import com.merufureku.aromatica.collection_service.dao.entity.Collections;
import com.merufureku.aromatica.collection_service.dao.entity.Fragrance;
import com.merufureku.aromatica.collection_service.dao.repository.CollectionsRepository;
import com.merufureku.aromatica.collection_service.dao.repository.FragrancesRepository;
import com.merufureku.aromatica.collection_service.dto.params.BaseParam;
import com.merufureku.aromatica.collection_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.collection_service.dto.responses.UserCollectionsResponse;
import com.merufureku.aromatica.collection_service.exceptions.ServiceException;
import com.merufureku.aromatica.collection_service.helper.ValidationHelper;
import com.merufureku.aromatica.collection_service.services.impl.InternalCollectionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.merufureku.aromatica.collection_service.enums.CustomStatusEnums.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InternalCollectionServiceImplTest {

    @InjectMocks
    private InternalCollectionServiceImpl internalCollectionServiceImpl;

    @Mock
    private CollectionsRepository collectionsRepository;

    @Mock
    private FragrancesRepository fragrancesRepository;

    @Mock
    private ValidationHelper validationHelper;

    private static final Integer USER_ID = 1;

    private BaseParam baseParam;
    private List<Collections> collections;
    private List<Fragrance> fragrances;

    @BeforeEach
    void setUp() {
        baseParam = new BaseParam(1, "correlation-id");

        collections = List.of(
                Collections.builder()
                        .id(1)
                        .userId(USER_ID)
                        .fragranceId(1L)
                        .build(),
                Collections.builder()
                        .id(2)
                        .userId(USER_ID)
                        .fragranceId(2L)
                        .build());

        fragrances = List.of(
                Fragrance.builder()
                        .id(1L)
                        .name("Fragrance 1")
                        .brand("Brand A")
                        .imageUrl("/img/1.png")
                        .build(),
                Fragrance.builder()
                        .id(2L)
                        .name("Fragrance 2")
                        .brand("Brand B")
                        .imageUrl("/img/2.png")
                        .build());
    }

    @Test
    public void testGetUserCollections_whenExists_thenReturnCollection() {

        doNothing().when(validationHelper).validateUserId(USER_ID);
        when(collectionsRepository.findByUserId(USER_ID)).thenReturn(collections);
        when(fragrancesRepository.findAllById(List.of(1L, 2L))).thenReturn(fragrances);

        BaseResponse<UserCollectionsResponse> response = internalCollectionServiceImpl
                .getUserCollections(USER_ID, baseParam);

        assertEquals(200, response.status());
        assertEquals("User collection fetched successfully", response.message());
        assertEquals(2, response.data().fragrances().size());

        verify(validationHelper, times(1)).validateUserId(USER_ID);
        verify(collectionsRepository, times(1)).findByUserId(USER_ID);
        verify(fragrancesRepository, times(1)).findAllById(List.of(1L, 2L));
    }

    @Test
    public void testGetUserCollections_whenUserNotExists_thenThrowException() {

        doThrow(new ServiceException(NO_USER_FOUND))
                .when(validationHelper).validateUserId(USER_ID);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> internalCollectionServiceImpl.getUserCollections(USER_ID, baseParam));

        assertEquals(NO_USER_FOUND, exception.getCustomStatusEnums());
    }

    @Test
    public void testGetUserCollections_whenUserHasNoCollection_thenReturnEmpty() {

        doNothing().when(validationHelper).validateUserId(USER_ID);
        when(collectionsRepository.findByUserId(USER_ID)).thenReturn(new ArrayList<>());
        when(fragrancesRepository.findAllById(new ArrayList<>())).thenReturn(new ArrayList<>());

        BaseResponse<UserCollectionsResponse> response = internalCollectionServiceImpl
                .getUserCollections(USER_ID, baseParam);

        assertEquals(200, response.status());
        assertEquals("User collection fetched successfully", response.message());
        assertEquals(0, response.data().fragrances().size());

        verify(validationHelper, times(1)).validateUserId(USER_ID);
        verify(collectionsRepository, times(1)).findByUserId(USER_ID);
        verify(fragrancesRepository, times(1)).findAllById(new ArrayList<>());
    }
}

