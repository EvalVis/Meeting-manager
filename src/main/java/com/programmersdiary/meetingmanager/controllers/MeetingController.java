package com.programmersdiary.meetingmanager.controllers;

import com.programmersdiary.meetingmanager.models.MeetingBinding;
import com.programmersdiary.meetingmanager.models.MeetingSearchBinding;
import com.programmersdiary.meetingmanager.services.MeetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Meeting Management", description = "API for managing meetings")
public class MeetingController {

    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping("/meetings")
    @Operation(
        summary = "Search and filter meetings",
        description = "Retrieve a list of meetings based on search criteria"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved meetings",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MeetingBinding.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error occurred while processing the request"
        )
    })
    private List<MeetingBinding> listMeetings(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Search criteria for filtering meetings",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MeetingSearchBinding.class)
            )
        )
        @RequestBody MeetingSearchBinding meetingSearchBinding
    ) {
        return meetingService.filterMeetings(meetingSearchBinding);
    }

    @PostMapping("/meeting")
    @Operation(
        summary = "Create a new meeting",
        description = """
                Create a new meeting with all required details including
                name, responsible person, description, category, type, dates, and participant
                """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Meeting created successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request - missing required fields or invalid data"
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal server error occurred while processing the request"
        )
    })
    private HttpStatus createMeeting(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Meeting details. All fields (name, responsiblePerson, description, category, type, startDate, endDate) are required.",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MeetingBinding.class)
            )
        )
        @RequestBody MeetingBinding meetingBinding
    ) {
        if (meetingBinding.anyFieldNull()) {
            return HttpStatus.BAD_REQUEST;
        }
        meetingService.createMeeting(meetingBinding);
        return HttpStatus.OK;
    }

    @DeleteMapping("/meeting")
    @Operation(
        summary = "Delete a meeting",
        description = "Delete a specific meeting by providing the meeting name and responsible person"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Meeting deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Meeting not found"
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal server error occurred while processing the request"
        )
    })
    private HttpStatus deleteMeeting(
        @Parameter(
            description = "Name of the meeting to delete",
            required = true,
            example = "Weekly Standup"
        )
        @RequestParam("meeting_name") String meetingName,
        
        @Parameter(
            description = "Full name of the person responsible for the meeting",
            required = true,
            example = "John Doe",
            in = io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER
        )
        @RequestHeader("responsible_person") String responsiblePerson
    ) {
        boolean deleted = meetingService.deleteMeeting(meetingName, responsiblePerson);
        if (deleted) {
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

}
