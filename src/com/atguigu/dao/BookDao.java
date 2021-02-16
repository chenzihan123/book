package com.atguigu.dao;

import com.atguigu.pojo.Book;

import java.util.List;

public interface BookDao {
    /**
     *
     * 添加图书
     * @param book 图书对象
     * @return
     */
    public int addBook(Book book);

    /**
     * 按照id删除图书
     * @param id 图书id
     * @return
     */
    public int deleteByBookId(Integer id);

    /**
     * 更新图书
     * @param book 图书对象
     * @return
     */
    public int updateBook(Book book);

    /**
     * 按照图书id查询图书
     * @param id 图书id
     * @return
     */
    public Book queryBookById(Integer id);

    /**
     * 查询图书列表
     * @return 图书列表
     */
    public List<Book> queryBooks();

    /**
     * 查询分页记录数
     * @return 分页记录数
     */
    Integer queryForPageTotalCount();

    /**
     * 查询分页数据
     * @param begin 开始索引
     * @param pageSize 分页条数
     * @return 分页数据列表
     */
    List<Book> queryForPageItems(int begin, int pageSize);

    /**
     * 按照价格查询分页记录数
     * @param min 最小价格
     * @param max 最大价格
     * @return 分页记录数
     */
    Integer queryForPageTotalCountByPrice(int min, int max);

    /**
     * 按照价格查询分页数据列表
     * @param begin 开始索引
     * @param pageSize 分页条数
     * @param min 最小价格
     * @param max 最大价格
     * @return 分页数据列表
     */
    List<Book> queryForPageItemsByPrice(int begin, int pageSize, int min, int max);
}
