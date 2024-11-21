Hotel Management App

Overview
This is a console-based application for managing a hotel system. The app provides the following basic commands:
- checkin - Guest check-in.
- checkout - Guest check-out.
- list - Lists all rooms with detailed information about each room.
- prices - Lists all rooms with their prices only.
- view - Displays information about a specific room.
- save - Saves the current state to an .xlsx file.
- help - Displays available commands.
- exit - Exits the application.

Key Features
    A simple and intuitive command-based interface.
    Custom MyMap<K, V> implementation, imitating java.util.Map.
    Room details can be viewed, edited, and saved to external files. (data/rooms.xlsx)

Requirements
    Java 17
    Apache Maven 3.9.+

How to Use
1. Download the .zip file.
2. Unzip the project: Extract the downloaded file to a directory of your choice.
3. Navigate to the main directory: Open a terminal and navigate to the HotelManagement directory.
4. Run the application:
            java -jar main-1.0.jar

Technologies Used
- Java 17 for core application development.
- Apache Maven for managing project dependencies.
- SonarQube for code quality analysis and test coverage reports.
- Javadoc for generating project documentation