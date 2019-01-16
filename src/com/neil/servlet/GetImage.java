package com.neil.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(name = "GetImage", urlPatterns = {"/getImage"})
public class GetImage extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/jpeg");
        System.out.println(System.getProperty("java.io.tmpdir"));
        ServletContext sc = getServletContext();
        InputStream is = sc.getResourceAsStream("images/aa.jpg");

        BufferedImage bi = ImageIO.read(is);
        OutputStream os = response.getOutputStream();

        ImageIO.write(bi, "jpg", os);

//        response.setContentType("image/jpeg");
//        OutputStream out = response.getOutputStream();
//        FileInputStream in = new FileInputStream("C:\\Users\\bravc\\IdeaProjects\\Instagram\\web\\images\\aa.jpg");
//        int size = in.available();
//        byte[] content = new byte[size];
//        in.read(content);
//        out.write(content);
//        in.close();
//        out.close();


    }
}
