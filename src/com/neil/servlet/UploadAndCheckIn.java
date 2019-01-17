package com.neil.servlet;

import com.neil.util.ConnectionManager;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;


/**
 * Servlet implementation class UploadServlet
 */
@WebServlet(name = "/UploadServlet", urlPatterns = {"/UploadAndCheckIn"})
public class UploadAndCheckIn extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 上傳儲存目錄
    private static final String UPLOAD_DIRECTORY = "images";

    // 上傳內容設定
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 30;  // 30MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

    // 查詢post_id & insert content to DB設定
    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;
    private static int MAX_ID;
    private static String content;

    /**
     * 上傳數據及保存文件
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //查詢max(post_id)
        try {
            conn = ConnectionManager.getConnection();
            String sqlforId = "SELECT max(post_id) FROM `post`";
            pstmt = conn.prepareStatement(sqlforId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MAX_ID = Integer.parseInt(rs.getString("max(post_id)"));
            }
            MAX_ID++;
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        // 若為多文件上傳，停止
        if (!ServletFileUpload.isMultipartContent(request)) {
            return;
        }

        // 設定上傳參數
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 設定儲存臨界值 - 超過後將產生臨時文件並儲存於臨時目錄中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 設定臨時儲存目錄
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 設定文件最大上傳值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 設定最大請求值 (包含文件及檔案數據)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文處理
        upload.setHeaderEncoding("UTF-8");

        // 建構臨時路徑來儲存上傳的文件
        // 這個路徑相對當前應用的目錄
        String uploadPath = getServletContext().getRealPath("/") + File.separator + UPLOAD_DIRECTORY;


        // 如果目錄不存在則創建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            // 解析請求的內容提取文件數據
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                // 迭代表單數據
                for (FileItem item : formItems) {
                    // 處理不在表單中的字串

                    if (item.isFormField()) {
                        content = item.getString();
                    }

                    if (!item.isFormField()) {
                        String fullFileName = new File(item.getName()).getName();
                        String[] subFileName = fullFileName.split("\\.");
                        String fileName = String.valueOf(MAX_ID);
                        String filePath = uploadPath + File.separator + fileName + "." + subFileName[1];
                        File storeFile = new File(filePath);
                        // 輸出上傳文件路徑
                        System.out.println("Save file to : " + filePath);
                        // 儲存文件到路徑
                        item.write(storeFile);
                        request.setAttribute("message", "文件上傳成功!");
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message",
                    "錯誤訊息: " + ex.getMessage());
        }


        //start to insert content to DB
        HttpSession session = request.getSession();
        String username = String.valueOf(session.getAttribute("username"));
        String userId = String.valueOf(session.getAttribute("id"));

        if (username.isEmpty() || userId.isEmpty()) {
            response.sendRedirect("index.jsp?errMsg=1");
        } else {

            try {
                System.out.println("PosterID : " + userId);
                System.out.println("PosterUsername : " + username);
                System.out.println("The content : " + content);

                String sql = "INSERT INTO `post`(`post_id`, `poster`, `content`, `liked`, `enable`, `posted_timestamp`) VALUES (?, ?, ?, ?, ?, ?)";
                conn = ConnectionManager.getConnection();
                pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, null);
                pstmt.setString(2, userId);
                pstmt.setString(3, content);
                pstmt.setString(4, "0");
                pstmt.setString(5, "1");
                pstmt.setString(6, null);
                pstmt.executeUpdate();

                response.sendRedirect("protected/HomePage.jsp");

                pstmt.close();
                conn.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
}