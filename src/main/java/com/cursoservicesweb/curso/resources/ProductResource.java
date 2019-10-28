package com.cursoservicesweb.curso.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.cursoservicesweb.curso.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cursoservicesweb.curso.dto.ProductCategoriesDTO;
import com.cursoservicesweb.curso.dto.ProductDTO;
import com.cursoservicesweb.curso.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAllPaged(
            @RequestParam(value = "page", defaultValue = "0") Integer page
            , @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerpage
            , @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
            , @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerpage, Sort.Direction.valueOf(direction), orderBy);
        Page<ProductDTO> list = service.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findbyId(@PathVariable Long id) {
        ProductDTO obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductCategoriesDTO obj) {
        ProductDTO newDTO = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(newDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductCategoriesDTO obj) {
        ProductDTO dto = service.update(id, obj);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ProductDTO>> findByCategoryPaged(
            @PathVariable Long categoryId
            , @RequestParam(value = "page", defaultValue = "0") Integer page
            , @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerpage
            , @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
            , @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerpage, Sort.Direction.valueOf(direction), orderBy);
        Page<ProductDTO> list = service.findByCategoryPaged(categoryId,pageRequest);
        return ResponseEntity.ok().body(list);

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}/addcategory")
    public ResponseEntity<Void> addCategory(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        service.addCategory(id,dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}/removecategory")
    public ResponseEntity<Void> removeCategory(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        service.removeCategory(id,dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}/setcategories")
    public ResponseEntity<Void> setCategories(@PathVariable Long id, @RequestBody  List<CategoryDTO> categories) {
        service.setCategories(id,categories);
        return ResponseEntity.noContent().build();
    }

}
