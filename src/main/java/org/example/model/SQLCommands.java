package org.example.model;

public interface SQLCommands {
    String SQL_CREATE_TABLE_DEPARTMENT = "CREATE TABLE IF NOT EXISTS Department " +
            "(id_of_department SERIAL PRIMARY KEY, name_of_department VARCHAR(86) UNIQUE NOT NULL);";
    String SQL_CREATE_TABLE_EMPLOYEE = "CREATE TABLE IF NOT EXISTS Employee " +
            "(id_of_employee SERIAL PRIMARY KEY, id_department INT, date_of_birth VARCHAR(12) NOT NULL, CONSTRAINT id_department_foreign_key FOREIGN KEY (id_department) REFERENCES Department(id_of_department) ON DELETE SET NULL, name_of_employee VARCHAR(34) NOT NULL UNIQUE, date_of_employment VARCHAR(15), position_of_employee VARCHAR(86) NOT NULL, salary INT NOT NULL);";
    String SQL_CREATE_TABLE_SUBORDINATION = "CREATE TABLE IF NOT EXISTS Subordination " +
            "(id SERIAL PRIMARY KEY, id_of_manager INT NOT NULL, id_of_subject INT NOT NULL, CONSTRAINT id_of_manager_foreign_key FOREIGN KEY (id_of_manager) REFERENCES Employee (id_of_employee) ON DELETE SET NULL, CONSTRAINT id_of_subject_foreign_key FOREIGN KEY (id_of_subject) REFERENCES Employee (id_of_employee) ON DELETE SET NULL);";
    String SQL_CREATE_TABLE_OTHER = "CREATE TABLE IF NOT EXISTS Other " +
            "(id SERIAL PRIMARY KEY, id_of_other_employee INT NOT NULL, CONSTRAINT id_of_other_employee_foreign_key FOREIGN KEY (id_of_other_employee) REFERENCES Employee(id_of_employee) ON DELETE CASCADE, additional_info VARCHAR(255));";
    String SQL_INSERT_INTO_DEPARTMENT = "INSERT INTO Department (name_of_department) VALUES ('DepartmentA'), ('DepartmentB'), ('DepartmentC')";
    String SQL_INSERT_INTO_EMPLOYEE = "INSERT INTO Employee (id_department, date_of_birth, name_of_employee, date_of_employment, position_of_employee, salary) VALUES " +
            "(2, '08.09.69', 'Jael F. Ballard','09.09.20', 'FrontendDev', 4000), (1, '15.09.98', 'Maxwell J. Brady','28.03.21', 'BackendDev', 4500), (1, '05.07.96', 'Gregory Ratliff','04.06.22', 'BackendDev', 4600), (1, '15.12.87','Rina O. Madden','08.05.22', 'DevOps', 3200), (2, '12.08.78', 'Philip C. Cline','09.02.21', 'QA', 3500), (3, '09.08.88', 'Uta Rose','04.01.22', 'Team-Lead', 5000), (2, '30.02.89', 'Derek C. Sanders','04.12.21', 'Fullstack', 4600), (3, '17.04.87', 'Samantha L. Wood','10.06.21', 'Team-Lead', 5000), (3,'01.08.98', 'Leandra Hayden','24.08.21', 'BackendDev', 4700), (3,'18.11.78', 'Daniel F. Travis','03.01.21', 'Fullstack', 4500), (1, '08.09.86', 'Kate U. Winson', '08.09.89', 'Secretary', 1200), (2, '31.09.86', 'David U. Nilson', '08.09.86', 'Admin', 2700)";
    String SQL_INSERT_INTO_SUBORDINATION = "INSERT INTO SUBORDINATION (id_of_manager, id_of_subject) VALUES " +
            "(2, 3), (2, 4), (7, 1), (7, 5), (8, 6), (8, 9), (8, 10), (2, 11), (7, 12);";
    String SQL_INSERT_INTO_OTHER = "INSERT INTO OTHER (id_of_other_employee, additional_info) VALUES" +
            "(1, 'Helps John'), (11, 'Gets cups of coffee and prepare documentations'), (12, 'Manages the system');";
    String SQL_SELECT_ALL_EMPLOYEES_SURNAME_ASC = "SELECT * FROM employee e JOIN department d ON d.id_of_department = e.id_department LEFT OUTER JOIN subordination s ON s.id_of_subject = e.id_of_employee LEFT OUTER JOIN other o ON o.id_of_other_employee = e.id_of_employee ORDER BY name_of_employee ASC;";
    String SQL_SELECT_ALL_EMPLOYEES_DATE_OF_EMPLOYMENT_ASC = "SELECT * FROM employee e JOIN department d ON d.id_of_department = e.id_department LEFT OUTER JOIN subordination s ON s.id_of_subject = e.id_of_employee LEFT OUTER JOIN other o ON o.id_of_other_employee = e.id_of_employee ORDER BY date_of_employment ASC";
    String SQL_SELECT_AMOUNT_OF_EMPLOYEES = "SELECT COUNT(*) FROM Employee;";
    String SQL_SELECT_JOIN_FULL_TABLE = "SELECT * FROM employee e JOIN department d ON d.id_of_department = e.id_department LEFT OUTER JOIN subordination s ON s.id_of_subject = e.id_of_employee LEFT OUTER JOIN other o ON o.id_of_other_employee = e.id_of_employee ";
    String SQL_DELETE = "DELETE FROM Employee where id_of_employee = ";
 //   String SQL_UPDATE_POSITION = "UPDATE Subordination set id_of_manager = {id_of_manager} where id = "; /**SUBORDINATION**/
}
