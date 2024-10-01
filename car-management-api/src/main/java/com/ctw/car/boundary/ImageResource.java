package com.ctw.car.boundary;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Path("/uploads") // Make sure this matches your URL
public class ImageResource {

    private static final String UPLOAD_DIR = "uploads"; // Adjust this path based on your setup

    @GET
    @Path("/{image}")
    @Produces("image/*") // Allow for different image types
    public Response getImage(@PathParam("image") String imageName) {
        File imageFile = new File(UPLOAD_DIR, imageName);

        if (!imageFile.exists()) {
            return Response.status(Response.Status.NOT_FOUND).build(); // Return 404 if not found
        }

        try {
            InputStream imageStream = new FileInputStream(imageFile);
            return Response.ok(imageStream).type(getMimeType(imageName)).build(); // Return the image with correct MIME type
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build(); // Return 500 if there's an error
        }
    }

    private String getMimeType(String imageName) {
        if (imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (imageName.endsWith(".png")) {
            return "image/png";
        } else if (imageName.endsWith(".gif")) {
            return "image/gif";
        }
        return "application/octet-stream"; // Default if unknown
    }
}
