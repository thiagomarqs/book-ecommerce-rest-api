package com.nozama.api.application.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nozama.api.application.dto.request.PublisherRequest;
import com.nozama.api.application.dto.response.PublisherResponse;
import com.nozama.api.application.mapper.EntityMapper;
import com.nozama.api.domain.entity.Publisher;
import com.nozama.api.domain.usecase.publisher.ManagePublisher;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/publishers")
@Tag(
	name = "Publisher", 
	description = "Operations for managing publishers."
)
public class PublisherController {
	
	@Autowired
    private ManagePublisher managePublisherUseCase;

    @Autowired
    private EntityMapper entityMapper;

	@PostMapping(
		consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    @Operation(
        summary = "Creates an publisher",
        description = "Creates an publisher based on the request's body payload.",
        tags = { "Publisher" }
    )
    public ResponseEntity<PublisherResponse> create(@RequestBody PublisherRequest payload) {
        Publisher publisher = entityMapper.mapEntity(payload, Publisher.class);

        publisher = managePublisherUseCase.create(publisher);

        PublisherResponse response = entityMapper
            .mapEntity(publisher, PublisherResponse.class)
            .setLinks();

        URI uri = response.getRequiredLink(IanaLinkRelations.SELF).toUri();

        return ResponseEntity.created(uri).body(response);
    }

	@GetMapping(
		value = "/{id}",
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
	)
    @Operation(
        summary = "Finds an publisher",
        description = "Finds an publisher by their Id.",
        tags = { "Publisher" }
    )
    public ResponseEntity<PublisherResponse> findById(@PathVariable(value = "id") Long id) {
        Publisher found = managePublisherUseCase.findById(id);
        PublisherResponse response = entityMapper
            .mapEntity(found, PublisherResponse.class)
            .setLinks();

        return ResponseEntity.ok(response);
    }

	@GetMapping(
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
	)
    @Operation(
        summary = "Finds all publishers",
        description = "Finds all publishers. If no publisher exists, an empty array will be returned.",
        tags = { "Publisher" }
    )
    public ResponseEntity<List<PublisherResponse>> findAll() {
        List<Publisher> found = managePublisherUseCase.findAll();
        List<PublisherResponse> response = entityMapper
            .mapList(found, PublisherResponse.class)
            .stream().map(a -> a.setLinks())
            .toList();

        return ResponseEntity.ok(response);
    }

	@PutMapping(
		value = "/{id}",
		consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
	)
    @Operation(
        summary = "Updates an publisher by their id",
        description = "Finds an publisher by the provided id and updates it. If the provided id is invalid, an exception will be thrown.",
        tags = { "Publisher" }
    )
    public ResponseEntity<PublisherResponse> update(@PathVariable(value = "id") Long id, @RequestBody PublisherRequest payload) {
        Publisher publisher = entityMapper.mapEntity(payload, Publisher.class);
        publisher.setId(id);

        publisher = managePublisherUseCase.update(publisher);

        PublisherResponse response = entityMapper.mapEntity(publisher, PublisherResponse.class).setLinks();

        return ResponseEntity.ok(response);
    }

	@DeleteMapping("/{id}")
    @Operation(
        summary = "Deletes an publisher by their id",
        description = "Finds an publisher by the provided id and deletes it. If the provided id is invalid, an exception will be thrown.",
        tags = { "Publisher" }
    )
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        managePublisherUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
