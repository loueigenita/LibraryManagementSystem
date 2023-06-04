package com.example.librarymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class LibraryManagementController {

    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> returnDateCol;
    @FXML
    private TableColumn<Book, String> borrowedDateCol;
    @FXML
    private TableColumn<Book, String> borrowerNameCol;
    @FXML
    private TableColumn<Book, String> genreCol;
    @FXML
    private TableColumn<Book, String> bookNameCol;
    @FXML
    private ComboBox<String> genreComboBox;
    @FXML
    private TextField bookNameField;
    @FXML
    private TextField borrowerNameField;
    @FXML
    private DatePicker borrowedDatePicker;
    @FXML
    private DatePicker returnDatePicker;
    private Book selectedBook;
    public LibraryManagementController() {
        // No-argument constructor
    }
    private ObservableList<Book> bookList;

    public LibraryManagementController(TableView<Book> tableView, TableColumn<Book, String> returnDateCol, TableColumn<Book, String> borrowedDateCol, TableColumn<Book, String> borrowerNameCol, TableColumn<Book, String> genreCol, TableColumn<Book, String> bookNameCol) {
        this.tableView = tableView;
        this.returnDateCol = returnDateCol;
        this.borrowedDateCol = borrowedDateCol;
        this.borrowerNameCol = borrowerNameCol;
        this.genreCol = genreCol;
        this.bookNameCol = bookNameCol;
    }


    public void initialize() {
        bookNameCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        borrowerNameCol.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));
        borrowedDateCol.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        returnDateCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        genreComboBox.getItems().addAll(
                "Fantasy", "Romance", "Mystery", "Science Fiction", "Thriller",
                "Horror", "Educational", "Sports", "Thesis", "Historical"
        );

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedBook = newSelection;
                populateFormFields(selectedBook);
            }
        });

        bookList = FXCollections.observableArrayList();
        tableView.setItems(bookList);
    }

    @FXML
    private void addBook() {
        String bookName = bookNameField.getText();
        String genre = genreComboBox.getValue();
        String borrowerName = borrowerNameField.getText();
        LocalDate borrowedDate = borrowedDatePicker.getValue();
        LocalDate returnDate = returnDatePicker.getValue();

        Book newBook = new Book(bookName, genre, borrowerName, borrowedDate, returnDate);
        bookList.add(newBook);
        clearForm();
    }

    @FXML
    private void updateBook() {
        if (selectedBook != null) {
            selectedBook.setBookName(bookNameField.getText());
            selectedBook.setGenre(genreComboBox.getValue());
            selectedBook.setBorrowerName(borrowerNameField.getText());
            selectedBook.setBorrowedDate(borrowedDatePicker.getValue());
            selectedBook.setReturnDate(returnDatePicker.getValue());
            // Update the book in the bookList
            int index = bookList.indexOf(selectedBook);
            if (index >= 0) {
                bookList.set(index, selectedBook);
                tableView.refresh(); // Refresh the TableView to reflect the changes
                clearForm();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Book Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a book to update.");
            alert.showAndWait();
        }
    }



    @FXML
    private void deleteBook() {
        if (selectedBook != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this book?");
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    bookList.remove(selectedBook);
                    clearForm();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Book Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a book to delete.");
            alert.showAndWait();
        }
    }

    private void populateFormFields(Book book) {
        bookNameField.setText(book.getBookName());
        genreComboBox.setValue(book.getGenre());
        borrowerNameField.setText(book.getBorrowerName());
        borrowedDatePicker.setValue(book.getBorrowedDate());
        returnDatePicker.setValue(book.getReturnDate());
    }

    private void clearForm() {
        tableView.getSelectionModel().clearSelection();
        bookNameField.clear();
        genreComboBox.getSelectionModel().clearSelection();
        borrowerNameField.clear();
        borrowedDatePicker.setValue(null);
        returnDatePicker.setValue(null);
        selectedBook = null;
    }

    public static class Book {
        private String bookName;
        private String genre;
        private String borrowerName;
        private LocalDate borrowedDate;
        private LocalDate returnDate;

        public Book(String bookName, String genre, String borrowerName, LocalDate borrowedDate, LocalDate returnDate) {
            this.bookName = bookName;
            this.genre = genre;
            this.borrowerName = borrowerName;
            this.borrowedDate = borrowedDate;
            this.returnDate = returnDate;
        }

        // Getters and setters

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public String getBorrowerName() {
            return borrowerName;
        }

        public void setBorrowerName(String borrowerName) {
            this.borrowerName = borrowerName;
        }

        public LocalDate getBorrowedDate() {
            return borrowedDate;
        }

        public void setBorrowedDate(LocalDate borrowedDate) {
            this.borrowedDate = borrowedDate;
        }

        public LocalDate getReturnDate() {
            return returnDate;
        }

        public void setReturnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
        }
    }
}