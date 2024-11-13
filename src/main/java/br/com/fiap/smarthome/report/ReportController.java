package br.com.fiap.smarthome.report;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import br.com.fiap.smarthome.report.dto.ReportRequest;
import br.com.fiap.smarthome.report.dto.ReportResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("report")
@CacheConfig(cacheNames = "reports")
@Tag(name = "Reports", description = "Report-related endpoint")
public class ReportController {

    @Autowired
    private ReportService service;

    @Autowired
    PagedResourcesAssembler<Report> pageAssembler;

    @PostMapping
    @ResponseStatus(CREATED)
    @CacheEvict(allEntries = true)
    public ResponseEntity<ReportResponse> create(@RequestBody @Valid ReportRequest reportRequest, UriComponentsBuilder uriBuilder) {
        Report report = service.create(reportRequest.toModel());

        var uri = uriBuilder
                .path("/report/{id}")
                .buildAndExpand(report.getReportId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(ReportResponse.from(report));
    }

    @GetMapping
    @Cacheable
    public PagedModel<EntityModel<Report>> readAll(@PageableDefault(size = 50, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Report> page = service.readAll(pageable);
        return pageAssembler.toModel(page, Report::toEntityModel);
    }

    @GetMapping("{id}")
    public ResponseEntity<Report> readItem(@PathVariable Long id) {
        return service.readItem(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    @CacheEvict(allEntries = true)
    public Report update(@PathVariable Long id, @RequestBody Report report) {
        return service.update(id, report);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("userReport/{userId}")
    public PagedModel<EntityModel<Report>> getReportsByUserId(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) @PathVariable Long userId, Pageable pageable) {
        Page<Report> page = service.getReportsByUserId(pageable, userId);
        return pageAssembler.toModel(page, Report::toEntityModel);
    }
}
