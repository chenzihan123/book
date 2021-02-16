package com.atguigu.test;

import com.atguigu.dao.BookDao;
import com.atguigu.dao.impl.BookDaoImp;
import com.atguigu.pojo.Book;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class BookDaoTest {

    private BookDao bookDao = new BookDaoImp();

    @Test
    public void addBook() {
        if (bookDao.addBook(new Book(null,"我爱学Java","子涵",new BigDecimal(9999),999,10,null)) > 0){
            System.out.println("添加成功!");
        }
    }

    @Test
    public void deleteBookId() {
        if (bookDao.deleteByBookId(21) > 0){
            System.out.println("删除成功!");
        }
    }

    @Test
    public void updateBook() {
        if (bookDao.updateBook(new Book(21,"我不是很爱爱学Java","陈子涵",new BigDecimal(9999),999,10,null)) > 0){
            System.out.println("添加成功!");
        }
    }

    @Test
    public void queryBookById() {
        Book book = bookDao.queryBookById(21);
        if (book != null){
            System.out.println("查询成功!");
            System.out.println(book);
        }
    }

    @Test
    public void queryBooks() {
        List<Book> books = bookDao.queryBooks();
        if (books != null){
            System.out.println("查询成功!");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }
    @Test
    public void queryForPageTotalCount() {
        System.out.println(bookDao.queryForPageTotalCount());
    }

    @Test
    public void queryForPageItems(){
        List<Book> books = bookDao.queryForPageItems(8, 4);
//        System.out.println(books);
        for (Book book : books) {
            System.out.println(book);
        }
    }
    @Test
    public void queryForPageTotalCountByPrice() {
        System.out.println(bookDao.queryForPageTotalCountByPrice(10,50));
    }

    @Test
    public void queryForPageItemsByPrice(){
        List<Book> books = bookDao.queryForPageItemsByPrice(2, 4,10,50);
        for (Book book : books) {
            System.out.println(book);
        }
    }
}