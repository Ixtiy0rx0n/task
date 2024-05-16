package org.example.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.dto.CreateEmployeeDTO;
import org.example.dto.EmployeeDTO;
import org.example.entity.EmployeeEntity;
import org.example.entity.PositionEntity;
import org.example.enums.AppLanguage;
import org.example.exp.AppBadException;
import org.example.repository.EmployeeRepository;
import org.example.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionService positionService;

    public EmployeeDTO getById(int id) {
        Optional optional = employeeRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Employee not found");
        }
        EmployeeEntity employee = (EmployeeEntity) optional.get();
        return toDTO(employee);
    }

    public Boolean deleteById(int id) {
        employeeRepository.deleteById(id);
        return true;
    }

    public PageImpl getAll(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        Pageable paging = PageRequest.of(page - 1, size, sort);
        Page<EmployeeEntity> articleTypePage = employeeRepository.findAll(paging);
        List<EmployeeEntity> entityList = articleTypePage.getContent();
        Long totalElement = articleTypePage.getTotalElements();
        List<EmployeeDTO> dtoList = new LinkedList<>();
        for (EmployeeEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }

        return new PageImpl<>(dtoList, paging, totalElement);
    }

    public EmployeeDTO create(CreateEmployeeDTO dto) {
        EmployeeEntity employee = employeeRepository.getByPhone(dto.getPhone());
        if (employee != null) {
            throw new AppBadException("Employee already exists");
        }

        PositionEntity position = positionService.get(dto.getPositionId());

        employee = new EmployeeEntity();
        employee.setPhone(dto.getPhone());
        employee.setName(dto.getName());
        employee.setAddress(dto.getAddress());
        employee.setCreatedDate(LocalDateTime.now());
        employee.setAge(dto.getAge());
        employee.setSurname(dto.getSurname());
        employee.setPositionId(dto.getPositionId());
        employee.setSalary(dto.getSalary());
        employeeRepository.save(employee);

        EmployeeDTO resultDTO = toDTO(employee);
        resultDTO.setId(employee.getId());
        resultDTO.setCreatedDate(employee.getCreatedDate());
        resultDTO.setPosition(position.getPositionName());

        return resultDTO;
    }

    public EmployeeDTO getByPhone(String phone) {
        EmployeeEntity employee = employeeRepository.getByPhone(phone);
        return toDTO(employee);
    }

    public EmployeeDTO update(EmployeeDTO dto) {
        Optional<EmployeeEntity> optional = employeeRepository.findById(dto.getId());
        if (optional.isEmpty()) {
            throw new AppBadException("Employee not found");
        }

        employeeRepository.update(dto.getName(), dto.getPhone(), dto.getSurname(), dto.getAge(), dto.getAddress(),
                dto.getSalary(), Integer.valueOf(dto.getPosition()), LocalDateTime.now());
        dto.setUpdatedDate(LocalDateTime.now());
        return dto; // xato ishlaydi
    }

    public byte[] filterDownload() throws IOException {
        List<EmployeeEntity> entityList = employeeRepository.findAll();
        return filterDownload(entityList);
    }


    public byte[] filterDownload(List<EmployeeEntity> employeeDTOList) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("employee");
        int rowNum = 0;
        Row header = sheet.createRow(rowNum++);
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 2000);
        sheet.setColumnWidth(5, 3000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 3000);
        sheet.setColumnWidth(8, 8000);
        sheet.setColumnWidth(9, 8000);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillBackgroundColor(IndexedColors.BLACK.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());

//        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Surname");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Phone number");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Age");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Position");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Salary");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(7);
        headerCell.setCellValue("Address");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(8);
        headerCell.setCellValue("Created date");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(9);
        headerCell.setCellValue("Updated date");
        headerCell.setCellStyle(headerStyle);

        for (EmployeeDTO employee : getDtoList()) {
            String cretedDate = "";
            String updatedDate = "";
            if (!(employee.getCreatedDate() == null)) {
                cretedDate = employee.getCreatedDate().toString();
            }
            if (!(employee.getUpdatedDate() == null)) {
                updatedDate = employee.getUpdatedDate().toString();
            }
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(employee.getId());
            row.createCell(1).setCellValue(employee.getName());
            row.createCell(2).setCellValue(employee.getSurname());
            row.createCell(3).setCellValue(employee.getPhone());
            row.createCell(4).setCellValue(employee.getAge());
            row.createCell(5).setCellValue(employee.getPosition());
            row.createCell(6).setCellValue(employee.getSalary());
            row.createCell(7).setCellValue(employee.getAddress());
            row.createCell(8).setCellValue(cretedDate);
            row.createCell(9).setCellValue(updatedDate);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);


        String filename = "employee.xlsx";

        return outputStream.toByteArray();
    }


    public List<EmployeeDTO> getDtoList() {
        List<EmployeeEntity> entityList = employeeRepository.findAll();
        List<EmployeeDTO> dtoList = new LinkedList<>();
        for (EmployeeEntity entity : entityList) {
            EmployeeDTO dto = new EmployeeDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setSurname(entity.getSurname());
            dto.setAddress(entity.getAddress());
            dto.setAge(entity.getAge());
            dto.setPhone(entity.getPhone());
            dto.setSalary(entity.getSalary());
            dto.setUpdatedDate(entity.getUpdatedDate());
            dto.setCreatedDate(entity.getCreatedDate());

            if(entity.getPosition() != null) {
            dto.setPosition(entity.getPosition().getPositionName());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }


    public EmployeeDTO toDTO(EmployeeEntity entity) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setAge(entity.getAge());
        dto.setSalary(entity.getSalary());
        return dto;
    }
}
