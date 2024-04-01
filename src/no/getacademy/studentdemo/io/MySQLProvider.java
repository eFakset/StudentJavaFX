package no.getacademy.studentdemo.io;

import java.sql.*;
import java.util.*;

import no.getacademy.studentdemo.beans.*;

public class MySQLProvider 
{
    private final Connection                connection;

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
        StringBuilder b = new StringBuilder();
        b.append("select bruker_id, brukertype_id, ansatt_id, ansatt_nv, mail_id, bruker_nv from VAnsatt");

        TreeSet<Teacher> teachers = new TreeSet<>();
        
        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(b.toString());
            ResultSet result = stmt.executeQuery();
            while (result.next())
            {
                teachers.add(new Teacher(
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
        
        return teachers;
    }    

    public Student
    getStudentByUserId(int userId)
    {
        String sql = "select bruker_id, brukertype_id, student_id, student_nv, mail_id, discord_id, github_id, bruker_nv, utdanningssteg_id, opprettet_dt, endret_dt";
        sql = sql + " from vstudent where bruker_id = " + userId;

        return this.selectStudent(sql);
    }

    public Student
    getStudent(int studentId)
    {
        String sql = "select bruker_id, brukertype_id, student_id, student_nv, mail_id, discord_id, github_id, bruker_nv, utdanningssteg_id, opprettet_dt, endret_dt";
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
                student = new Student(
                    result.getInt(1), 
                    result.getInt(2),
                    result.getInt(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getInt(9),
                    result.getDate(10),
                    result.getDate(11)
                );
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
        b.append("select bruker_id, brukertype_id, student_id, student_nv, mail_id, discord_id, github_id, bruker_nv, utdanningssteg_id, opprettet_dt, endret_dt from vstudent");
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
                students.add(new Student(
                                    result.getInt(1), 
                                    result.getInt(2),
                                    result.getInt(3),
                                    result.getString(4),
                                    result.getString(5),
                                    result.getString(6),
                                    result.getString(7),
                                    result.getString(8),
                                    result.getInt(9),
                                    result.getDate(10),
                                    result.getDate(11)
                                )
                            );
            }
        }
        catch (SQLException e)
        {
            System.err.println("Feil i getStudents: " + b.toString() +  " " + e);
        }
        
        return students;
    }   
    
    public TreeSet<Level>
    getLevels()
    {
        String sql = "select utdanningssteg_id, utdanningssteg_nv from utdanningssteg";

        TreeSet<Level> levels = new TreeSet<>();
        
        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            while (result.next())
            {
                levels.add(new Level(
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
        return levels;
    }
}
