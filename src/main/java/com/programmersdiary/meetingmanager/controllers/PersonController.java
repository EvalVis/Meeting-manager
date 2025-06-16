package com.programmersdiary.meetingmanager.controllers;

import com.programmersdiary.meetingmanager.models.PersonBinding;
import com.programmersdiary.meetingmanager.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Person Management", description = "APIs for managing meeting participants")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    @Operation(
        summary = "Add person to meeting",
        description = "Add a person to an existing meeting. Returns true if the person has schedule conflicts, false otherwise."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Person successfully added to meeting",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "boolean", description = "True if person has schedule conflicts, false otherwise")
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request - missing required fields in person data"
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal server error occurred while processing the request"
        )
    })
    private ResponseEntity<Boolean> addPersonToMeeting(
        @Parameter(
            description = "Name of the meeting to add the person to",
            required = true,
            example = "Weekly Standup"
        )
        @RequestParam("meeting_name") String meetingName,
        
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Person details including full name and invitation date. Both fields are required.",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PersonBinding.class)
            )
        )
        @RequestBody PersonBinding personBinding
    ) {
        if (personBinding.anyFieldNull()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Boolean warned = personService.addPersonToMeeting(meetingName, personBinding);
        return new ResponseEntity<>(warned, HttpStatus.OK);
    }

    @DeleteMapping("/person")
    @Operation(
        summary = "Remove person from meeting",
        description = "Remove a person from an existing meeting by their full name"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Person successfully removed from meeting"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Meeting or person not found"
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal server error occurred while processing the request"
        )
    })
    private HttpStatus deletePersonFromMeeting(
        @Parameter(
            description = "Name of the meeting to remove the person from",
            required = true,
            example = "Weekly Standup"
        )
        @RequestParam("meeting_name") String meetingName,
        
        @Parameter(
            description = "Full name of the person to remove from the meeting",
            required = true,
            example = "John Doe"
        )
        @RequestParam("person_full_name") String personFullName
    ) {
        boolean removed = personService.removePersonFromMeeting(meetingName, personFullName);
        if (removed) {
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

}
