package com.campusflow.campusflow.tableview;
import com.campusflow.campusflow.EntityClass.Subject;
import com.campusflow.campusflow.database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class SubjectView {
    private TableColumn<Subject, String> semester;
    private TableColumn<Subject, String> Subject;
    private TableColumn<Subject, Integer> subId;
    private TableView<Subject> tableView;
    ObservableList<Subject> SubjectData() {
        Vector<Subject> SubjectVector = new Vector<Subject>();
        Integer subId;
        String subject;
        String semester;

        try {
            String sql = "Select * from subjects";
            PreparedStatement statement = Database.con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                subId = result.getInt("subId");
                semester = result.getString("semester");
                subject = result.getString("sub_name");

                Subject newSubject = new Subject(subId,semester, subject);
                SubjectVector.add(newSubject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(SubjectVector);
    }
    public SubjectView(TableView<Subject> SubjectTable, TableColumn<Subject, Integer> subId, TableColumn<Subject, String> semester,TableColumn<Subject, String> subject) throws SQLException{
        this.tableView = SubjectTable;
        this.subId = subId;
        this.semester = semester;
        this.Subject= subject;

        this.subId.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("subId"));
        this.semester.setCellValueFactory(new PropertyValueFactory<Subject, String>("semester"));
        this.Subject.setCellValueFactory(new PropertyValueFactory<Subject, String >("subject"));

        tableView.setItems(SubjectData());
    }
}
