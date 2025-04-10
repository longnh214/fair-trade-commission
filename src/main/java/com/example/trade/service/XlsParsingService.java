package com.example.trade.service;

import com.example.trade.dto.FtcDataDto;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class XlsParsingService {
    @Value("${external.corporate}")
    private String corporate;

    public List<FtcDataDto> parseToDto(InputStream xlsInputStream) throws Exception {
        List<FtcDataDto> results = new ArrayList<>();
        Workbook workbook = new HSSFWorkbook(xlsInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            FtcDataDto dto = FtcDataDto.builder()
                    .commSaleNumber(getCellString(row.getCell(1)))
                    .isCorporate(convertCorporate(getCellString(row.getCell(3))))
                    .businessName(getCellString(row.getCell(4)))
                    .businessNumber(getCellString(row.getCell(5)))
                    .address(getCellString(row.getCell(6)))
                    .build();

            results.add(dto);
        }

        return results;
    }

    private String convertCorporate(String isCorporate){
        if(isCorporate.equals("Y")){
            return corporate;
        }
        return isCorporate;
    }

    private String getCellString(Cell cell) {
        if (cell == null) return "";
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }
}