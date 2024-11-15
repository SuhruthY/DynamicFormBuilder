
# Dynamic Form Generator

This project provides a service that generates HTML forms dynamically based on a given JSON input. It allows you to define form fields and their properties (e.g., text fields, dropdowns, checkboxes, etc.) through JSON, and then converts this data into an HTML form that can be used on a web page.

### Features:
- Dynamic HTML form generation based on JSON input.
- Supports various field types: `text`, `password`, `email`, `textarea`, `dropdown`, `checkbox`, `radio`, `button`.
- Automatic generation of form labels and placeholders.
- Customizable required fields and other attributes like `min`, `max`, and `checked`.
- An API endpoint to generate HTML forms from JSON requests.

---

## Table of Contents

- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [Project Structure](#project-structure)
- [Form JSON Format](#form-json-format)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Contributing](#contributing)

---

## Technologies Used

- **Java**: For backend logic and form generation.
- **Spring Boot**: Framework for building the REST API.
- **Jackson**: Library for parsing JSON into Java objects.
- **HTML/CSS**: For generating the form's front-end structure.
- **Maven/Gradle**: Dependency management and build tools.
- **Postman/Swagger**: For testing the REST API endpoints.

---

## Setup Instructions

Follow these steps to set up and run the project locally:

### Prerequisites:
- Java 8 or higher
- Maven or Gradle (for dependency management and build)
- IDE like IntelliJ IDEA, Eclipse, or Visual Studio Code
- Postman (optional, for testing API endpoints)

### Steps:

1. Clone this repository to your local machine:

   ```bash
   git clone https://github.com/<username>/dynamic-form-generator.git
   cd dynamic-form-generator
   ```

2. Import the project into your IDE as a **Maven** or **Gradle** project.

3. Build the project:

   **Using Maven**:
   ```bash
   mvn clean install
   ```

   **Using Gradle**:
   ```bash
   gradle build
   ```

4. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
   or
   ```bash
   gradle bootRun
   ```

5. The application will start on port `8080` by default. You can visit it in your browser or use Postman to test the API.

---

## Project Structure

```
src/
 ├── main/
 │    ├── java/
 │    │    ├── com/
 │    │    │    └── suhruth/
 │    │    │         └── dynamicformbuilder/
 │    │    │              ├── controller/
 │    │    │              │    └── FormController.java
 │    │    │              └── service/
 │    │    │                   └── FormGeneratorService.java
 │    └── resources/
 │         └── application.properties
 └── test/
      ├── java/
      │    └── com/
      │         └── suhruth/
      │              └── dynamicformbuilder/
      │                   └── FormGeneratorServiceTest.java
```

- **controller/FormController.java**: Contains the REST API endpoint to generate forms.
- **service/FormGeneratorService.java**: Contains the logic for converting JSON into HTML.
- **application.properties**: Configuration file for Spring Boot.

---

## Form JSON Format

To generate a dynamic form, you need to send a `POST` request with a JSON body to the `/api/forms/generate` endpoint. The structure of the JSON should look like this:

```json
{
  "formName": "User Registration",
  "fields": [
    {
      "type": "text",
      "name": "firstName",
      "label": "First Name",
      "required": true,
      "placeholder": "Enter your first name"
    },
    {
      "type": "email",
      "name": "email",
      "label": "Email",
      "required": true,
      "placeholder": "Enter your email"
    },
    {
      "type": "password",
      "name": "password",
      "label": "Password",
      "required": true,
      "placeholder": "Enter your password"
    },
    {
      "type": "checkbox",
      "name": "terms",
      "label": "Accept Terms",
      "required": true,
      "checked": true
    },
    {
      "type": "button",
      "name": "submit",
      "buttonType": "submit",
      "label": "Submit"
    }
  ]
}
```

### Fields:

- **type**: The type of input (e.g., `text`, `email`, `password`, `dropdown`, `checkbox`, `radio`, `button`).
- **name**: The name of the field, which will be used as the input's `name` attribute.
- **label**: The label for the field.
- **required**: Boolean indicating if the field is required.
- **placeholder**: Placeholder text for the field (optional).
- **options**: For `dropdown`, `radio`, or `checkbox` types, this contains an array of values.

---

## API Endpoints

### `POST /api/forms/generate`

This endpoint generates an HTML form from the provided JSON.

**Request Body**:

```json
{
  "formName": "Form Name",
  "fields": [
    { ... }
  ]
}
```

**Response**:

- On success, returns an HTML string of the generated form.
- On failure, returns a `400 Bad Request` with an error message if the JSON format is invalid.

---

## Testing

To test the API, you can use Postman or any similar tool to send a `POST` request to the endpoint `/api/forms/generate` with a JSON body as shown above.

You can also write unit tests for `FormGeneratorService` to ensure that the form generation logic works as expected. Use `JUnit` or `Mockito` for mocking dependencies and testing different form scenarios.

---

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-name`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add new feature'`).
5. Push to the branch (`git push origin feature-name`).
6. Open a pull request.

---

## License

This project is licensed under the MIT License – see the [LICENSE](https://github.com/SuhruthY/DynamicFormBuilder/blob/master/LICENSE) file for details.
