/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
public class ViewServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
      
        //b2. Xử lý yêu cầu (truy cập CSDL để thêm mới user)
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String data = "";
        try {

            //1.Nạp driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //System.out.println("Nap driver OK");
            //2.Thiết lập kết nối CSDL
            conn = DriverManager.getConnection("jdbc:sqlserver://pc319;databaseName=demodb", "sa", "sa");
            //System.out.println("Ket noi OK");
            //3. Tạo đối tượng thi hành truy vána
            ps = conn.prepareStatement("select * from users");
            //Truyển giá trị có các tham số tronh cầu lệnh 
            //4.Thi hành truy vấn
           rs = ps.executeQuery();
           
            //5. Xử lý kết quả trả về
            data += "<table border =1>";
            data += "<tr><th>Id</th><th>Name</th><th>Password</th><th>Email</th><th>Country</th><th>Edit</th><th>Delete</th></tr>";
            while ( rs.next())
            {
                data += "<tr>";
                data += "<td>" + rs.getInt(1) + "</td>";
                data += "<td>" + rs.getString(2) + "</td>";
                data += "<td>" + rs.getString(3) + "</td>";
                data += "<td>" + rs.getString(4) + "</td>";
                data += "<td>" + rs.getString(5) + "</td>";
                data += "<td><a href = EditServlet?id=" + rs.getInt(1) +">Edit</a></td>";
                data += "<td><a href = DeleteServlet?id=" + rs.getInt(1) +">Delete</a></td>";
                data += "</tr>";
                
           }
            data += "</table>";
            //6.đong ket noi
            conn.close();
        } catch (Exception e) {
            System.out.println("Loi:" + e.toString());
            out.println("<h2> Thêm user thất bại </h2>");
        }
      //  request.getRequestDispatcher("index.html").include(request, response);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<a href = index.html> Add New Users</a>");
            out.println("<h1>Users List</h1>");
            out.println(data);
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
