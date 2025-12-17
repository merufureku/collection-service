package com.merufureku.aromatica.collection_service.services.factory;

import com.merufureku.aromatica.collection_service.services.impl.InternalCollectionServiceImpl0;
import com.merufureku.aromatica.collection_service.services.impl.InternalCollectionServiceImpl1;
import com.merufureku.aromatica.collection_service.services.interfaces.IInternalCollectionService;
import org.springframework.stereotype.Component;

@Component
public class InternalCollectionServiceFactory {

    private final InternalCollectionServiceImpl0 internalCollectionServiceImpl0;
    private final InternalCollectionServiceImpl1 internalCollectionServiceImpl1;

    public InternalCollectionServiceFactory(InternalCollectionServiceImpl0 internalCollectionServiceImpl0, InternalCollectionServiceImpl1 internalCollectionServiceImpl1) {
        this.internalCollectionServiceImpl0 = internalCollectionServiceImpl0;
        this.internalCollectionServiceImpl1 = internalCollectionServiceImpl1;
    }

    public IInternalCollectionService getService(int version) {
        return switch (version) {
            case 0 -> internalCollectionServiceImpl0;
            case 1 -> internalCollectionServiceImpl1;
            default -> throw new IllegalArgumentException("Unsupported service version: " + version);
        };
    }
}
