/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import my.common.DatabaseUtil;

/**
 *
 * @author ADMIN
 */
public class EditServlet extends HttpServlet {

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
            String method = request.getMethod();
            System.out.println("method" + method);
            if (method.equalsIgnoreCase("get")) {
                String id = request.getParameter("id");
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    conn = DatabaseUtil.getConnection();
                // System.out.println("Ket noi OK");
                //3.Tạo đối tượng thi hành truy vấn
                    ps = conn.prepareStatement("select * from users where id=" + id);
                //truyền giá trị cho các tham số trong câu lệnh SQL
                // ps.setInt(1, Integer.parseInt(id));
                //4.Thi hành truy vấn
                    rs = ps.executeQuery();
                //5.Xu ly ket qua tra ve
                    if (rs.next()) {
                        /* TODO output your page here. You may use following sample code. */
                        String country = rs.getString(5); // Giả sử cột quốc gia là cột thứ 5
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Update user</title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1>Update User</h1>");
                        out.println("<form action='EditServlet' method='POST'>"
                                + "<input type='hidden' name='id' value=" + id + ">"
                                + "<table border='0'>"
                                + "<tr>"
                                + "<td>Name</td>"
                                + "<td><input type='text' name='uname' value=" + rs.getString(2) + " required /></td>"
                                + "</tr>"
                                + "<tr>"
                                + "<td>Password</td>"
                                + "<td><input type='password' name='upass' value=" + rs.getString(3) + " required /></td>"
                                + "</tr>"
                                + "<tr>"
                                + "<td>Email</td>"
                                + "<td><input type='email' name='email' value=" + rs.getString(4) + " /></td>"
                                + "</tr>"
                                + "<tr>"
                                + "<td>Country</td>"
                                + "<td>"
                                + "<select name='country'>"
                                + " <option value='Vietnam' " + (country.equals("'VietNam") ? "selected" : "") + ">Vietnam</option>"
                                + " <option value='USA'  " + (country.equals("'USA") ? "selected" : "") + ">USA</option>"
                                + " <option value='UK'  " + (country.equals("'UK") ? "selected" : "") + ">UK</option>"
                                + " <option value='Other' " + (country.equals("'Other") ? "selected" : "") + ">Other</option>"
                                + "</select>"
                                + "</td>"
                                + "</tr>"
                                + "<tr>"
                                + "<td colspan=2><input type='submit' value='Edit & Save' /></td>"
                                + "</tr>"
                                + "</table>"
                                + "</form>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                    //6.dong ket noi
                    conn.close();
                } catch (Exception e) {
                    System.out.println("Loi:" + e.toString());
                    out.println("<h2>Thao tác xoá user thất bại</h2>");
                }
            } else if (method.equalsIgnoreCase("post")) //xu ly theo method POST
            {
                //b1. lấy giá trị tham số từ client
                String uname = request.getParameter("uname");
                String upass = request.getParameter("upass");
                String email = request.getParameter("email");
                String country = request.getParameter("country");
                String id = request.getParameter("id");
                //b2. Xử lý yêu cầu (truy cập CSDL để thêm mới user)
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = DatabaseUtil.getConnection();
                    // System.out.println("Ket noi OK");
                    //3.Tạo đối tượng thi hành truy vấn
                    ps = conn.prepareStatement("update users set name=?, password=?,email=?, country=? where id=?");
                    //truyền giá trị cho các tham số trong câu lệnh SQL
                    ps.setString(1, uname);
                    ps.setString(2, upass);
                    ps.setString(3, email);
                    ps.setString(4, country);
                    ps.setInt(5, Integer.parseInt(id));
                    //4.Thi hành truy vấn
                    int kq = ps.executeUpdate();
                    //5.Xu ly ket qua tra ve
                    if (kq > 0) {
                        out.println("<h2>Cập nhật user thành công</h2>");
                    } else {
                        out.println("<h2>Cap nhat user thất bại</h2>");

                    }
                    //6.dong ket noi
                    conn.close();
                } catch (Exception e) {
                    System.out.println("Loi:" + e.toString());
                    out.println("<h2>Cap nhat user thất bại</h2>");
                }
//chèn nội dung của ViewServlet vào kết quả phản hồi
                request.getRequestDispatcher("ViewServlet").include(request, response);
            }
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditServlet at " + request.getContextPath() + "</h1>");
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
