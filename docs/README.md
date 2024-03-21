# NUPlanner Project README

## Overview

NUPlanner is a collaborative scheduling application designed to help users manage and coordinate their weekly schedules efficiently. It supports multiple users, allowing them to add events with participants, which are then incorporated into their schedules. The application facilitates schedule management through the ability to upload, modify, and download user schedules via XML files, ensuring ease of access and modification.

## Quick Start

To get started with NUPlanner, follow these steps:

1. **Initialize the Central System**:
    ```java
    CentralSystem centralSystem = new CentralSystem();
    ```

2. **Load User Schedules**:
   Load schedules from XML files for each user.
    ```java
    List<String> filePaths = new ArrayList<>();
    filePaths.add("/path/to/user1.xml");
    centralSystem.loadSchedulesFromXML(filePaths);
    ```

3. **Modify Schedules**:
   Add, update, or delete events as needed.

4. **Save Schedules**:
   Export updated schedules back to XML.
    ```java
    centralSystem.writeScheduleToXML("/output/directory", "user1");
    ```

## Key Components/Subcomponents

### Model (`src` Directory)

- **CentralSystem**: Orchestrates user schedules and maintains event consistency across the system.
- **Day**: Enumerates days of the week to support scheduling.
- **Event**: Encapsulates event details and scheduling logic.
- **Schedule**: Handles event management for individual users, ensuring no scheduling conflicts.
- **User**: Represents users within the system, each with a unique schedule.

### Interfaces (`src` Directory)

- **ICentralSystem**: Defines the central system's operations.
- **IEvent**: Establishes the contract for event manipulation.
- **ISchedule**: Specifies the structure for user schedule management.
- **IUser**: Outlines expected user functionalities.
- **IXmlHandler**: Details XML processing methods for schedules.

### XML Handling (`src` Directory)

- **XmlHandler**: Manages the reading and writing of schedules to and from XML format.

### Testing Suite (`test` Directory)

- **TestCentralSystem, TestEvent, TestSchedule, TestXmlHandler, MoreCentralSystem**: Provide comprehensive unit testing for the system components.

## Source Organization

- **`/docs`**: Has this ReadMe file.
- **`/OutputTestDirectory`**: Directory for testing writing schedules to.
- **`/src`**: Contains all the source code, including models, interfaces, and the main application logic.
- **`/test`**: Houses the testing suite to ensure the application's reliability.
- **`/examples`**: Example XML files for demonstration and testing purposes, such as `prof.xml`.

## XML File Handling

Our approach to managing XML files involves the implementation of an XML handler. This specialized handler is designed to efficiently process XML files, enabling the dynamic creation and manipulation of user schedules based on the events and dates specified within these files. When provided with a user's schedule, the handler is capable of generating an XML file that accurately represents this schedule, including assigning it the appropriate user name and ensuring it is saved to the correct file path.

Integration with the central system is seamless. Whenever the central system requires interaction with XML files—whether loading a user's schedule from a file or exporting a schedule to XML—it delegates these tasks to the XML handler. This delegation process ensures that the central system can focus on its core functionalities, relying on the handler to accurately process and manage the XML files. As a result, files are either created or ingested into the system with precision, streamlining the entire process of schedule management.