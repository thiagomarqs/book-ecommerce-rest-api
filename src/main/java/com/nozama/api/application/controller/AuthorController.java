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

import com.nozama.api.application.dto.request.AuthorRequest;
import com.nozama.api.application.dto.response.AuthorResponse;
import com.nozama.api.application.mapper.EntityMapper;
import com.nozama.api.domain.entity.Author;
import com.nozama.api.domain.usecase.author.ManageAuthor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/authors")
@Tag(
	name = "Author", 
	description = "Operations for managing authors."
)
public class AuthorController {

    @Autowired
    private ManageAuthor manageAuthorUseCase;

    @Autowired
    private EntityMapper entityMapper;

    @PostMapping(
		consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    @Operation(
        summary = "Creates an author",
        description = "Creates an author based on the request's body payload.",
        tags = { "Author" }
    )
    public ResponseEntity<AuthorResponse> create(@RequestBody AuthorRequest payload) {
        Author author = entityMapper.mapEntity(payload, Author.class);

        author = manageAuthorUseCase.create(author);

        AuthorResponse response = entityMapper
            .mapEntity(author, AuthorResponse.class)
            .setLinks();

        URI uri = response.getRequiredLink(IanaLinkRelations.SELF).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping(
		value = "/{id}",
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
	)
    @Operation(
        summary = "Finds an author",
        description = "Finds an author by their Id.",
        tags = { "Author" }
    )
    public ResponseEntity<AuthorResponse> findById(@PathVariable(value = "id") Long id) {
        Author found = manageAuthorUseCase.findById(id);
        AuthorResponse response = entityMapper
            .mapEntity(found, AuthorResponse.class)
            .setLinks();

        return ResponseEntity.ok(response);
    }

    @GetMapping(
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
	)
    @Operation(
        summary = "Finds all authors",
        description = "Finds all authors. If no author exists, an empty array will be returned.",
        tags = { "Author" }
    )
    public ResponseEntity<List<AuthorResponse>> findAll() {
        List<Author> found = manageAuthorUseCase.findAll();
        List<AuthorResponse> response = entityMapper
            .mapList(found, AuthorResponse.class)
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
        summary = "Updates an author by their id",
        description = "Finds an author by the provided id and updates it. If the provided id is invalid, an exception will be thrown.",
        tags = { "Author" }
    )
    public ResponseEntity<AuthorResponse> update(@PathVariable(value = "id") Long id, @RequestBody AuthorRequest payload) {
        Author author = entityMapper.mapEntity(payload, Author.class);
        author.setId(id);

        author = manageAuthorUseCase.update(author);

        AuthorResponse response = entityMapper.mapEntity(author, AuthorResponse.class).setLinks();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Deletes an author by their id",
        description = "Finds an author by the provided id and deletes it. If the provided id is invalid, an exception will be thrown.",
        tags = { "Author" }
    )
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        manageAuthorUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

}
