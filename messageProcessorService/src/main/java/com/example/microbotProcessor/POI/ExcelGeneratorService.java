package com.example.microbotProcessor.POI;


import com.example.microbotProcessor.arango.MessageDTO;
import com.example.microbotProcessor.arango.MessageService;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

@Singleton
@RequiredArgsConstructor
@Slf4j
public class ExcelGeneratorService {

    private final MessageService messageService;

    public byte[] generateExcel() throws Exception {

        List<MessageDTO> messages = messageService.getLimitedSortedByTimeMessages(100);

        if (messages == null || messages.isEmpty()) {
            messages = List.of(); // пустой список, чтобы дальше обработать
        }

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Сообщения");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Время");
            headerRow.createCell(1).setCellValue("Текст Сообщения");

            int rowIndex = 1;
            for (MessageDTO message : messages) {
                Row row = sheet.createRow(rowIndex);
                row.createCell(0).setCellValue(message.getTimestamp());
                row.createCell(1).setCellValue(message.getContent());
                rowIndex++;
            }
            workbook.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            return null;
        }
    }
}
