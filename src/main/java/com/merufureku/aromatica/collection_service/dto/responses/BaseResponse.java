package com.merufureku.aromatica.collection_service.dto.responses;

public record BaseResponse<T>(int status, String message, T data){}
