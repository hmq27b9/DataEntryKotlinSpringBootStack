package com.dataentrykotlinspringbootstack.backend.routers

import com.dataentrykotlinspringbootstack.backend.controllers.EntryController
import com.dataentrykotlinspringbootstack.backend.controllers.EntryRequest
import com.dataentrykotlinspringbootstack.backend.controllers.EntryResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/entries")
class EntryRouter(private val entryController: EntryController) {

    @GetMapping
    fun list(): ResponseEntity<List<EntryResponse>> =
        ResponseEntity.ok(entryController.listEntries())

    @PostMapping
    fun create(@Valid @RequestBody request: EntryRequest): ResponseEntity<EntryResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(entryController.createEntry(request))
}