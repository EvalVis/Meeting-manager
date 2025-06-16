package com.programmersdiary.meetingmanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Meeting Manager API",
        description = """
                An API for managing meetings.
                Functionality:
                Meetings:
                1) Create meeting
                2) Search for the meeting
                3) Delete a meeting
                People in meetings;
                1) Add person to the meeting
                2) Remote person from the meeting
                Allows creating, searching and deleting meetings.
                """,
        version = "1.0.0",
        contact = @Contact(
            name = "Evaldas Visockas",
            email = "developersediary@gmail.com"
        ),
        license = @License(
            name = "GNU Affero General Public License version 3",
            url = "https://opensource.org/license/agpl-v3"
        )
    )
)
public class OpenApiConfig {
} 