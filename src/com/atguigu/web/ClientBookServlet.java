package com.atguigu.web;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImp;
import com.atguigu.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ClientBookServlet extends BaseServlet {
    private BookService bookService = new BookServiceImp();

    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 0);
        pageNo += 1;
        //1、获取请求的参数==封装成Book对象
        Book book = WebUtils.copyParamToBean(request.getParameterMap(), new Book());

        //2、调用BookService.addBook()保存图书
        bookService.addBook(book);

        //3、跳转到图书列表页面，浏览器会记录最后一次请求的参数，用户刷新会造成重复提交数据当用户按下功能键 F5，就会发起浏览器记录的最后一次请求。
        //  /manager/bookServlet?action=list        不应该使用请求转发
        //分页之后全部修改为跳到分页
//        request.getRequestDispatcher("/manager/bookServlet?action=list").forward(request,response);
        //使用重定向
        response.sendRedirect(request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + pageNo);
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取请求的参数id，图书编程
//        String id = request.getParameter("id");
//        int i = 0;
//        try {
//            i = Integer.parseInt(id);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }

        int id = WebUtils.parseInt(request.getParameter("id"), 0);

        //2、调用bookServlet.deleteBookById();图书
        bookService.deleteBookById(id);
        //3、重定向回图书列表管理页面
        //  /book/manager/bookServlet?action=list
        //分页之后全部修改为跳到分页
        response.sendRedirect(request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + request.getParameter("pageNo"));
    }

    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取请求的参数==封装成Book对象
        Book book = WebUtils.copyParamToBean(request.getParameterMap(), new Book());
        //2、调用BookService.updateBook(book);修改屠苏
        bookService.updateBook(book);
        //3、重定向回图书列表管理页面
        //地址：/工程名/manager/bookServlet?action=list
        //分页之后全部修改为跳到分页
        response.sendRedirect(request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + request.getParameter("pageNo"));
    }

    protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取请求的参数图书编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        //2、调用bookService.queryBookById查询图书
        Book book = bookService.queryBookById(id);
        //3、保存图书擦Request域中
        request.setAttribute("book", book);
        //4、请求转发到。pages/manager/book_edit.jsp页面
        request.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(request, response);
    }

    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、通过BookServlet查询全部的图书
        List<Book> books = bookService.queryBooks();
        //2、把所有的图书保存草Request域中
        request.setAttribute("books", books);
        //3、请求转发到/pages/manager/bookmanager.jsp。页面中使用JSTL标签库进行遍历
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request, response);
    }

    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取请求的参数 pageNo和pageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        //2、调用BookService.page(pageNo,pageSize)
        Page<Book> page = bookService.page(pageNo,pageSize);

        page.setUrl("client/bookServlet?action=page");

        //3、保存Page对象到Request域中
        request.setAttribute("page",page);
        //4、请求转发到/pages/manager/book_manager.jsp
        request.getRequestDispatcher("/pages/client/index.jsp").forward(request, response);
    }

    protected void pageByPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取请求的参数 pageNo和pageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);

        int min = WebUtils.parseInt(request.getParameter("min"),0);
        int max = WebUtils.parseInt(request.getParameter("max"),Integer.MAX_VALUE);

        //2、调用BookService.page(pageNo,pageSize)
        Page<Book> page = bookService.pageByPrice(pageNo,pageSize,min,max);

        StringBuffer stringBuffer = new StringBuffer("client/bookServlet?action=pageByPrice");
        //如果有最小价格参数，追加到分页条的地址中
        if (request.getParameter("min") != null){
            stringBuffer.append("&min=").append(request.getParameter("min"));
        }
        if (request.getParameter("max") != null){
            stringBuffer.append("&max=").append(request.getParameter("max"));
        }

        page.setUrl(stringBuffer.toString());

        //3、保存Page对象到Request域中
        request.setAttribute("page",page);
        //4、请求转发到/pages/manager/book_manager.jsp
        request.getRequestDispatcher("/pages/client/index.jsp").forward(request, response);
    }
}
