package com.programmersdiary.meetingmanager.models;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Types of meetings based on where they are happening")
public enum MeetingType {
    @Schema(description = "Online/virtual meeting")
    LIVE,
    @Schema(description = "Physical in-person meeting")
    IN_PERSON
}
