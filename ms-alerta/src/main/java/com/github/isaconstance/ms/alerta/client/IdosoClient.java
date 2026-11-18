package com.github.isaconstance.ms.alerta.client;

import com.github.isaconstance.ms.alerta.client.dto.IdosoResponseDTO;
import com.github.isaconstance.ms.alerta.client.dto.StatusIdoso;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("ms-idoso")
public interface IdosoClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/idosos/{id}")
    IdosoResponseDTO getIdosoById(@PathVariable("id") Long id);

    @RequestMapping(method = RequestMethod.PUT, value = "/api/v1/idosos/{id}/status")
    IdosoResponseDTO updateStatus(@PathVariable("id") Long id,
                                  @RequestParam("status") StatusIdoso status);
}