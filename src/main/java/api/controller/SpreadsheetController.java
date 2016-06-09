package api.controller;

import api.model.Items;
import api.service.ItemsApi;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by matthew on 04.05.16.
 */
@RestController
@RequestMapping("spreadsheet")
public class SpreadsheetController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    ItemsApi service;

    @RequestMapping(value = {"", "/"},
      method = RequestMethod.GET,
      produces = {
        MediaType.APPLICATION_OCTET_STREAM_VALUE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      })
    public ResponseEntity exportSheet() {
        XSSFWorkbook wb = new XSSFWorkbook();
        if (wb != null) {
            CellStyle style = wb.createCellStyle();
            style.setAlignment(CellStyle.ALIGN_CENTER);
            List<Items.Item> items = new ArrayList<>(service.items().getItems());
            Sheet sheet = wb.createSheet("items");
            sheet.createFreezePane(0,1);
            int rowNum = 0;
            Row row = sheet.createRow(rowNum);
            for (int i = 0; i < (6 + items.get(0).getValues().size()); i++) {
                Cell cell = row.createCell(i);
                cell.setAsActiveCell();
                cell.setCellStyle(style);
                switch (i) {
                    case 0:
                        cell.setCellValue("Id");
                        break;
                    case 1:
                        cell.setCellValue("Name");
                        break;
                    case 2:
                        cell.setCellValue("Description");
                        break;
                    case 3:
                        cell.setCellValue("Content");
                        break;
                    case 4:
                        cell.setCellValue("Modified Date");
                        break;
                    case 5:
                        cell.setCellValue("CreationDate");
                        break;
                    default:
                        cell.setCellValue("Values");
                }
            }
            for (Items.Item item : items) {
                rowNum = rowNum + 2;
                row = sheet.createRow(rowNum);
                for (int i = 0; i < (6 + item.getValues().keySet().size()); i++) {
                    Cell cell = row.createCell(i);
                    cell.setAsActiveCell();
                    switch (i) {
                        case 0:
                            cell.setCellValue(item.getId());
                            break;
                        case 1:
                            cell.setCellValue(item.getName());
                            cell.setCellStyle(style);
                            break;
                        case 2:
                            cell.setCellValue(item.getDescription());
                            break;
                        case 3:
                            cell.setCellValue(item.getContent());
                            break;
                        case 4:
                            cell.setCellValue(item.getModifiedDate());
                            break;
                        case 5:
                            cell.setCellValue(item.getCreationDate());
                            break;
                        default:
                            List<String> keys = new ArrayList<>(item.getValues().keySet());
                            for (int j = 0; j < keys.size(); j++) {
                                Object val = item.getValues().get(keys.get(j));
                                if (val != null) {
                                    cell.setCellValue(val.toString());
                                }
                                i++;
                            }
                    }
                }
            }
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            wb.write(outputStream);
            HttpHeaders headers = new HttpHeaders();
            headers.add("content-disposition", "attachment; filename=" + "test.xlsx");
            headers.setContentType(MediaType.valueOf(wb.getWorkbookType().getContentType()));
            return new ResponseEntity(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return new ResponseEntity(null, null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = {"/transpose"},
      method = RequestMethod.GET,
      produces = {
        MediaType.APPLICATION_OCTET_STREAM_VALUE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      })
    public ResponseEntity transposeSheet() {
        XSSFWorkbook wb = new XSSFWorkbook();
        if (wb != null) {
            CellStyle style = wb.createCellStyle();
            style.setAlignment(CellStyle.ALIGN_CENTER);
            List<Items.Item> items = new ArrayList<>(service.items().getItems());
            Sheet sheet = wb.createSheet("items");
            sheet.createFreezePane(0,1);
            int colNum = 0;
            Row row;
            for (int i = 0; i < (6 + items.get(0).getValues().size()); i++) {
                row = sheet.createRow(i);
                Cell cell = row.createCell(colNum);
                cell.setAsActiveCell();
                cell.setCellStyle(style);
                switch (i) {
                    case 0:
                        cell.setCellValue("Id");
                        break;
                    case 1:
                        cell.setCellValue("Name");
                        break;
                    case 2:
                        cell.setCellValue("Description");
                        break;
                    case 3:
                        cell.setCellValue("Content");
                        break;
                    case 4:
                        cell.setCellValue("Modified Date");
                        break;
                    case 5:
                        cell.setCellValue("CreationDate");
                        break;
                    default:
                        cell.setCellValue("Values");
                }
            }
            items = new ArrayList<>(service.items().getItems());
            for (Items.Item item : items) {
                colNum ++;

                for (int i = 0; i < (6 + item.getValues().keySet().size()); i++) {
                    row = sheet.getRow(i);
                    Cell cell = row.createCell(colNum);
                    cell.setAsActiveCell();
                    switch (i) {
                        case 0:
                            cell.setCellValue(item.getId());
                            break;
                        case 1:
                            cell.setCellValue(item.getName());
                            cell.setCellStyle(style);
                            break;
                        case 2:
                            cell.setCellValue(item.getDescription());
                            break;
                        case 3:
                            cell.setCellValue(item.getContent());
                            break;
                        case 4:
                            cell.setCellValue(item.getModifiedDate().toString());
                            break;
                        case 5:
                            cell.setCellValue(item.getCreationDate().toString());
                            break;
                        default:
                            List<String> keys = new ArrayList<>(item.getValues().keySet());
                            for (int j = 0; j < keys.size(); j++) {
                                Object val = item.getValues().get(keys.get(j));
                                if (val != null) {
                                    cell.setCellValue(val.toString());
                                }
                                i++;
                            }
                    }
                }
            }
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            wb.write(outputStream);
            HttpHeaders headers = new HttpHeaders();
            headers.add("content-disposition", "attachment; filename=" + "test.xlsx");
            headers.setContentType(MediaType.valueOf(wb.getWorkbookType().getContentType()));
            headers.add("content-disposition", "attachment; filename=" + "test.xlsx");
            return new ResponseEntity(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return new ResponseEntity(null, null, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = {"", "/"},
      method = RequestMethod.POST,
      produces = {
        MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE
      })
    public ResponseEntity<Items> importSheet(HttpServletRequest request) {
        Items out = new Items();
        ResponseEntity<Items> toReturn = new ResponseEntity(out, null, HttpStatus.BAD_REQUEST);
        try {
            Workbook wb = WorkbookFactory.create(request.getInputStream());
            if (wb != null) {
                Iterator<Sheet> sheets = wb.sheetIterator();
                List<Items.Item> items = new ArrayList<>();
                while (sheets.hasNext()) {
                    Sheet sheet = sheets.next();
                    Iterator<Row> rows = sheet.iterator();

                    while (rows.hasNext()) {
                        Row row = rows.next();
                        Items.Item item = new Items.Item();
                        Iterator<Cell> cells = row.cellIterator();
                        int i = 0;
                        while (cells.hasNext()) {
                            Cell cell = cells.next();
                            switch (i) {
                                case 0:
                                    item.setId(cell.getStringCellValue());
                                    break;
                                case 1:
                                    item.setName(cell.getStringCellValue());
                                    break;
                                case 2:
                                    item.setDescription(cell.getStringCellValue());
                                    break;
                                case 3:
                                    item.setContent(cell.getStringCellValue());
                                    break;
                                case 4:
                                    item.setModifiedDate(cell.getDateCellValue());
                                    break;
                                case 5:
                                    item.setCreationDate(cell.getDateCellValue());
                                    if (cell.getStringCellValue() != null) {
                                        item.setModifiedDate(new Date(cell.getStringCellValue()));
                                    }
                                    break;
                                case 6:
                                    if (cell.getStringCellValue() != null) {
                                        item.setCreationDate(new Date(cell.getStringCellValue()));
                                    }
                                    break;
                                default:
                                    item.getValues().put(cell.getAddress().formatAsString(), cell.getStringCellValue());
                            }
                            i++;
                        }
                        items.add(item);
                    }
                }
                out.getItems().addAll(items);
            }
            service.items().getItems().addAll(out.getItems());
            toReturn = new ResponseEntity(out, null, HttpStatus.OK);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        } catch (InvalidFormatException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return toReturn;
    }
}
