package com.dataentrykotlinspringbootstack.backend.controllers

import com.dataentrykotlinspringbootstack.backend.dto.EntryRequest
import com.dataentrykotlinspringbootstack.backend.dto.EntryResponse
import com.dataentrykotlinspringbootstack.backend.services.EntryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/entries")
class EntryController(private val entryService: EntryService) {

    @GetMapping
    fun list(): ResponseEntity<List<EntryResponse>> {
        return ResponseEntity.ok(entryService.listEntries())
    }

    @PostMapping
    fun create(@Valid @RequestBody request: EntryRequest): ResponseEntity<EntryResponse> {
        val created = entryService.createEntry(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }
}