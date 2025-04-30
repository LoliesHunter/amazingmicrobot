package com.example.microbotProcessor.controller;

import com.example.microbotProcessor.POI.ExcelGeneratorService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Produces;
import lombok.RequiredArgsConstructor;
import io.micronaut.context.annotation.Value;
import lombok.extern.slf4j.Slf4j;

@Controller("/export")
@RequiredArgsConstructor
@Slf4j
public class ExportController {

    private final ExcelGeneratorService excelGeneratorService;


    @Value("${export.secret-token}")
    private String secretToken;

    @Get
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public HttpResponse<byte[]> exportExcel(@Header("Authorization") String authorizationHeader){
        if (!("Bearer " + secretToken).equals(authorizationHeader)) {
            return HttpResponse.unauthorized();
        }

        try {
            byte[] excelData = excelGeneratorService.generateExcel();
            return HttpResponse.ok(excelData)
                    .header("Content-Disposition", "attachment; filename=messages.xlsx");
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            return HttpResponse.serverError();
        }
    }

}
