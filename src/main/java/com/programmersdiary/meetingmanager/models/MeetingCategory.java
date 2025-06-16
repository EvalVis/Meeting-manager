package com.programmersdiary.meetingmanager.models;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Categories of meetings")
public enum MeetingCategory {
    @Schema(description = "Code review or development-focused meetings")
    CODE_MONKEY,
    
    @Schema(description = "Hub or coordination meetings")
    HUB,
    
    @Schema(description = "Short duration meetings")
    SHORT,
    
    @Schema(description = "Team building activities")
    TEAM_BUILDING
}
