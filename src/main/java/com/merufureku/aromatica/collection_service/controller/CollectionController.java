package com.merufureku.aromatica.collection_service.controller;

import com.merufureku.aromatica.collection_service.dto.params.BaseParam;
import com.merufureku.aromatica.collection_service.dto.responses.AddToCollectionResponse;
import com.merufureku.aromatica.collection_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.collection_service.dto.responses.CollectionsResponse;
import com.merufureku.aromatica.collection_service.services.interfaces.ICollectionService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collections")
public class CollectionController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final ICollectionService collectionService;

    public CollectionController(ICollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    @Operation(summary = "Get User's Collection")
    public ResponseEntity<BaseResponse<CollectionsResponse>> getUserCollection(@RequestParam(required = false, defaultValue = "1") int version,
                                                                               @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);
        var response = collectionService.getUserCollections(getUserId(), baseParam);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{fragranceId}")
    @Operation(summary = "Add New Fragrance to User's Collection")
    public ResponseEntity<BaseResponse<AddToCollectionResponse>> insertToCollection(@PathVariable("fragranceId") Long fragranceId,
                                                                                    @RequestParam(required = false, defaultValue = "1") int version,
                                                                                    @RequestParam(required = false, defaultValue = "") String correlationId) {
        var baseParam = new BaseParam(version, correlationId);
        var response = collectionService.addToCollection(getUserId(), fragranceId, baseParam);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{fragranceId}")
    @Operation(summary = "Delete Fragrance from User's Collection")
    public ResponseEntity<Void> removeFromCollection(@PathVariable("fragranceId") Long fragranceId,
                                                     @RequestParam(required = false, defaultValue = "1") int version,
                                                     @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);
        collectionService.removeFromCollection(getUserId(), fragranceId, baseParam);

        return ResponseEntity.noContent().build();
    }

    private Integer getUserId(){

        return (Integer) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
