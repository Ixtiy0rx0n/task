package org.example.service;

import org.example.dto.EmployeeDTO;
import org.example.dto.PaginationResultDTO;
import org.example.entity.EmployeeEntity;
import org.example.exp.AppBadException;
import org.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

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

    public EmployeeDTO create(EmployeeDTO dto) {
        EmployeeEntity employee = employeeRepository.getByPhone(dto.getPhone());
        if (employee != null) {
            throw new AppBadException("Employee already exists");
        }
        else {
            employee = new EmployeeEntity();
            employee.setPhone(dto.getPhone());
            employee.setName(dto.getName());
            employee.setAddress(dto.getAddress());
            employee.setCreatedDate(LocalDateTime.now());
            employee.setAge(dto.getAge());
            employee.setSurname(dto.getSurname());
            employee.setPositionId(dto.getPosition());
            employee.setSalary(dto.getSalary());
            employeeRepository.save(employee);
        }
        dto.setId(employee.getId());
        dto.setCreatedDate(employee.getCreatedDate());
        return dto;
    }

    public EmployeeDTO getByPhone(String phone) {
        EmployeeEntity employee = employeeRepository.getByPhone(phone);
        return toDTO(employee);
    }

    public EmployeeDTO update(EmployeeDTO dto) {
        Optional<EmployeeEntity> optional = employeeRepository.findById(dto.getId());
        if (optional.isEmpty()){
            throw new AppBadException("Employee not found");
        }

        employeeRepository.update(dto.getName(), dto.getPhone(), dto.getSurname(), dto.getAge(), dto.getAddress(),
                dto.getSalary(), dto.getPosition(), LocalDateTime.now());
        dto.setUpdatedDate(LocalDateTime.now());
        return dto;
    }





    public EmployeeDTO toDTO(EmployeeEntity entity) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setPosition(entity.getPositionId());
        dto.setAge(entity.getAge());
        dto.setSalary(entity.getSalary());
        return dto;
    }
}
