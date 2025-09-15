package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	@FXML
	private TextField nameField;

	@FXML
	private TextField idField;

	@FXML
	private TextField departmentField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField cgpaField;

	@FXML
	private TextField searchField;

	@FXML
	private TableView<Student> studentTable;

	@FXML
	private TableColumn<Student, String> nameColumn;

	@FXML
	private TableColumn<Student, String> idColumn;

	@FXML
	private TableColumn<Student, String> departmentColumn;

	@FXML
	private TableColumn<Student, String> emailColumn;

	@FXML
	private TableColumn<Student, Double> cgpaColumn;


	private ObservableList<Student> studentList = FXCollections.observableArrayList();
	private ObservableList<Student> filteredList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		cgpaColumn.setCellValueFactory(new PropertyValueFactory<>("cgpa"));

		studentTable.setItems(studentList);
	}

	@FXML
	private void addStudent() {
		if (validateFields()) {
			String name = nameField.getText();
			String id = idField.getText();
			String department = departmentField.getText();
			String email = emailField.getText();
			double cgpa = Double.parseDouble(cgpaField.getText());

			Student student = new Student(name, id, department, email, cgpa);
			studentList.add(student);

			clearFields();
		} else {
			showAlert("Error", "All fields must be filled!");
		}
	}
	@FXML
	private void addStudent1() {
		if (validateFields()) {
			String name = nameField.getText();
			String id = idField.getText();
			String department = departmentField.getText();
			String email = emailField.getText();
			double cgpa = Double.parseDouble(cgpaField.getText());

			Student student = new Student(name, id, department, email, cgpa);
			studentList.add(student);

			clearFields();
		} else {
			showAlert("Error", "All fields must be filled!");
		}
	}
	

	@FXML
	private void updateStudent() {
		Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
		if (selectedStudent != null && validateFields()) {
			selectedStudent.setName(nameField.getText());
			selectedStudent.setId(idField.getText());
			selectedStudent.setDepartment(departmentField.getText());
			selectedStudent.setEmail(emailField.getText());
			selectedStudent.setCgpa(Double.parseDouble(cgpaField.getText()));

			studentTable.refresh();
			clearFields();
		} else if (selectedStudent == null) {
			showAlert("Error", "Please select a student to update!");
		} else {
			showAlert("Error", "All fields must be filled!");
		}
	}

	@FXML
	private void deleteStudent() {
		Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
		if (selectedStudent != null) {
			studentList.remove(selectedStudent);
			clearFields();
		} else {
			showAlert("Error", "Please select a student to delete!");
		}
	}

	@FXML
	private void searchStudent() {
		String searchText = searchField.getText().toLowerCase();
		if (searchText.isEmpty()) {
			studentTable.setItems(studentList);
		} else {
			filteredList.clear();
			for (Student student : studentList) {
				if (student.getName().toLowerCase().contains(searchText)
						|| student.getId().toLowerCase().contains(searchText)
						|| student.getDepartment().toLowerCase().contains(searchText)) {
					filteredList.add(student);
				}
			}
			studentTable.setItems(filteredList);
		}
	}

	@FXML
	private void selectStudent() {
		Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
		if (selectedStudent != null) {
			nameField.setText(selectedStudent.getName());
			idField.setText(selectedStudent.getId());
			departmentField.setText(selectedStudent.getDepartment());
			emailField.setText(selectedStudent.getEmail());
			cgpaField.setText(String.valueOf(selectedStudent.getCgpa()));
		}
	}

	private boolean validateFields() {
		try {
			return !nameField.getText().isEmpty() && !idField.getText().isEmpty()
					&& !departmentField.getText().isEmpty() && !emailField.getText().isEmpty()
					&& !cgpaField.getText().isEmpty() && Double.parseDouble(cgpaField.getText()) >= 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void clearFields() {
		nameField.clear();
		idField.clear();
		departmentField.clear();
		emailField.clear();
		cgpaField.clear();
		searchField.clear();
		studentTable.setItems(studentList);
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
}