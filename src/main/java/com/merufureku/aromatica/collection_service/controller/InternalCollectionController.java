package com.merufureku.aromatica.collection_service.controller;

import com.merufureku.aromatica.collection_service.dto.params.BaseParam;
import com.merufureku.aromatica.collection_service.dto.params.GetFragranceBatchParam;
import com.merufureku.aromatica.collection_service.dto.responses.BaseResponse;
import com.merufureku.aromatica.collection_service.dto.responses.CollectionsResponse;
import com.merufureku.aromatica.collection_service.dto.responses.UserCollectionsResponse;
import com.merufureku.aromatica.collection_service.services.interfaces.IInternalCollectionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("internal/collections")
public class InternalCollectionController {

    private final IInternalCollectionService internalCollectionService;

    public InternalCollectionController(IInternalCollectionService internalCollectionService) {
        this.internalCollectionService = internalCollectionService;
    }

    @PostMapping("/batch")
    @Operation(summary = "Search and get collection of all users with given fragrance IDs")
    public ResponseEntity<BaseResponse<CollectionsResponse>> getCollectionsByFragranceIds(
            @RequestBody GetFragranceBatchParam param,
            @RequestParam(required = false) Integer excludedUserId,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);
        var response = internalCollectionService.getCollections(excludedUserId, param, baseParam);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get User's Collection")
    public ResponseEntity<BaseResponse<UserCollectionsResponse>> getUserCollection(
            @PathVariable("userId") Integer userId,
            @RequestParam(required = false, defaultValue = "1") int version,
            @RequestParam(required = false, defaultValue = "") String correlationId) {

        var baseParam = new BaseParam(version, correlationId);
        var response = internalCollectionService.getUserCollections(userId, baseParam);

        return ResponseEntity.ok(response);
    }
}
