package com.atguigu.test;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImp;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class BookServiceTest {

    private BookService bookService = new BookServiceImp();
    @Test
    public void addBook() {
        bookService.addBook(new Book(22,"爱学习","涵涵涵",new BigDecimal(999),999,9,null));
    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(22);
    }

    @Test
    public void updateBook() {
        bookService.updateBook(new Book(22,"不是很爱学习","涵涵涵",new BigDecimal(9999),999,99,null));
    }

    @Test
    public void queryBookById() {
        Book book = bookService.queryBookById(22);
        System.out.println(book);
    }

    @Test
    public void queryBooks() {
        List<Book> books = bookService.queryBooks();
        for (Book book : books) {
            System.out.println(book);
        }
    }

    @Test
    public void page(){
        System.out.println(bookService.page(1,Page.PAGE_SIZE));
    }

    @Test
    public void pageByPrice(){
        System.out.println(bookService.pageByPrice(1,Page.PAGE_SIZE,10,50));
    }
}