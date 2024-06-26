package no.getacademy.studentdemo;

import java.io.FileReader;
import java.util.*;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.*;
import javafx.stage.Stage;
import no.getacademy.studentdemo.beans.*;
import no.getacademy.studentdemo.io.MySQLProvider;
import no.getacademy.studentdemo.view.*;

public class App extends Application 
{
    private LoginScene                  loginScene; 
    private TeacherScene                teacherScene; 
    private StudentScene                studentScene; 

    public static MySQLProvider         provider;
    public Properties                   defaultProperties, properties;

    public static String                PROP_CONNECTIONURL      = "SQL_ConnectionURL";
    public static String                PROP_DBUSER             = "SQL_User";
    public static String                PROP_DBPASSWORD         = "SQL_Password";

    public static int                   SCREEN_WIDTH             = 1400;
    public static int                   SCREEN_HEIGHT            = 900;

    public static User                  loggedInUser;

    @Override
    public void start(Stage stage)
    {
        this.initProperties();
        App.provider = new MySQLProvider("com.mysql.cj.jdbc.Driver",
                                            this.properties.getProperty(App.PROP_CONNECTIONURL),
                                            this.properties.getProperty(App.PROP_DBUSER),
                                            this.properties.getProperty(App.PROP_DBPASSWORD)
        );

        Group loginRoot = new Group();
        this.loginScene = new LoginScene(loginRoot);

        Group teacherRoot = new Group();
        this.teacherScene = new TeacherScene(teacherRoot);

        Group studentRoot = new Group();
        this.studentScene = new StudentScene(studentRoot);

        App.loggedInUser = new User();
        App.loggedInUser.userTypeIdProperty().addListener(new ChangeListener<Number>()
        {
            @Override 
            public void changed(ObservableValue<? extends Number> o, Number oldVal, Number newVal)
            {
                int userTypeId = (Integer)newVal;
                if (userTypeId == AbstractItem.STUDENT)
                {
                    studentScene.activate();
                    stage.setScene(studentScene);
                }
                else
                {
                    teacherScene.activate();
                    stage.setScene(teacherScene);
                }
                stage.setX(200);
                stage.setY(100);
            }
        });

        stage.setScene(loginScene);

/*        App.loggedInUser.idProperty().set(3);
        App.loggedInUser.userTypeIdProperty().set(2);
        stage.setScene(this.teacherScene);
        this.teacherScene.activate();
        App.loggedInUser.idProperty().set(7);
        App.loggedInUser.userTypeIdProperty().set(1);
        stage.setScene(this.studentScene);
        this.studentScene.activate();
*/        
        stage.show();
    }

    private void
    initProperties()
    {
        String iniFile = System.getProperty("user.home") + "\\source\\repos\\StudentJavaFX\\dbconfig.env";

        this.defaultProperties = new Properties();

        this.properties = new Properties(this.defaultProperties);

        try
        {
            this.properties.load(new FileReader(iniFile));

        }
        catch (Exception e)
        {
            System.err.println(iniFile + " could not be opened. " + e);
            System.exit(-2);
        }
    }
}
