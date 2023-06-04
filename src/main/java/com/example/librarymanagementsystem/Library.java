package com.example.librarymanagementsystem;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<LibraryManagementController.Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(LibraryManagementController.Book book) {
        books.add(book);
    }

    public void removeBook(LibraryManagementController.Book book) {
        books.remove(book);
    }

    public List<LibraryManagementController.Book> getAllBooks() {
        return books;
    }

}
