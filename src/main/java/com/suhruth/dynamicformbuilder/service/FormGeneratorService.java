package com.suhruth.dynamicformbuilder.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class FormGeneratorService {

    public String generateHtmlForm(String formJson) throws Exception {
        // Parse JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode formNode = objectMapper.readTree(formJson);

        StringBuilder htmlBuilder = new StringBuilder();
        
        //styles
        htmlBuilder.append("""
        <style>		
        	form { 
		        font-family: Arial, sans-serif; 
		        padding: 20px; 
		        background-color: #f9f9f9; 
		        border-radius: 5px; 
		        width: 300px; 
		        margin: 0 auto; 
		    }
		    label { 
		        display: block; 
		        margin-bottom: 8px; 
		        font-weight: bold; 
		    }
		    input:not([type='checkbox'], [type='radio']), select { 
		        width: 100%; 
		        padding: 8px; 
		        margin-bottom: 15px; 
		        border: 1px solid #ccc; 
		        border-radius: 4px; 
		    }
		    button { 
		        padding: 10px 15px; 
		        background-color: #4CAF50; 
		        color: white; 
		        border: none; 
		        border-radius: 4px; 
		        cursor: pointer; 
		    }
		    button:hover { 
		        background-color: #45a049; 
		    }
         </style>
            """);
   

        // Extract form metadata
        String formName = formNode.get("formName").asText();
        htmlBuilder.append("<form id=\"")
                   .append(formName.toLowerCase().replace(" ", "-"))
                   .append("\">");

        // Generate HTML for each field
        for (JsonNode field : formNode.get("fields")) {
            htmlBuilder.append(generateFieldHtml(field));
            htmlBuilder.append("<br>");
        }

        // Add submit button if not explicitly defined
        if (!formNode.has("submitButton") || formNode.get("submitButton").asBoolean(true)) {
            htmlBuilder.append("<button type=\"submit\">Submit</button>");
        }

        htmlBuilder.append("</form>");
        return htmlBuilder.toString();
    }

    private String generateFieldHtml(JsonNode field) {
        StringBuilder fieldHtml = new StringBuilder();
        String type = field.get("type").asText();
        String name = field.get("name").asText();
        String label = field.has("label") ? field.get("label").asText() : "";
        boolean required = field.has("required") && field.get("required").asBoolean();


        // Generate field HTML based on type
        switch (type) {
            case "text":
            case "password":
            case "email":
            case "url":
            case "number":
            case "range":
            case "date":
            case "time":
            case "datetime-local":
            case "hidden":
            case "file":
            	// Append label if present
                if (!label.isEmpty()) {
                    fieldHtml.append("<label for=\"")
                             .append(name)
                             .append("\">")
                             .append(label)
                             .append("</label>");
                }
                fieldHtml.append("<input type=\"")
                         .append(type)
                         .append("\" name=\"")
                         .append(name)
                         .append("\" id=\"")
                         .append(name)
                         .append("\"");
                if (required) fieldHtml.append(" required");
                if (field.has("placeholder")) {
                    fieldHtml.append(" placeholder=\"")
                             .append(field.get("placeholder").asText())
                             .append("\"");
                }
                if (field.has("min")) {
                    fieldHtml.append(" min=\"")
                             .append(field.get("min").asText())
                             .append("\"");
                }
                if (field.has("max")) {
                    fieldHtml.append(" max=\"")
                             .append(field.get("max").asText())
                             .append("\"");
                }
                fieldHtml.append(" />");
                break;

            case "textarea":
            	// Append label if present
                if (!label.isEmpty()) {
                    fieldHtml.append("<label for=\"")
                             .append(name)
                             .append("\">")
                             .append(label)
                             .append("</label>");
                }
                fieldHtml.append("<textarea name=\"")
                         .append(name)
                         .append("\" id=\"")
                         .append(name)
                         .append("\"");
                if (required) fieldHtml.append(" required");
                if (field.has("placeholder")) {
                    fieldHtml.append(" placeholder=\"")
                             .append(field.get("placeholder").asText())
                             .append("\"");
                }
                fieldHtml.append(">");
                if (field.has("defaultValue")) {
                    fieldHtml.append(field.get("defaultValue").asText());
                }
                fieldHtml.append("</textarea>");
                break;

            case "dropdown":
            	// Append label if present
                if (!label.isEmpty()) {
                    fieldHtml.append("<label for=\"")
                             .append(name)
                             .append("\">")
                             .append(label)
                             .append("</label>");
                }
                fieldHtml.append("<select name=\"")
                         .append(name)
                         .append("\" id=\"")
                         .append(name)
                         .append("\"");
                if (required) fieldHtml.append(" required");
                fieldHtml.append(">");
                for (JsonNode option : field.get("options")) {
                    fieldHtml.append("<option value=\"")
                             .append(option.asText())
                             .append("\">")
                             .append(option.asText())
                             .append("</option>");
                }
                fieldHtml.append("</select>");
                break;
                
            case "checkbox":
            	//open label
            	 if (!label.isEmpty()) {
                     fieldHtml.append(" <label for=\"")
                              .append(name)
                              .append("\">");
                 }
                fieldHtml.append("<input type=\"checkbox\" name=\"")
                         .append(name)
                         .append("\" id=\"")
                         .append(name)
                         .append("\"");
                if (required) fieldHtml.append(" required");
                if (field.has("checked") && field.get("checked").asBoolean()) {
                    fieldHtml.append(" checked");
                }
                fieldHtml.append(" />");
                //close label
                if (!label.isEmpty()) {
                    fieldHtml.append(label)
                             .append("</label>");
                }
                break;

            case "radio":
            	if (!label.isEmpty()) {
                    fieldHtml.append("<label for=\"")
                             .append(name)
                             .append("\">")
                             .append(label)
                             .append("</label>");
                }
                for (JsonNode option : field.get("options")) {
                    String value = option.asText();
                    fieldHtml .append("<label for=\"")
		                     .append(name + "-" + value.toLowerCase())
		                     .append("\">")
		                     .append("<input type=\"radio\" name=\"")
                             .append(name)
                             .append("\" id=\"")
                             .append(name + "-" + value.toLowerCase())
                             .append("\" value=\"")
                             .append(value)
                             .append("\"");
                    if (required) fieldHtml.append(" required");
                    fieldHtml.append(" />")
                             .append(value)
                             .append("</label>");
                }
                break;

            case "button":
            	if(!"submit".equals(name)) {
	                String buttonType = field.has("buttonType") ? field.get("buttonType").asText("button") : "button";
	                fieldHtml.append("<button type=\"")
	                         .append(buttonType)
	                         .append("\" name=\"")
	                         .append(name)
	                         .append("\">")
	                         .append(label.isEmpty() ? "Button" : label)
	                         .append("</button><br>");
            	}
                break;

            default:
                throw new IllegalArgumentException("Unsupported field type: " + type);
        }

        return fieldHtml.toString();
    }
}