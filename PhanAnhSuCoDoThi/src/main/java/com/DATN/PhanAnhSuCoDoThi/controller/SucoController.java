package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.CreateSucoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.SucoResponse;
import com.DATN.PhanAnhSuCoDoThi.service.ISucoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/suco")
public class SucoController {
    private ISucoService sucoService;

    @GetMapping
    public List<SucoResponse> getAll(){
        return sucoService.findAll();
    }

    @GetMapping("/{id}")
    public SucoResponse getById(@PathVariable String id) {
        return sucoService.findById(id);
    }

    @PostMapping
    public SucoResponse create(@RequestBody CreateSucoRequest request) {
        return sucoService.create(request);
    }

}
