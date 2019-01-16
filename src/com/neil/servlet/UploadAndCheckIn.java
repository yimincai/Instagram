package com.neil.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "/UploadAndCheckIn", urlPatterns = {"/UploadAndCheckIn"})
public class UploadAndCheckIn extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String DATA_DIRECTORY = "/data";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 20;
    private static final int MAX_RESOURCE_SIZE = 1024 * 1024;
    private static FileItem item = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //check that we have a file upload request
        boolean isMutipart = ServletFileUpload.isMultipartContent(request);

        if (!isMutipart) {
            return;
        }

        //create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //sets the size threshold beyond which files are written directly to disk
        factory.setSizeThreshold(MAX_MEMORY_SIZE);

        //constructs the folder there uploaded file will be store
        String uploadFolder = getServletContext().getRealPath("") +
                File.separator + DATA_DIRECTORY;

        FileItemFactory fileItemFactory = new DiskFileItemFactory();

        //sets the directory used to temporally store files that are larger
        //than the configured size threshold. we use temporary directory for java
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
        //set overall request size constraint
        upload.setSizeMax(MAX_RESOURCE_SIZE);
        try {
            //parse the request
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();

            while (iter.hasNext()) {
                item = (FileItem) iter.next();
            }

            if (!item.isFormField()) {
                String fileName = new File(item.getName()).getName();
                String filePath = uploadFolder + File.separator + fileName;
                File uploadedFile = new File(filePath);
                System.out.println(filePath);
                //saves the file to upload directory
                item.write(uploadedFile);
            }

            //displays done.jsp pafe after upload finished
            getServletContext().getRequestDispatcher("/done.jsp").forward(request, response);
        } catch (FileUploadException e) {
            throw new ServletException(e);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
