# 🎮 Tokimon Database - JavaFX Client Application

> A sophisticated desktop client application for managing a Pokémon-inspired creature database with modern JavaFX UI and RESTful API integration.

[![Java](https://img.shields.io/badge/Java-22-orange.svg)](https://openjdk.java.net/projects/jdk/22/)
[![JavaFX](https://img.shields.io/badge/JavaFX-22-blue.svg)](https://openjfx.io/)
[![Gson](https://img.shields.io/badge/Gson-2.11.0-green.svg)](https://github.com/google/gson)

## 🌟 Overview

The Tokimon Database Client is a feature-rich desktop application built with JavaFX that provides an intuitive interface for managing Tokimon creatures. This client application communicates with a REST API server to perform full CRUD operations on a database of collectible creatures, complete with custom styling, input validation, and responsive design.

## ✨ Key Features

### 🎨 Modern User Interface

- **Custom Typography**: Integrated VT323 retro gaming font for authentic visual appeal
- **Animated Background**: Dynamic GIF background with overlay design
- **Responsive Layout**: Adaptive grid system for optimal viewing across different screen sizes
- **Card-Based Design**: Elegant card layout for displaying Tokimon collections

### 🔗 RESTful API Integration

- **Complete CRUD Operations**: Create, Read, Update, and Delete Tokimon records
- **HTTP Client Implementation**: Native Java HTTP connections with proper error handling
- **JSON Serialization**: Seamless data exchange using Google Gson library
- **Real-time Synchronization**: Automatic UI refresh after data modifications

### 🛡️ Advanced Input Validation

- **URL Validation**: Real-time image URL verification with HTTP response checking
- **Type-Safe Inputs**: Regex-based input filtering for strings and integers
- **Content-Type Validation**: Ensures uploaded URLs point to valid image resources
- **User Feedback**: Immediate visual feedback for validation results

### 🎯 Core Functionality

- **Tokimon Gallery**: Grid-based display with customizable card layouts (5 columns)
- **Add New Creatures**: Form-based creation with comprehensive validation
- **Detailed View**: Pop-up windows for viewing and editing individual Tokimon
- **Bulk Operations**: Efficient handling of multiple records
- **Real-time Updates**: Dynamic refresh without application restart

## 🏗️ Technical Architecture

### Technology Stack

- **Frontend**: JavaFX 22 with FXML support
- **HTTP Client**: Native Java HTTP URL Connection
- **JSON Processing**: Google Gson 2.11.0
- **Build System**: IntelliJ IDEA with artifact configuration
- **Module System**: Java Platform Module System (JPMS)

### Design Patterns

- **MVC Architecture**: Clear separation of concerns
- **Event-Driven Programming**: JavaFX event handlers for user interactions
- **Observer Pattern**: Real-time UI updates based on data changes
- **Factory Pattern**: Dynamic UI component generation

### Data Model

```java
public class Tokimon {
    private long tid;           // Unique identifier
    private String name;        // Creature name
    private String imagePath;   // URL to creature image
    private String type;        // Creature classification
    private int rarityScore;    // Rarity rating (1-10)
}
```

## 🚀 Getting Started

### Prerequisites

- Java 22 or higher
- JavaFX SDK 22.0.2
- Running Tokimon REST API server on `localhost:8080`

### Installation & Setup

1. **Clone the repository**

   ```bash
   git clone https://github.com/sgoyal04/CMPT213--a5-client.git
   cd CMPT213--a5-client
   ```

2. **Configure JavaFX**

   - Download JavaFX SDK 22.0.2
   - Add JavaFX libraries to your IDE classpath
   - Configure VM options: `--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics`

3. **Build and Run**

   ```bash
   # Using IntelliJ IDEA artifact
   java -jar out/artifacts/ClientSide_jar/ClientSide.jar

   # Or compile and run directly
   javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics src/module-info.java src/ca/cmpt213/asn5/client/*.java
   java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics -cp src ca.cmpt213.asn5.client.ClientApplication
   ```

## 🎮 User Guide

### Main Interface

- **Show Tokimons**: Browse the complete collection in an organized grid layout
- **Add Tokimons**: Create new creatures with comprehensive form validation

### Adding a New Tokimon

1. Click "Add Tokimons" button
2. Fill in the creature details:
   - **Image URL**: Valid image URL (automatically validated)
   - **Name**: Alphabetic characters only
   - **Type**: Creature classification
   - **Rarity**: Numeric score (0-9)
3. Click "Submit" to save

### Managing Existing Tokimons

- **View Details**: Click "View" on any card to open detailed editor
- **Edit Information**: Modify attributes in the pop-up window
- **Delete Creatures**: Remove unwanted Tokimons with confirmation
- **Real-time Updates**: Changes reflect immediately in the main view

## 🔧 API Endpoints

The client communicates with the following REST endpoints:

| Method | Endpoint             | Description             |
| ------ | -------------------- | ----------------------- |
| GET    | `/tokimon/all`       | Retrieve all Tokimons   |
| POST   | `/tokimon/add`       | Create new Tokimon      |
| PUT    | `/tokimon/edit/{id}` | Update existing Tokimon |
| DELETE | `/tokimon/{id}`      | Delete Tokimon by ID    |

## 📁 Project Structure

```
src/
├── ca/cmpt213/asn5/client/
│   ├── ClientApplication.java    # Main application class
│   └── Tokimon.java             # Data model
├── fonts/
│   ├── VT323-Regular.ttf        # Custom gaming font
│   └── OFL.txt                  # Font license
├── images/
│   ├── backround.gif            # Animated background
│   └── tokimon_backround.png    # Static background option
├── META-INF/
│   └── MANIFEST.MF              # Application manifest
└── module-info.java             # Module configuration
```

## 🎨 UI/UX Features

### Visual Design

- **Retro Aesthetic**: VT323 font creates nostalgic gaming atmosphere
- **Consistent Styling**: Unified color scheme and component spacing
- **Visual Hierarchy**: Clear information architecture with proper emphasis
- **Accessibility**: High contrast ratios and readable font sizes

### User Experience

- **Intuitive Navigation**: Simple two-button interface for core functions
- **Immediate Feedback**: Real-time validation and status messages
- **Error Prevention**: Input restrictions and URL validation
- **Responsive Design**: Adaptive layouts for different content amounts

## 🔒 Input Validation & Security

### Client-Side Validation

- **Image URL Verification**: HTTP HEAD requests to validate image accessibility
- **Content-Type Checking**: Ensures URLs point to actual image files
- **Input Sanitization**: Regex patterns prevent invalid character entry
- **Form Validation**: Required field checking before submission

### Error Handling

- **Network Resilience**: Graceful handling of connection failures
- **User-Friendly Messages**: Clear error communication
- **Fallback Behaviors**: Appropriate responses to server unavailability

## 🚧 Development Features

### Code Quality

- **Comprehensive Documentation**: Detailed JavaDoc comments
- **Clean Architecture**: Well-organized class structure
- **Exception Handling**: Robust error management throughout
- **Type Safety**: Strong typing with generic collections

### Extensibility

- **Modular Design**: Easy to add new features or modify existing ones
- **Configurable Layouts**: Adjustable grid dimensions and spacing
- **Pluggable Validation**: Extensible validation framework
- **Theme Support**: Easy to modify colors and styling

## 📈 Performance Optimizations

- **Efficient Image Loading**: Lazy loading and caching for creature images
- **Minimal Network Calls**: Optimized API usage patterns
- **Memory Management**: Proper resource disposal and cleanup
- **UI Responsiveness**: Background processing for network operations

## 🤝 Contributing

This project was developed as part of CMPT 213 (Object-Oriented Programming) coursework, demonstrating advanced Java concepts including:

- JavaFX application development
- REST API client implementation
- JSON data serialization
- Event-driven programming
- Input validation strategies
- Modern UI/UX principles

**Created by Sanika and Gurveen**
