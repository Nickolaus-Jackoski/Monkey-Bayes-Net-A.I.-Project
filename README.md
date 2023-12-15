# Project Summary: Monkey Tracking with Bayes Net

## Overview
This project involves developing a program to track a monkey's location in a ballroom using a Bayesian network (Bayes Net). The scenario originates from a real-life event in 1976 where rhesus monkeys escaped from the Memphis Zoo and entered the Rhodes College campus. The current situation involves a single monkey loose in the BCLC ballroom of Rhodes College, with the challenge of locating it using AI techniques.

## Problem Setup
- **Ballroom Representation**: A two-dimensional grid (m rows x n columns).
- **Monkey's Position**: Unknown, but within the grid.
- **Sensors Used**:
  - **Motion Sensors**: Installed at top-left (0, 0) and bottom-right (m-1, n-1) corners with limited accuracy.
  - **Sound Sensors**: Cover the entire ballroom, pinpointing the monkey's location with varying accuracy.

## Bayesian Network Setup
- **Last Location (L)**: Represents the monkey's last known location (an ordered pair), initially uniformly distributed across the grid.
- **Current Location (C)**: Depends on the last location and the monkey's movement (up, down, left, right).
- **Motion Sensors (M1 and M2)**: Detect monkey's presence based on its location relative to the sensors.
- **Sound Sensor (S)**: Provides a probable location, either exact or off by one or two Manhattan steps.

## Program Functionality
- **Input**: Reads a text file with grid size and sensor readings (M1, M2, and S) for each timestep.
- **Bayes Net Calculation**: Utilizes sensor readings to update the probability of the monkey's current location (C).
- **Output**: Displays probabilities for each grid square at every timestep.

## Implementation Details
- **Java Files**: Includes `Main.java`, `SoundProb.java`, and `MonkeyBayesNet.java`.
- **OOP Principles**: Uses object-oriented programming for different aspects (probability distributions for L, C, M1/M2, and S).
- **Dynamic Bayes Net**: The distribution for C at one timestep becomes the distribution for L at the next timestep.
- **Debugging and Output Formatting**: Integrated for ease of verification and comparison.

## How to Run The Program
- Put all of the .txt files and .java files in the same folder 
- Pick a .txt file to run and enter it into the prompt it will run through the file and display the results.

## Code Breakdown: Key Components of Each Class

### 1. `Main.java`
- **Purpose**: Entry point of the application.
- **Key Functions**:
  - **File Reading**: Reads the input text file.
  - **Initialization**: Sets up initial probabilities and grid configuration.
  - **Execution**: Updates the Bayesian network after each sensor reading.
  - **Output**: Prints the probability distribution of the monkey's location.

### 2. `SoundProb.java`
- **Purpose**: Manages sound sensor probability calculations.
- **Key Components**:
  - **Probability Distribution**: Calculates likelihood based on sound sensor readings.
  - **Error Handling**: Accounts for inaccuracies, distributing probabilities.
  - **Helper Methods**: Assists in calculating probabilities for different scenarios.

### 3. `MonkeyBayesNet.java`
- **Purpose**: Core of the Bayesian network.
- **Key Components**:
  - **Last Location (L) Distribution**: Manages previous location probability.
  - **Current Location (C) Distribution**: Updates current location probability.
  - **Motion Sensors (M1 and M2) Distributions**: Calculates probabilities based on motion sensor readings.
  - **Bayesian Update**: Updates probabilities using sensor data.
  - **Dynamic Update**: Distribution for C becomes next starting probability for L.

#### General Features Across Classes
- **Modularity**: Responsible for specific aspects, adhering to OOP principles.
- **Probability Handling**: Calculates and updates probabilities based on sensor readings.
- **Error and Edge Case Handling**: Handles inaccuracies and edge cases like corners and walls.
- **Debugging and Output Formatting**: For validation and understanding.

## Conclusion
Each class in the project plays a distinct role in modeling and solving the monkey tracking problem using Bayesian networks. The modular design makes the codebase maintainable and extensible, allowing for future enhancements such as more sophisticated sensor models or different grid configurations. This project was part of my COMP 372 A.I. class at Rhodes college. 
