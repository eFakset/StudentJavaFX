package no.getacademy.studentdemo.io;

import java.sql.*;
import java.util.*;

import no.getacademy.studentdemo.beans.*;

public class MySQLProvider 
{
    private final Connection                connection;
    private TreeSet<Teacher>          teachers;
    private TreeSet<Level>            levels;


    public MySQLProvider(String jdbcClass, String jdbcUrl, String user, String password)
    {
        this.connection = this.connect2db(jdbcClass, jdbcUrl, user, password);
    }

/**
 * Connecting to MySql database
 */
    private Connection
    connect2db(String jdbcClass, String jdbcUrl, String user, String password)
    {
        Connection c = null;
        try
        {
            Class.forName(jdbcClass);
            c = DriverManager.getConnection(jdbcUrl, user, password);
        }
        catch (Exception e)
        {
            System.err.println("MySQLProvider - Kobling til database feilet: " + e);
            System.exit(-2);
        }
        return c;
    }

    public User
    logIn(String userName, String passWord)
    {
        String sql = "select bruker_id, brukertype_id from bruker" +
                        " where bruker_nv = '" + userName + "' and passord = '" + passWord + "'";

        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            while (result.next())
            {
                return new User(result.getInt(1), userName, result.getInt(2));
            }
        }
        catch (SQLException e)
        {
            System.err.println("Feil i logIn: " + sql +  " " + e);
        }
        return null;      
    }

    public Teacher
    getTeacherByUserId(int userId)
    {
        String sql = "select bruker_id, brukertype_id, ansatt_id, ansatt_nv, mail_id, bruker_nv";
        sql = sql + " from vansatt where bruker_id = " + userId;

        return this.selectTeacher(sql);
    }

    public Teacher
    getTeacher(int teacherId)
    {
        String sql = "select bruker_id, brukertype_id, ansatt_id, ansatt_nv, mail_id, bruker_nv";
        sql = sql + " from vansatt where ansatt_id = " + teacherId;

        return this.selectTeacher(sql);
    }

    private Teacher
    selectTeacher(String sql)
    {
        Teacher teacher = null;
        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            while (result.next())
            {
                teacher = new Teacher(
                    result.getInt(1), 
                    result.getInt(2),
                    result.getInt(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6)
                );
            }
        }
        catch (SQLException e)
        {
            System.err.println("Feil i getTeacher: " + sql +  " " + e);
        }
        
        return teacher;
    }    

    public TreeSet<Teacher>
    getTeachers()
    {
        if (this.teachers == null)
        {
            StringBuilder b = new StringBuilder();
            b.append("select bruker_id, brukertype_id, ansatt_id, ansatt_nv, mail_id, bruker_nv from vansatt");

            this.teachers = new TreeSet<>();
            
            try
            {
                PreparedStatement stmt = this.connection.prepareStatement(b.toString());
                ResultSet result = stmt.executeQuery();
                while (result.next())
                {
                    this.teachers.add(new Teacher(
                        result.getInt(1), 
                        result.getInt(2),
                        result.getInt(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6)
                                    )
                                );
                }
            }
            catch (SQLException e)
            {
                System.err.println("Feil i getStudents: " + b.toString() +  " " + e);
            }
        }
            
        return this.teachers;
    }    

    public Student
    getStudentByUserId(int userId)
    {
        String sql = "select bruker_id, brukertype_id, student_id, student_nv, mail_id, discord_id, github_id, bruker_nv, utdanningssteg_id, student_steg_id, opprettet_dt, endret_dt, endret_av_nv";
        sql = sql + " from vstudent where bruker_id = " + userId;

        return this.selectStudent(sql);
    }

    public Student
    getStudent(int studentId)
    {
        String sql = "select bruker_id, brukertype_id, student_id, student_nv, mail_id, discord_id, github_id, bruker_nv, utdanningssteg_id, student_steg_id, opprettet_dt, endret_dt, endret_av_nv";
        sql = sql + " from vstudent where student_id = " + studentId;

        return this.selectStudent(sql);
    }

    private Student
    selectStudent(String sql)
    {
        Student student = null;
        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            while (result.next())
            {
                student = processStudent(result);
            }
        }
        catch (SQLException e)
        {
            System.err.println("Feil i getStudent: " + sql +  " " + e);
        }
        
        return student;
    }    

    public TreeSet<Student>
    getStudents(int teacherId, int levelId)
    {
        StringBuilder b = new StringBuilder();
        b.append("select bruker_id, brukertype_id, student_id, student_nv, mail_id, discord_id, github_id, bruker_nv, utdanningssteg_id, student_steg_id, opprettet_dt, endret_dt, endret_av_nv from vstudent");
        if (teacherId > 0)
        {
            b.append(" where");
            b.append(" student_id in (select student.student_id");
            b.append(" from student, student_steg, kontakt");
            b.append(" where student.student_id = student_steg.student_id and student_steg.student_steg_id = kontakt.student_steg_id");
            b.append(" and ansatt_id = ");
            b.append(Integer.toString(teacherId));
            b.append(")");
        }
        if (levelId > 0)
        {
            if (teacherId > 0)
                b.append(" and");
            else
                b.append(" where");
            b.append(" student_id in (");
            b.append(" select student_id");
            b.append(" from student_steg");
            b.append(" where student_steg.utdanningssteg_id = ");
            b.append(Integer.toString(levelId));
            b.append(" and now() between dato_fra and ifnull(dato_til,'2099-12-31'))");
        }

        TreeSet<Student> students = new TreeSet<>();
        
        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(b.toString());
            ResultSet result = stmt.executeQuery();
            while (result.next())
            {
                students.add(processStudent(result));
            }
        }
        catch (SQLException e)
        {
            System.err.println("Feil i getStudents: " + b.toString() +  " " + e);
        }
        
        return students;
    } 
    
    private Student
    processStudent(ResultSet result)
    {
        try
        {
            int studentLevelId = result.getInt(9);
            Student student = new Student(
                result.getInt(1), 
                result.getInt(2),
                result.getInt(3),
                result.getString(4),
                result.getString(5),
                result.getString(6),
                result.getString(7),
                result.getString(8),
                studentLevelId,
                result.getInt(10),
                result.getDate(11),
                result.getDate(12),
                result.getString(13)
            );

            Iterator<Level> iter = this.getLevels().iterator();
            while (iter.hasNext())
            {
                Level level = iter.next();
                if (level.getId() == studentLevelId)
                {
                    student.studentLevelNameProperty().set(level.getName());
                }
            }

            return student;
        }
        catch (SQLException e)
        {
            System.err.println("Feil i processStudent: " + e);
        }
    
        return null;
    }

    public TreeSet<Level>
    getLevels()
    {
        if (this.levels == null)
        {
            String sql = "select utdanningssteg_id, utdanningssteg_nv from utdanningssteg";

            this.levels = new TreeSet<>();
            
            try
            {
                PreparedStatement stmt = this.connection.prepareStatement(sql);
                ResultSet result = stmt.executeQuery();
                while (result.next())
                {
                    this.levels.add(new Level(
                                result.getInt(1),
                                result.getString(2)
                            )
                    );
                }
            }
            catch (SQLException e)
            {
                System.err.println("Feil i getLevels: " + sql +  " " + e);
            }
        }

        return this.levels;
    }

    public StudentLevel
    getStudentLevel(Student student)
    {
        int currentLevelId = student.getStudentLevelId();
        String sql = "select student_steg.dato_fra, student_steg.dato_til, team_nr, bedrift_nv, student_bedrift.dato_fra, student_bedrift.dato_til" +
                    " from student_steg" +
                    " left outer join student_bedrift on student_steg.student_steg_id = student_bedrift.student_steg_id" +
                    " where student_steg.student_steg_id = " + currentLevelId;
                                        
        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            while (result.next())
            {
// todo Rydde i Date  - trenger jeg dem i det hele tatt?              
                java.util.Date levelFromDate = result.getDate(1);
                java.util.Date levelToDate = result.getDate(2);
                java.util.Date companyFromDate = result.getDate(5);
                java.util.Date companyToDate = result.getDate(6);

                String companyName = result.getString(4);

                return new StudentLevel(currentLevelId, 
                                        student.studentLevelNameProperty().get(), 
                                        result.getInt(3),
                                        companyName,
                                        companyName == null ? levelFromDate : companyFromDate,
                                        companyName == null ? levelToDate : companyToDate
                                        );
            }
        }
        catch (SQLException e)
        {
            System.err.println("Feil i getStudentLevel: " + sql +  " " + e);
        }

        return null;
    }

    public TreeMap<Integer, Contact>
    getContacts(Student student)
    {
        int currentLevelId = student.getStudentLevelId();
        String sql = "select kontakttype.kontakttype_id, kontakttype_nv, ansatt.ansatt_id, ansatt_nv" +
                    " from kontakt, ansatt, kontakttype" +
                    " where kontakt.ansatt_id = ansatt.ansatt_id and kontakt.kontakttype_id = kontakttype.kontakttype_id" +
                    " and student_steg_id = " + currentLevelId;
                    
        TreeMap<Integer, Contact> contacts = new TreeMap<>();
        
        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            while (result.next())
            {
                int contactTypeId = result.getInt(1);
                contacts.put(contactTypeId, new Contact(
                    contactTypeId,
                            result.getString(2),
                            result.getInt(3),
                            result.getString(4)
                        )
                );
            }
        }
        catch (SQLException e)
        {
            System.err.println("Feil i getContacts: " + sql +  " " + e);
        }

        return contacts;
    }

    public TreeMap<Integer, Document>
    getDocuments(Student student)
    {
// todo Uryddig bsuk av hva som er id og name. Smnl med Contact etc.        
        int currentLevelId = student.getStudentLevelId();
        String sql = "select dokument_id, url, dokumenttype.dokumenttype_id, dokumenttype_nv" +
                        " from dokument, dokumenttype" +
                        " where dokument.dokumenttype_id = dokumenttype.dokumenttype_id" +
                        " and student_steg_id = " + currentLevelId;
                    
        TreeMap<Integer, Document> documents = new TreeMap<>();
        
        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            while (result.next())
            {
                int documentTypeId = result.getInt(3);
                documents.put(documentTypeId, new Document(
                            result.getInt(1),
                            result.getString(2),
                            documentTypeId,
                            result.getString(4)
                        )
                );
            }
        }
        catch (SQLException e)
        {
            System.err.println("Feil i getContacts: " + sql +  " " + e);
        }

        return documents;
    }

    public boolean
    updateStudent(Student student)
    {
        boolean ok = true;
        String sql = "update student set student_nv = ?, mail_id = ?, discord_id = ?," +
                    " github_id = ?, endret_dt = now(), endret_av_nv = ? where student_id = ?";

        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getMailId());
            stmt.setString(3, student.getDiscordName());
            stmt.setString(4, student.getGitHubName());
            stmt.setString(5, student.getUpdatedByName());
            stmt.setInt(6, student.getId());
            ok = stmt.executeUpdate() == 1;
        }
        catch (SQLException e)
        {
            System.err.println("Feil i updateStudent: " + sql +  " " + e);
        }
            
        return ok;
    }
}
