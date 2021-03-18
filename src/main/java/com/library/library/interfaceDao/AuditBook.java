package com.library.library.interfaceDao;

import com.library.library.entity.Book;

public interface AuditBook {
    boolean isBookCreatedById(long id);
    boolean isBookFreeToTake(Book book);
    boolean isBookCreated(String bookName, String authorName, String authorSurName);
    boolean isBookElementsNotNull(Book book);
    boolean isBookNotNull(Book book);

}
