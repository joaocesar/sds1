package com.devsuperior.dspesquisa.resources;

import com.devsuperior.dspesquisa.dto.RecordDTO;
import com.devsuperior.dspesquisa.dto.RecordInsertDTO;
import com.devsuperior.dspesquisa.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping(value = "/records")
public class RecordResource {

    private static String VAZIO = "";

    @Autowired
    private RecordService service;

    @PostMapping
    public ResponseEntity<RecordDTO> inset(@RequestBody RecordInsertDTO dto) {
        RecordDTO newDto = service.insert(dto);
        return ResponseEntity.ok().body(newDto);
    }

    @GetMapping
    public ResponseEntity<Page<RecordDTO>> findAll(
            @RequestParam(value = "min", defaultValue = "") String min,
            @RequestParam(value = "max", defaultValue = "") String max,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "0") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "moment") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {

        Instant minDate = (VAZIO.equals(min)) ? null : Instant.parse(min);
        Instant maxDate = (VAZIO.equals(max)) ? null : Instant.parse(max);

        if (linesPerPage == 0) {
            linesPerPage = Integer.MAX_VALUE;
        }

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<RecordDTO> list = service.findByMoment(minDate, maxDate, pageRequest);

        return ResponseEntity.ok().body(list);
    }
}
