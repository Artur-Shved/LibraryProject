package com.library.library.interfaceDao;

import com.library.library.entity.Book;
import com.library.library.entity.User;

public interface AuditUser {
    boolean isUserAlredyCreated(String userName, String userSurName);
    boolean isUserAlredyCreated(long id);
    boolean isBookIsSame(Book a, Book b);
    boolean isUserNotNull(User user);
    boolean isUserFieldsNotNull(User user);
}
