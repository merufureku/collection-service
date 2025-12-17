package com.merufureku.aromatica.collection_service.services.factory;

import com.merufureku.aromatica.collection_service.services.impl.CollectionServiceImpl0;
import com.merufureku.aromatica.collection_service.services.impl.CollectionServiceImpl1;
import com.merufureku.aromatica.collection_service.services.interfaces.ICollectionService;
import org.springframework.stereotype.Component;

@Component
public class CollectionServiceFactory {

    private final CollectionServiceImpl0 collectionServiceImpl0;
    private final CollectionServiceImpl1 collectionServiceImpl1;

    public CollectionServiceFactory(CollectionServiceImpl0 collectionServiceImpl0, CollectionServiceImpl1 collectionServiceImpl1) {
        this.collectionServiceImpl0 = collectionServiceImpl0;
        this.collectionServiceImpl1 = collectionServiceImpl1;
    }

    public ICollectionService getService(int version) {
        return switch (version) {
            case 0 -> collectionServiceImpl0;
            case 1 -> collectionServiceImpl1;
            default -> throw new IllegalArgumentException("Unsupported service version: " + version);
        };
    }
}
